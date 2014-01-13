/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import java.util.*;
import datas.*;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;


/**
 *
 * @author mariata
 */
public class ConsumerEntity extends SimulationEntity {
    // time in ms to wait for all the timers to be set
    // TODO : remove if not useful anymore
    private final int INIT_TIME = 0;
    private final int THREAD_POOL_NB = 10;

    private ScheduledExecutorService scheduler;
    private SimulationScenario simulationScenario = null;
    private Hashtable<String, AgentConfiguration> hashAgentsConf;        
    private Date startDate;
    private ResultsLogger logger;
    private int nbSteps;
    private simulationRef.SimulationWSService wsService;
    private simulationRef.SimulationWS wsPort;

    private volatile int reqId = 0;  // Don't change manually because of concurrency issues
    private int stepsDone = 0; // Don't change manually because of concurrency issues

    public ConsumerEntity(String agentId) {
        super(agentId);
        logger = new ResultsLogger(this.getid());
        wsService = new simulationRef.SimulationWSService();
        wsPort = wsService.getSimulationWSPort();
    }

    /**
     * Configure the simulation scenario and id
     * @param id
     * @param simulationScenario
     */
    public void configureConsumer(SimulationScenario simulationScenario) {
        this.simulationScenario = simulationScenario;
        hashAgentsConf = this.initializeAgentsTable();        
        nbSteps = simulationScenario.getSteps().size();
    }

    /**
     * Start the simulation
     */
    //TODO : test overlapping steps
    // TODO: test request rate limit
    @Override
    public void startSimulation() {
        scheduler =
            Executors.newScheduledThreadPool(THREAD_POOL_NB);
        SimulationStep step;
        int i;
        

        for (i = 0; i < nbSteps; i++) {
            long taskDelay, taskEnd;
            int nbRequest = 0;
            Date stepStartDate;
            step = simulationScenario.getSteps().get(i);

            // parameters for the scheduledExecutor run() method
            final String destId = step.getDestID();
            final String wsUrl = hashAgentsConf.get(destId).getWsAddress();
            final int reqPayloadSize = step.getRequestPayloadSize();
            final long processTime =  step.getProcessTime();
            final int respPayloadSize = step.getResponsePayloadSize();

            // duration converted in seconds from ms
            double stepDuration = (double) step.getBurstDuration() / 1000.0;
            System.out.println("stepduration="+stepDuration);
            nbRequest = (int) (stepDuration * step.getBurstRate());
            System.out.println("nbRequest="+nbRequest);

            // Make sure at least one request is sent
            if(nbRequest <= 0) {
                nbRequest = 0;
            }
            //interval between two request in ms
            long period = (long) ((stepDuration / (float)nbRequest) *1000);

            startDate = new Date(INIT_TIME + System.currentTimeMillis());
            logger.setStartDate(startDate);
            stepStartDate = new Date(startDate.getTime() + step.getBurstStartDate());            
             final Runnable stepTask = new Runnable() {
                 int nbReqSent = 0;
                        public void run() {                            
                            
                            //System.out.println("*********************************");
                            //System.out.println("Run "+nbReqSent+" start date : " + System.currentTimeMillis());
                            //System.out.println("Run "+nbReqSent+" bf log : " + System.currentTimeMillis());
                            try {
                                //logger.writeSimulationEvent(reqId,AgentType.CONSUMER, EventType.REQUEST_SENT);
                                logger.writeSimulationEvent(getRequestID(),AgentType.CONSUMER, EventType.REQUEST_SENT);
                            } catch (Exception ex) {
                                Logger.getLogger(ConsumerEntity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //System.out.println("Run "+nbReqSent+" af log : " + System.currentTimeMillis());
                            // Send the request to the producer
                            // (the response logging is done in the request callback)
                            //System.out.println("Run "+nbReqSent+" bf async req : " + System.currentTimeMillis());
                            //sendAsyncRequest(wsUrl, destId, reqId, reqPayloadSize, processTime, respPayloadSize);
                            sendAsyncRequest(wsUrl, destId, getRequestID(), reqPayloadSize, processTime, respPayloadSize);
                            //System.out.println("Run "+nbReqSent+" af async req : " + System.currentTimeMillis());
                            //System.out.println("Run "+nbReqSent+" bf increment : " + System.currentTimeMillis());
                            incrementRequestID(); // synchronized method
                            //System.out.println("Run "+nbReqSent+" af increment : " + System.currentTimeMillis());
                            nbReqSent++;   
                        }
                    };
                // schedule the task
                taskDelay = stepStartDate.getTime()- System.currentTimeMillis();

                // Schedule the periodic task of sending requests
                final ScheduledFuture<?> stepTaskHandle =
                    scheduler.scheduleAtFixedRate(stepTask, taskDelay, period, TimeUnit.MILLISECONDS);
            //System.out.println("task delay :"+taskDelay);
            System.out.println("task period :"+period);    


            scheduler.schedule(new Runnable() {
                    public void run() {
                        stepTaskHandle.cancel(true);
                        System.out.println("step task done");
                    }
                }, step.getBurstStopDate() + INIT_TIME, TimeUnit.MILLISECONDS);
            
        }
       
    }
   
    /**
     * Abort the simulation
     */
    @Override
    public void abortSimulation() {
        scheduler.shutdownNow();
        if(scheduler.isShutdown()) {
            System.out.println("Scheduler shutdown");
        }
    }

    /**
     * Get an hashtable of agentconfiguration indexed by agents ids.
     * Used to find a WS address corresponding to an ID.
     * @return
     */
    private Hashtable<String, AgentConfiguration> initializeAgentsTable() {
        Hashtable<String, AgentConfiguration> hashtable = new Hashtable<String, AgentConfiguration>();
        for (AgentConfiguration agentConfiguration :this.simulationScenario.getAgentsconfiguration()) {
             hashtable.put(agentConfiguration.getAgentId(), agentConfiguration);
        }
        return hashtable;
    }

    

    synchronized private void incrementRequestID() {
        reqId++;
    }
    synchronized private int getRequestID() {
        return reqId;
    }

    synchronized private void stepDone() {
        stepsDone++;        
    }


    /**
     * Sends an asynchronous request to the WS whose ID is producerID.
     * The request callback handler logs the response when it is received
     * @param producerId
     * @param requestId
     * @param reqPayloadSize
     */
    private void sendAsyncRequest(String wsAddress, String producerId, int requestId, int reqPayloadSize, long processTime, int respPayloadSize) {
        String requestData = null;        
        final int finalReqId = requestId;

        try { // Call Web Service Operation(async. callback)
           
            // Dynamic WS addressing
            ((BindingProvider)wsPort).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsAddress);

            javax.xml.ws.AsyncHandler<simulationRef.RequestOperationResponse> asyncHandler = new javax.xml.ws.AsyncHandler<simulationRef.RequestOperationResponse>() {

                public void handleResponse(javax.xml.ws.Response<simulationRef.RequestOperationResponse> response) {
                    try {                        
                        logger.writeSimulationEvent(finalReqId, AgentType.CONSUMER , EventType.RESPONSE_RECEIVED);

                        System.out.println("Result :\n " + response.get().getReturn());
                    } catch (Exception ex) {
                        // TODO handle exception
                    }
                }
            };

            requestData = Utils.getDummyString(reqPayloadSize, 'A');

            // Call web service asynchronously (callback)
            java.util.concurrent.Future<? extends java.lang.Object> callBackResult = wsPort.requestOperationAsync(producerId, requestId, requestData, processTime, respPayloadSize,asyncHandler);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    public ResultsLogger getLogger() {
        return logger;
    }
}

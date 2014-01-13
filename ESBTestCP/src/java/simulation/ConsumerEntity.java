/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;


import java.util.*;
import datas.*;
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
    //TODO : change the number of threads depending on the scenario ?
    private final int THREAD_POOL_NB = 10;
    // time to wait after the last request is sent (in ms)
    private final int LAST_REQ_TIMEOUT = 3000;

    private ScheduledExecutorService scheduler; // scheduler to send requests
    private SimulationScenario simulationScenario = null;
    private Hashtable<String, AgentConfiguration> hashAgentsConf;        
    private Date startDate; //Simulation start date
    private ResultsLogger logger;
    private int nbSteps;    // number of steps in the scenario
    private simulationRef.SimulationWSService wsService;
    private simulationRef.SimulationWS wsPort;

    // Request ID : don't change manually because of concurrency issues
    // Use incrementReqId() and getReqId()
    private volatile int reqId = 0;  

    public ConsumerEntity(String agentId) {
        super(agentId);
        logger = new ResultsLogger(this.getid());
        wsService = new simulationRef.SimulationWSService();
        wsPort = wsService.getSimulationWSPort();
    }

    /**
     * Configure the simulation scenario     
     * @param simulationScenario
     */
    public void configureConsumer(SimulationScenario simulationScenario) {
        this.simulationScenario = simulationScenario;
        hashAgentsConf = this.initializeAgentsTable();        
        nbSteps = simulationScenario.getSteps().size();
    }

    /**
     * Starts the simulation (non-blocking)
     */
     //TODO : exit if there is an error
    @Override
    public void startSimulation() {
        scheduler =
            Executors.newScheduledThreadPool(THREAD_POOL_NB);
        SimulationStep step;
        int i;
        

        for (i = 0; i < nbSteps; i++) {
            long taskDelay;
            int nbRequests = 0;
            Date stepStartDate;
            step = simulationScenario.getSteps().get(i);

            // parameters for the scheduledExecutor run() method
            final String destId = step.getDestID();
            final String wsUrl = hashAgentsConf.get(destId).getWsAddress();
            final int reqPayloadSize = step.getRequestPayloadSize();
            final long processTime =  step.getProcessTime();
            final int respPayloadSize = step.getResponsePayloadSize();
            final int currentStepNb = i;
            //final int nbReqToSend =

            // duration converted in seconds from ms
            double stepDuration = (double) step.getBurstDuration() / 1000.0;
            System.out.println("stepduration="+stepDuration);
            nbRequests = (int) (stepDuration * step.getBurstRate());
            System.out.println("nbRequests="+nbRequests);

            // Make sure at least one request is sent
            if(nbRequests <= 0) {
                nbRequests = 0;
            }
            //interval between two request in ms
            long period = (long) ((stepDuration / (float)nbRequests) *1000);

            // Set simulation starting date
            startDate = new Date(INIT_TIME + System.currentTimeMillis());
            logger.setStartDate(startDate);
            // Set starting date of the current scenario step
            stepStartDate = new Date(startDate.getTime() + step.getBurstStartDate());

            // Create a task that sends a request to a producer
             final Runnable stepTask = new Runnable() {
                 int nbReqSent = 0;
                        public void run() {                            
                            try {                                
                                logger.writeSimulationEvent(getReqId(),AgentType.CONSUMER, EventType.REQUEST_SENT);
                            } catch (Exception ex) {
                                Logger.getLogger(ConsumerEntity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            sendAsyncRequest(wsUrl, destId, getReqId(), reqPayloadSize, processTime, respPayloadSize);                            
                            incrementReqId(); // synchronized method                            
                            nbReqSent++;

                            /* Not an efficient way to do this
                            // If this is the last step
                            if(currentStepNb == nbRequests -1) {
                                if(nbReqSent == n) {
                                    listener.simulationDone(logger.getResultSet());
                                }
                            }
                             * */
                        }
                    };
                // schedule the task
                taskDelay = stepStartDate.getTime()- System.currentTimeMillis();

                // Schedule the periodic task of sending requests
                final ScheduledFuture<?> stepTaskHandle =
                    scheduler.scheduleAtFixedRate(stepTask, taskDelay, period, TimeUnit.MILLISECONDS);            
            System.out.println("task period :"+period);    


            // Start the periodic task to send requests
            scheduler.schedule(new Runnable() {
                    public void run() {
                        stepTaskHandle.cancel(true);
                        System.out.println("step task done");                        
                    }
                }, step.getBurstStopDate() + INIT_TIME, TimeUnit.MILLISECONDS);
            
        }

        // Notify the AgentController when the simulation is done
        //******************************************************************
        // Note : this just calls the AgentController when the scenario is
        // SUPPOSED to be done, it does not count the number of requests sent
        //******************************************************************
        //TODO : fix above issue ?
            scheduler.schedule(new Runnable() {
                    public void run() {
                        System.out.println("**************\nSimulation done\n****************");
                        if(listener == null) {
                            try {
                                throw new Exception("ConsumerEntity listener not set");
                            } catch (Exception ex) {
                                Logger.getLogger(ConsumerEntity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else {
                            listener.simulationDone(logger.getResultSet());
                        }
                        
                    }
                }, simulationScenario.getEndDate() + LAST_REQ_TIMEOUT, TimeUnit.MILLISECONDS);

                
       
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

    

    synchronized private void incrementReqId() {
        reqId++;
    }
    synchronized private int getReqId() {
        return reqId;
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

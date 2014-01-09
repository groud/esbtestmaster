/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

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
    private final int INIT_TIME = 1000;


    private SimulationScenario simulationScenario = null;
    private Hashtable<String, AgentConfiguration> hashAgentsConf;    
    private SimulationThread simulationThread;
    private Date startDate;
    private ResultsLogger logger;
    private int nbSteps;
    

    private static int reqId = 0;  // Don't change manually because of concurrency issues
    private int stepsDone = 0; // Don't change manually because of concurrency issues
    /**
     * Configure the simulation scenario and id
     * @param id
     * @param simulationScenario
     */
    public void configureConsumer(String id, SimulationScenario simulationScenario) {
        this.simulationScenario = simulationScenario;
        this.setId(id);
        hashAgentsConf = this.initializeAgentsTable();
        logger = new ResultsLogger(this.getid());
        nbSteps = simulationScenario.getSteps().size();
    }

    /**
     * Start the simulation
     */
    @Override
    public void startSimulation() {
        final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(5);
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
                                logger.writeSimulationEvent(reqId,AgentType.CONSUMER, EventType.REQUEST_SENT);
                            } catch (Exception ex) {
                                Logger.getLogger(ConsumerEntity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //System.out.println("Run "+nbReqSent+" af log : " + System.currentTimeMillis());
                            // Send the request to the producer
                            // (the response logging is done in the request callback)
                            //System.out.println("Run "+nbReqSent+" bf async req : " + System.currentTimeMillis());
                            sendAsyncRequest(wsUrl, destId, reqId, reqPayloadSize, processTime, respPayloadSize);
                            //System.out.println("Run "+nbReqSent+" af async req : " + System.currentTimeMillis());
                            //System.out.println("Run "+nbReqSent+" bf increment : " + System.currentTimeMillis());
                            incrementRequestID(); // synchronized method
                            //System.out.println("Run "+nbReqSent+" af increment : " + System.currentTimeMillis());
                            nbReqSent++;                            
                        }
                    };
                // schedule the task
                taskDelay = stepStartDate.getTime()- System.currentTimeMillis();
                final ScheduledFuture<?> stepTaskHandle =
                    scheduler.scheduleAtFixedRate(stepTask, taskDelay, period, TimeUnit.MILLISECONDS);
            System.out.println("delay :"+taskDelay);
            System.out.println("period :"+period);
            //TODO : cancel

            taskEnd = step.getBurstStopDate() + startDate.getTime();
            scheduler.schedule(new Runnable() {
                    public void run() { stepTaskHandle.cancel(true); }
                }, taskEnd, TimeUnit.SECONDS);
            
        }

        try {
            //TODO : Wait for end properly
            Thread.sleep(20000);
            //System.out.println("***************\nEnd of startSimulation ended");
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsumerEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("***************\nEnd of startSimulation ended");
        System.out.println(logger.getResultSet().toString());
    }


    /*
    
    public void startSimulation() {
        //TODO : add receive response code        
        simulationThread = new SimulationThread();
        simulationThread.start();
        
        try {
            simulationThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsumerEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("***************\nSimulation thread ended");

        System.out.println(logger.getResultSet().toString());
        
        //TODO : warn master when the consumer has finished its scenario

        //TODO : wait for the master to ask the consumer to send its results back
    }
    */
    /**
     * Abort the simulation
     */
    @Override
    public void abortSimulation() {
        simulationThread.stop();
    }

    /**
     * Get an hashtable of agentconfiguration indexed by agents ids.
     * Used to find a WS address corresponding to an ID.
     * @return
     */
    private Hashtable<String, AgentConfiguration> initializeAgentsTable() {
        Hashtable<String, AgentConfiguration> hashtable = new Hashtable<String, AgentConfiguration>();
        for (AgentConfiguration agentConfiguration :this.simulationScenario.getAgentsconfiguration()) {
             hashtable.put(agentConfiguration.getName(), agentConfiguration);
        }
        return hashtable;
    }

    /**
     * A Thread used to execute the simulation
     */
    private class SimulationThread extends Thread {

        private Timer timer;
        private SimulationStep step;        
        private volatile int nbRequest = 0;

        

        //TODO : tests foireux pour vérifier la précision des envois de requêtes
        // (en écrivant la date d'envoi de chaque requête pour vérifier)
        @Override
        public void run() {
            
           
            


            /*
            int i;
            
            reqId = 0;
            stepsDone = 0;

            //send request
            if (simulationScenario != null) {
                // Store simulation start date
                
                startDate = new Date(INIT_TIME + System.currentTimeMillis());
                logger.setStartDate(startDate);

                System.out.println("simulation start date : "+startDate.getTime());

                for (i = 0; i < nbSteps; i++) {                    
                    step = simulationScenario.getSteps().get(i);
                    final String wsUrl = hashAgentsConf.get(step.getDestID()).getWsAddress();
                    timer = new Timer();
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

                    Date timerTaskStart = new Date(startDate.getTime() + step.getBurstStartDate());
                    System.out.println("step start : "+timerTaskStart.getTime());
                    System.out.println("step period : "+period);

                    //configure timer,and start scenario
                    timer.scheduleAtFixedRate(new TimerTask() {
                        int nbReqSent = 0;                        
                        
                        public void run() {
                            System.out.println("*********************************");
                            
                            System.out.println("Run "+nbReqSent+" start date : " + System.currentTimeMillis());
                            System.out.println("Run "+nbReqSent+" bf log : " + System.currentTimeMillis());
                            try {
                                logger.writeSimulationEvent(reqId,AgentType.CONSUMER, EventType.REQUEST_SENT);
                            } catch (Exception ex) {
                                Logger.getLogger(ConsumerEntity.class.getName()).log(Level.SEVERE, null, ex);
                            }System.out.println("Run "+nbReqSent+" af log : " + System.currentTimeMillis());
                            // Send the request to the producer
                            // (the response logging is done in the request callback)
                            System.out.println("Run "+nbReqSent+" bf async req : " + System.currentTimeMillis());
                            sendAsyncRequest(wsUrl, step.getDestID(), reqId, step.getRequestPayloadSize(), step.getProcessTime(), step.getResponsePayloadSize());
                            System.out.println("Run "+nbReqSent+" af async req : " + System.currentTimeMillis());
                            System.out.println("Run "+nbReqSent+" bf increment : " + System.currentTimeMillis());
                            incrementRequestID(); // synchronized method
                            System.out.println("Run "+nbReqSent+" af increment : " + System.currentTimeMillis());
                            nbReqSent++;

                            System.out.println("Run "+nbReqSent+" end date : " + System.currentTimeMillis());
                            // When all requests for the step have been sent
                            if(nbReqSent==nbRequest) {                                
                                // Notify the ConsumerEntity that a step is done
                                stepDone();
                                // Cancel the timer task
                                this.cancel();
                            }
                            
                            
                        }                    
                    }, timerTaskStart , period);
                }          

            } else {
                System.out.println("steps have not been configured");
            }

            // Waits for all steps to be done
            //*********************************************************
            //TODO : ***WILL*** hang if a step is not finished correctly
            // -> need for a timeout somewhere
            // TODO : some results may come back after this thead ends
            // -> possible problem
            //*********************************************************
           
            while(stepsDone < nbSteps) {                
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConsumerEntity.class.getName()).log(Level.SEVERE, null, ex);
                }                                         
            }            
            */
        }             
    }

    synchronized private void incrementRequestID() {
        reqId++;
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
       

        try { // Call Web Service Operation(async. callback)
            simulationRef.SimulationWSService service = new simulationRef.SimulationWSService();
            simulationRef.SimulationWS port = service.getSimulationWSPort();

            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsAddress);

            javax.xml.ws.AsyncHandler<simulationRef.RequestOperationResponse> asyncHandler = new javax.xml.ws.AsyncHandler<simulationRef.RequestOperationResponse>() {

                public void handleResponse(javax.xml.ws.Response<simulationRef.RequestOperationResponse> response) {
                    try {                        
                        //logger.writeSimulationEvent(reqId, AgentType.CONSUMER , EventType.RESPONSE_RECEIVED);

                        System.out.println("Result :\n " + response.get().getReturn());
                    } catch (Exception ex) {
                        // TODO handle exception
                    }
                }
            };

            requestData = Utils.getDummyString(reqPayloadSize, 'A');

            // Call web service asynchronously (callback)
            java.util.concurrent.Future<? extends java.lang.Object> callBackResult = port.requestOperationAsync(producerId, requestId, requestData, processTime, respPayloadSize,asyncHandler);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    public ResultsLogger getLogger() {
        return logger;
    }
    
}

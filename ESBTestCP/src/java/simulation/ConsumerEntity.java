/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import java.util.*;
import datas.*;
import java.util.TimerTask;
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
   
    private int reqId = 0;  // Don't change manually because of concurrency issues
    private int stepsDone = 0; // Don't change manually because of concurrency issues

    public ConsumerEntity(String agentId) {
        super(agentId);
    }

    /**
     * Configure the simulation scenario and id
     * @param id
     * @param simulationScenario
     */
    public void configureConsumer(SimulationScenario simulationScenario) {
        this.simulationScenario = simulationScenario;
        hashAgentsConf = this.initializeAgentsTable();
        logger = new ResultsLogger(this.getid());
        nbSteps = simulationScenario.getSteps().size();
    }

    /**
     * Start the simulation
     */
    @Override
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

    /**
     * Abort the simulation
     */
    @Override
    public void abortSimulation() {
        if (simulationThread instanceof SimulationThread) simulationThread.stop();
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
            int i;
            
            reqId = 0;
            stepsDone = 0;

            //send request
            if (simulationScenario != null) {
                // Store simulation start date

                startDate = new Date(INIT_TIME + System.currentTimeMillis());
                logger.setStartDate(startDate);

                for (i = 0; i < nbSteps; i++) {
                    step = simulationScenario.getSteps().get(i);
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

                    //configure timer,and start scenario
                    timer.scheduleAtFixedRate(new TimerTask() {
                        int nbReqSent = 0;
                        
                        public void run() {                           
                            try {
                                logger.writeSimulationEvent(reqId,AgentType.CONSUMER, EventType.REQUEST_SENT);
                            } catch (Exception ex) {
                                Logger.getLogger(ConsumerEntity.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            // Send the request to the producer
                            // (the response logging is done in the request callback)
                            sendAsyncRequest(step.getDestID(), reqId, step.getRequestPayloadSize(), step.getProcessTime(), step.getResponsePayloadSize());
                            incrementRequestID(); // synchronized method
                            nbReqSent++;

                            // When all requests for the step have been sent
                            if(nbReqSent==nbRequest) {                                
                                // Notify the ConsumerEntity that a step is done
                                stepDone();
                                // Cancel the timer task
                                this.cancel();
                            }
                            
                            
                        }                    
                    }, new Date(startDate.getTime() + step.getBurstStartDate()) , period);
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
    private void sendAsyncRequest(String producerId, int requestId, int reqPayloadSize, long processTime, int respPayloadSize) {
        String requestData = null;
        ProducerConfiguration producerConf = null;
        // tempAgentConf used to test if the AgentConfiguration exists and if it's
        // a ProducerConfiguration without having to cast all the time afterwards
        AgentConfiguration tempAgentConf;
        final int reqId = requestId;    // to be accessed in the inner class handler

        // Get the ProducerConf
        tempAgentConf = this.hashAgentsConf.get(producerId);
        
        if (tempAgentConf == null) {
            throw new IllegalArgumentException("AgentConfiguration is null");
        } else if (tempAgentConf instanceof ProducerConfiguration) {
            // To avoid typecasting afterwards
            producerConf = (ProducerConfiguration) tempAgentConf;
        } else {
            throw new IllegalArgumentException("Remote agent is not a producer");
        }


        try { // Call Web Service Operation(async. callback)
            simulationRef.SimulationWSService service = new simulationRef.SimulationWSService();
            simulationRef.SimulationWS port = service.getSimulationWSPort();

            ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,producerConf.getWsAddress());

            javax.xml.ws.AsyncHandler<simulationRef.RequestOperationResponse> asyncHandler = new javax.xml.ws.AsyncHandler<simulationRef.RequestOperationResponse>() {

                public void handleResponse(javax.xml.ws.Response<simulationRef.RequestOperationResponse> response) {
                    try {                        
                        logger.writeSimulationEvent(reqId, AgentType.CONSUMER , EventType.RESPONSE_RECEIVED);

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

    /**
     * Teste la communication inter-agents.
     * @param args
     */
    public static void main(String[] args) {

        
        String producerId = "producer1";
        String consumerId = "consumer1";
        // Test ConsumerEntity
        ConsumerEntity cons = new ConsumerEntity("cons1");

        SimulationScenario ss = new SimulationScenario();
        ProducerConfiguration pc = new ProducerConfiguration();
        pc.setAgentId(producerId);
        pc.setWsAddress("http://localhost:8090/ESBTestCompositeService1/casaPort1");
        ss.getAgentsconfiguration().add(pc);

        ss.addStep(new SimulationStep(consumerId, producerId, 0, 3000, 1, 16, 1000L, 20));
        ss.addStep(new SimulationStep(consumerId, producerId, 3000, 5000, 2, 16, 1000L, 20));

        cons.configureConsumer(ss);
        cons.startSimulation();
         
    }
}

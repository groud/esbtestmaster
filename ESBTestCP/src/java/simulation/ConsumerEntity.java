/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import java.util.*;
import datas.*;
import java.util.TimerTask;
import javax.xml.ws.BindingProvider;


/**
 *
 * @author mariata
 */
public class ConsumerEntity extends SimulationEntity {

    private SimulationScenario simulationScenario = null;
    private Hashtable<String, AgentConfiguration> hashAgentsConf;
    private ResultSet resultSet;
    private SimulationThread simulationThread;

    /**
     * Configure the simulation scenario and id
     * @param id
     * @param simulationScenario
     */
    public void configureConsumer(String id, SimulationScenario simulationScenario) {
        this.simulationScenario = simulationScenario;
        this.setId(id);
        hashAgentsConf = this.initializeAdressesTable();
    }

    /**
     * Start the simulation
     */
    @Override
    public void startSimulation() {
        //TODO : add receive response code
        resultSet = new ResultSet();
        simulationThread = new SimulationThread();
        simulationThread.start();

        //TODO : warn master when the consumer has finished its scenario

        //TODO : wait for the master to ask the consumer to send its results back
    }

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
    private Hashtable<String, AgentConfiguration> initializeAdressesTable() {
        Hashtable<String, AgentConfiguration> hashtable = new Hashtable<String, AgentConfiguration>();
        for (AgentConfiguration agentConfiguration :this.simulationScenario.getAgentsconfiguration()) {
             hashtable.put(agentConfiguration.getName(), agentConfiguration);
        }
        return hashtable;
    }

    /**
     * Logs a simulation event
     * @param agent
     * @param event
     */
    private void writeSimulationEvent(AgentType agent, EventType event) {
        ResultEvent currentEvent = new ResultEvent();
        currentEvent.setAgentId(this.getid());
        currentEvent.setAgentType(agent);
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        currentEvent.setEventDate(0);//now.getTime());// TODOOOO
        currentEvent.setEventType(event);

        //add in list of events
        resultSet.getEvents().add(currentEvent);
    }

    /**
     * A Thread used to execute the simulation
     */
    private class SimulationThread extends Thread {

        private Timer timer;
        private SimulationStep step;
        private int reqId = 0;
        private int nbRequest = 0;
        private Date startDate;

        //TODO : tests foireux pour vérifier la précision des envois de requêtes
        // (en écrivant la date d'envoi de chaque requête pour vérifier)
        @Override
        public void run() {
            int i;
            //send request
            if (simulationScenario != null) {
                // Store simulation start date
                startDate = new Date();
                for (i = 0; i < simulationScenario.getSteps().size(); i++) {
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
                            
                            //writeSimulationEvent(AgentType.CONSUMER, EventType.REQUEST_SENT);
                            // Send the request to the producer
                            // (the response logging is done in the request callback)
                            sendAsyncRequest(step.getDestID(), reqId, step.getRequestPayloadSize(), step.getProcessTime(), step.getResponsePayloadSize());
                            reqId++;
                            nbReqSent++;
                            
                            if(nbReqSent==nbRequest) {
                                this.cancel();
                            }
                            

                        }                    
                    }, new Date(startDate.getTime() + step.getBurstStartDate()) , period);
                }          

            } else {
                System.out.println("steps have not been configured");
            }

        }
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
                        // TODO : log response
                        //writeSimulationEvent(AgentType.CONSUMER , EventType.RESPONSE_RECEIVED);

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
        ConsumerEntity cons = new ConsumerEntity();

        SimulationScenario ss = new SimulationScenario();
        ProducerConfiguration pc = new ProducerConfiguration();
        pc.setName(producerId);
        pc.setWsAddress("http://localhost:8090/ESBTestCompositeService1/casaPort1");
        ss.getAgentsconfiguration().add(pc);

        ss.addStep(new SimulationStep(consumerId, producerId, 0, 3000, 1, 16, 1000L, 20));
        ss.addStep(new SimulationStep(consumerId, producerId, 3000, 4500, 2, 16, 1000L, 20));

        cons.configureConsumer(consumerId, ss);
        cons.startSimulation();
    }
}

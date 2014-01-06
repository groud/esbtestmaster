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

        // TODO (Samy) : save the simulation start date in startSimulation() (System.currentTimeMillis)
        // use timer.scheduleAtFixedRate with delay = startDate + step.getBurstStartDate()
        // and period = step.getBurstDuration() / nbRequest
        // and CANCEL timer after the correct number of requests has been sent
        @Override
        public void run() {
            int i;
            //send request
            if (simulationScenario != null) {
                for (i = 0; i < simulationScenario.getSteps().size(); i++) {
                    step = simulationScenario.getSteps().get(i);
                    timer = new Timer();
                    long nbRequest = (long) (step.getBurstDuration() * step.getBurstRate());
                    //interval between two request
                    long period = (long) (step.getBurstDuration() / nbRequest);

                    //configure timer,and start senario
                    timer.scheduleAtFixedRate(new TimerTask() {

                        public void run() {

                            //writeSimulationEvent(AgentType.CONSUMER, EventType.REQUEST_SENT);
                            // Send the request to the producer
                            // (the response logging is done in the request callback)
                            sendAsyncRequest(step.getDestID(), reqId, step.getRequestPayloadSize(), step.getProcessTime(), step.getResponsePayloadSize());
                            reqId++;


                        }
                    }, step.getBurstStartDate(), period);
                }
                //TO DO : send the results to the agent controller, wait the end of all thread
                //listener.simulationDone(resultSet);

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

        ss.getSteps().add(new SimulationStep(consumerId, producerId, 0, 2, 1, 16, 1000L, 20));

        cons.configureConsumer(consumerId, ss);
        cons.startSimulation();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import java.util.*;
import datas.*;
import interfaces.SimulationMessageListener;
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

    //SimulationMessageListener listener; already defined in the superclass

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

    @Override
    public void startSimulation() {
        //TODO : add receive response code
        resultSet = new ResultSet();
        simulationThread = new SimulationThread();
        simulationThread.start();

    }

    @Override
    public void abortSimulation() {
        simulationThread.stop();
    }


    private Hashtable<String, AgentConfiguration> initializeAdressesTable() {
        Hashtable<String, AgentConfiguration> hashtable = new Hashtable<String, AgentConfiguration>();
        for (int i = 0; i < this.simulationScenario.getAgentsconfiguration().size(); i++) {
            //hashtable.put(this.simulationScenario.getAgentsconfiguration().get(i).getName(), this.simulationScenario.getAgentsconfiguration().get(i).getWsAddress());
            hashtable.put(this.simulationScenario.getAgentsconfiguration().get(i).getName(), this.simulationScenario.getAgentsconfiguration().get(i));
        }
        return hashtable;
    }

    private void writeSimulationEvent(AgentType agent, EventType event) {
        ResultEvent currentEvent = new ResultEvent();
        currentEvent.setAgentId(this.getid());
        currentEvent.setAgentType(agent);
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now =new Date();
        currentEvent.setEventDate(0);//now.getTime());// TODOOOO
        currentEvent.setEventType(event);

        //add in list of events
        resultSet.getEvents().add(currentEvent);
    }

    private class SimulationThread extends Thread {

        private Timer timer;
        private SimulationStep step;
        private int reqId = 0;

        // TODO (Samy) : save the simulation start date in startSimulation() (System.currentTimeMillis)
        // use timer.scheduleAtFixedRate with delay = startDate + step.getBurstStartDate()
        // and period = step.getBurstDuration() / nbRequest
        // and CANCEL timer after the correct number of requests has been sent
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
                            
                            //writeSimulationEvent(AgentType.CONSUMER , EventType.REQUEST_SENT );
                            // Send the request to the producer
                            // (the response logging is done in the request callback)
                            sendAsyncRequest(step.getProviderID(), reqId, step.getDataPayloadSize());
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

    /* Synchronous -> not used
    private void sendRequest(String producerId, int requestId, int reqPayloadSize) {
        String requestData = null;
        ProducerConfiguration producerConf = null;
        // tempAgentConf used to test if the AgentConfiguration exists and if it's
        // a ProducerConfiguration without having to cast all the time afterwards
        AgentConfiguration tempAgentConf;

        // Get ProducerConf
        tempAgentConf = this.hashAgentsConf.get(producerId);

        if(tempAgentConf == null) {
            throw new IllegalArgumentException("AgentConfiguration is null");
        }
        else if(tempAgentConf.getAgentType() == AgentType.PRODUCER) {
            // To avoid typecasting afterwards
            producerConf = (ProducerConfiguration) tempAgentConf;
        }
        else {
            throw new IllegalArgumentException("Remote agent is not a producer");
        }

        try { // Call Web Service Operation
            simulationRef.SimulationWSService service = new simulationRef.SimulationWSService();
            simulationRef.SimulationWS port = service.getSimulationWSPort();

            // Dynamic URL binding;
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, producerConf.getWsAddress());

            // Fill the request payload with reqPayloadSize 'A's
            requestData = Utils.getDummyString(reqPayloadSize, 'A');

            // TODO : log events before and after request
            String result = port.requestOperation(producerId, requestId, requestData,
                                   producerConf.getResponseTime(), producerConf.getResponseSize());
            System.out.println("Producer response :\n"+result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }
    */

    /**
     * Sends an asynchronous request to the WS whose ID is producerID.
     * The request callback handler logs the response when it is received
     * @param producerId
     * @param requestId
     * @param reqPayloadSize
     */
    private void sendAsyncRequest(String producerId, int requestId, int reqPayloadSize) {
        String requestData = null;
        ProducerConfiguration producerConf = null;
        // tempAgentConf used to test if the AgentConfiguration exists and if it's
        // a ProducerConfiguration without having to cast all the time afterwards
        AgentConfiguration tempAgentConf;

        // Get the ProducerConf
        tempAgentConf = this.hashAgentsConf.get(producerId);

        if(tempAgentConf == null) {
            throw new IllegalArgumentException("AgentConfiguration is null");
        }
        else if(tempAgentConf.getAgentType() == AgentType.PRODUCER) {
            // To avoid typecasting afterwards
            producerConf = (ProducerConfiguration) tempAgentConf;
        }
        else {
            throw new IllegalArgumentException("Remote agent is not a producer");
        }


        try { // Call Web Service Operation(async. callback)
            simulationRef.SimulationWSService service = new simulationRef.SimulationWSService();
            simulationRef.SimulationWS port = service.getSimulationWSPort();


            javax.xml.ws.AsyncHandler<simulationRef.RequestOperationResponse> asyncHandler = new javax.xml.ws.AsyncHandler<simulationRef.RequestOperationResponse>() {
                public void handleResponse(javax.xml.ws.Response<simulationRef.RequestOperationResponse> response) {
                    try {
                        // TODO : log response
                        //writeSimulationEvent(AgentType.CONSUMER , EventType.RESPONSE_RECEIVED);
                        System.out.println("Result :\n "+ response.get().getReturn());
                    } catch(Exception ex) {
                        // TODO handle exception
                    }
                }
            };
            
            requestData = Utils.getDummyString(reqPayloadSize, 'A');

            // Call web service asynchronously (callback)
            java.util.concurrent.Future<? extends java.lang.Object> callBackResult = port.requestOperationAsync(producerId, requestId, requestData, producerConf.getResponseTime(), producerConf.getResponseSize(), asyncHandler);
            /*
            while(!callBackResult.isDone()) {
                // do something                
                Thread.sleep(100);
            }
             */
          
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }

    }

    

    public static void main(String[] args) {
        int requestPayloadSize = 16;
        String producerId = "producer1";
        String consumerId = "consumer1";
        // Test ConsumerEntity
        ConsumerEntity cons = new ConsumerEntity();

        SimulationScenario ss = new SimulationScenario();
        ProducerConfiguration pc = new ProducerConfiguration();
        pc.setName(producerId);
        pc.setWsAddress("http://localhost:8090/ESBTestCompositeService1/casaPort1");
        pc.setResponseSize(32);
        pc.setResponseTime(1000);
        ss.getAgentsconfiguration().add(pc);

        ss.addStep(new SimulationStep(consumerId, producerId, 0, 2, 1, 16));
        //ss.addStep(new SimulationStep(consumerId, producerId, 0, 1000, 1, 16));
        //ss.addStep(new SimulationStep(consumerId, producerId, 0, 1000, 1, 16));
        //ss.addStep(new SimulationStep(consumerId, producerId, 0, 1000, 1, 16));
        cons.configureConsumer(consumerId, ss);

        //cons.initializeAdressesTable();

        //cons.sendRequest("id", 0, requestPayloadSize);
        //cons.sendAsyncRequest(producerId, 0, requestPayloadSize);
        cons.startSimulation();
        //System.out.println("after\n ");
        //while(true);
    }
}

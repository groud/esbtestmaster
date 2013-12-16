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
    private Hashtable<String, String> hashWSAddresses;
    private SortedSet<ResultEvent> events;
    private SimulationThread simulationThread;

    /**
     * Configure the simulation scenario and id
     * @param id
     * @param simulationScenario
     */
    public void configureConsumer(String id, SimulationScenario simulationScenario) {
        this.simulationScenario = simulationScenario;
        this.setId(id);

        hashWSAddresses = this.initializeAdressesTable();
    }

    @Override
    public void startSimulation() {
        //TODO : add receive response code
        simulationThread = new SimulationThread();
        simulationThread.start();
    }

    @Override
    public void abortSimulation() {
        simulationThread.stop();
    }

    private Hashtable<String, String> initializeAdressesTable() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        for (int i = 0; i < this.simulationScenario.getAgentsconfiguration().size(); i++) {
            hashtable.put(this.simulationScenario.getAgentsconfiguration().get(i).getName(), this.simulationScenario.getAgentsconfiguration().get(i).getWsAddress());
        }
        return hashtable;
    }

    private void writeSimulationEvent(AgentType agent, EventType event) {
        ResultEvent currentEvent = new ResultEvent();
        currentEvent.setAgentId(this.getid());
        currentEvent.setAgentType(agent);
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        currentEvent.setEventDate(0);// TODOOOO
        currentEvent.setEventType(event);

        //add in list of events
        events.add(currentEvent);
    }

    private class SimulationThread extends Thread {

        private Timer timer;
        private SimulationStep step;

        public void run() {
            int i;
            //send request
            if (simulationScenario == null) {
                for (i = 0; i < simulationScenario.getSteps().size(); i++) {
                    step = simulationScenario.getSteps().get(i);
                    timer = new Timer();
                    long nbRequest = (long) (step.getBurstDuration() * step.getBurstRate());
                    //interval between two request
                    long period = (long) (step.getBurstDuration() / nbRequest);

                    //configure timer,and start senario
                    timer.scheduleAtFixedRate(new TimerTask() {

                        public void run() {
                            //code send request
                            // ****************************************************
                            // TODO : store respTime and respSize for each producer
                            // instead of sending the same values everytime
                            // ****************************************************
                            int respTime = 1000; // ms
                            int respSize = 32; // bytes

                            sendRequest(step.getProviderID(), step.getDataPayloadSize(), respTime, respSize);
                        }
                    }, step.getBurstStartDate(), period);
                }
            } else {
                System.out.println("steps have not been configured");
            }
        }
    }

    private void sendRequest(String producerId, int reqPayloadSize, int respTime, int respSize) {
        String requestData = null;

        try { // Call Web Service Operation
            simulationRef.SimulationWSService service = new simulationRef.SimulationWSService();
            simulationRef.SimulationWS port = service.getSimulationWSPort();

            // Dynamic URL binding;
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.hashWSAddresses.get(producerId));

            // Fill the same answer String with reqPayloadSize characters
            if (reqPayloadSize > 0) {
                char[] array = new char[respSize];
                Arrays.fill(array, 'A');
                requestData = new String(array);
            }

            // TODO : log events before and after request
            //java.lang.String result = port.requestOperation(producerId, requestData, respTime, respSize);
            //System.out.println("Result = "+result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    public static void main(String[] args) {
        // Test ConsumerEntity
        ConsumerEntity cons = new ConsumerEntity();

        SimulationScenario ss = new SimulationScenario();
        ProducerConfiguration pc = new ProducerConfiguration();
        pc.setName("id");
        pc.setWsAddress("http://localhost:8090/ESBTestCompositeService1/casaPort1");
        ss.getAgentsconfiguration().add(pc);
        cons.configureConsumer("lol", ss);

        cons.initializeAdressesTable();

        cons.sendRequest("id", 16, 1000, 32);
    }
}

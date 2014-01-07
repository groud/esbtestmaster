/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import datas.*;
import interfaces.ProducerWSListener;

/**
 *
 * @author mariata
 */
public class ProducerEntity extends SimulationEntity implements ProducerWSListener {

    private ResultSet resultSet;
    private boolean simulationDone = true;

    /**
     * Start the simulation
     */
    public void startSimulation() {
        resultSet = new ResultSet();
        SimulationWS.setListener(this);
        simulationDone = false;
    }

    /**
     * Abort the simulation
     */
    public void abortSimulation() {
        simulationDone = true;
    }

    /**
     * End the simulation and send the results
     */
    public void endOfSimlation() {
        simulationDone = true;
        listener.simulationDone(resultSet);
    }

    /**
     * Log a requestReceived event
     * @param requestId
     */
    public void requestReceived(int requestId, String requestData, long respTime, int respSize) {
        if(!simulationDone) {
            ResultSimulationEvent event = new ResultSimulationEvent();
            event.setAgentId(this.getid());
            event.setAgentType(AgentType.PRODUCER);
            event.setEventDate(0);//TODO Modify to the real date
            event.setEventType(EventType.REQUEST_RECEIVED);
            resultSet.getEvents().add(event);
        }
    }

    /**
     * Log a responseSent event
     * @param requestId
     */
    public void responseSent(int requestId) {
        if(!simulationDone) {
            ResultSimulationEvent event = new ResultSimulationEvent();
            event.setAgentId(this.getid());
            event.setAgentType(AgentType.PRODUCER);
            event.setEventDate(0);//TODO Modify to the real date
            event.setEventType(EventType.RESPONSE_SENT);
            resultSet.getEvents().add(event);
        }
    }
}

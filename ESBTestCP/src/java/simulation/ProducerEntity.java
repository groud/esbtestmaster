/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import datas.*;

/**
 *
 * @author mariata
 */
public class ProducerEntity extends SimulationEntity {

    private ResultSet resultSet;
    private ResultEvent currentEvent;
    private Thread simulationThread;

    /**
     * A thread listening to events from the simulation
     */
    private class SimulationThread extends Thread {

        @Override
        public void run() {
            //TODO : Listen request,and grab consumerID, when a request arrive, send response
        }
    }

    /**
     * Start the simulation
     */
    public void startSimulation() {
        resultSet = new ResultSet();
        simulationThread = new SimulationThread();
        simulationThread.start();
    }

    /**
     * Abort the simulation
     */
    public void abortSimulation() {
        simulationThread.stop();
    }

    /**
     * End the simulation and send the results
     */
    public void endOfSimlation() {
        simulationThread.stop();
        listener.simulationDone(resultSet);
    }

    /**
     * Sends a reponse to a consumer
     * @param consumerID
     * @param dataPayload
     */
    private void sendResponse(String consumerID, float dataPayload) {
        // TODO : Generate fake payload, send response,
        writeSimulationEvent(AgentType.PRODUCER, EventType.RESPONSE_SENT);
    }

    /**
     * Log an event
     * @param agent
     * @param event
     */
    private void writeSimulationEvent(AgentType agent, EventType event) {
        currentEvent.setAgentId(this.getid());
        currentEvent.setAgentType(agent);
        //TODO: Set the date to current milliseconds
        currentEvent.setEventDate(0);
        currentEvent.setEventType(event);

        //add in list of events
        resultSet.getEvents().add(currentEvent);
    }
}

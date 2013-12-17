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
    private boolean abortSimulation = false;
    
    ResultSet resultSet;
    private ResultEvent currentEvent;

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
     * Start the simulation
     */
    public void startSimulation() {
        String consumerID = null;
        float dataPayload = 0;
        if (!abortSimulation) {
            //TODO : Listen request,and grab consumerID, when a request arrive, send response
            //Demarrer un thread en ecoute plutôt !!!!
            sendResponse(consumerID, dataPayload);
        }
    }

    /**
     * Abort the simulation
     */
    public void abortSimulation() {
        //
        abortSimulation = true;
    }

    /**
     * TODO : change return type to byte[] or a Response object
     * @param reqType
     * @param reqData
     * @return
     */
    public String processRequest(char reqType, String reqData) {
        return "DUMMY";
    }

   //A REVOIR : proposition lever un timer au niveau du controller et dire au producer que c'est fini a la fin de la simulation
   public void endOfSimlation() {

        //TODO : send the results to the agent controller, wait the end of all thread
        // listener.simulationDone(resultSet);
    }
}

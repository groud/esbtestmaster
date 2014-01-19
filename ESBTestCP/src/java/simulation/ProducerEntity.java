package simulation;

import datas.*;
import interfaces.ProducerWSListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ProduceEntity is resposible for running the simulation as a producer.
 * It uses a ResultLogger to log events, listening to a web service class (SimulationWS)
 */
public class ProducerEntity extends SimulationEntity implements ProducerWSListener {
    private boolean simulationDone = true;

    /**
     * Returns a ProducerEntity instance
     * @param agentId
     */
    public ProducerEntity(String agentId) {
        super(agentId);
    }

    /**
     * Starts the simulation
     */
    public void startSimulation() {
        SimulationWS.setListener(this);
        simulationDone = false;
    }

    /**
     * Aborts the simulation
     */
    public void abortSimulation() {
        simulationDone = true;
    }

    /**
     * Ends the simulation, forcig the ProducerEntity to send the results
     */
    public void endOfSimulation() {
        simulationDone = true;
        listener.simulationDone(logger.getResultSet());
    }

    /**
     * Logs a requestReceived event
     * @param requestId
     */
    public void requestReceived(int requestId, String requestData, long respTime, int respSize) {
        if (!simulationDone) {
            try {
                logger.writeSimulationEvent(requestId, AgentType.PRODUCER, EventType.REQUEST_RECEIVED);
            } catch (Exception ex) {
                Logger.getLogger(ProducerEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Logs a responseSent event
     * @param requestId
     */
    public void responseSent(int requestId) {
        if (!simulationDone) {
            try {
                logger.writeSimulationEvent(requestId, AgentType.PRODUCER, EventType.RESPONSE_SENT);
            } catch (Exception ex) {
                Logger.getLogger(ProducerEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

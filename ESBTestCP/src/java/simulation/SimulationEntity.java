package simulation;

import interfaces.SimulationMessageListener;

/**
 * A SimulationEntity is responsible for the behaviour of an agent
 */
public abstract class SimulationEntity {
    private String id;
    protected ResultsLogger logger;
    
    SimulationMessageListener listener;

    public SimulationEntity(String id) {
        logger = new ResultsLogger(id);
        this.id = id;
    }

    /**
     * Set the listener for the simulation events
     * @param listener
     */
    public void setListener(SimulationMessageListener listener) {
        this.listener = listener;
    }

    /**
     * Set the agent ID.
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the agent ID.
     * @return
     */
    public String getid() {
        return this.id;
    }

    /**
     * Start the simulation
     */
    public abstract void startSimulation();
    
    /**
     * Abort the simulation
     */
    public abstract void abortSimulation();
}

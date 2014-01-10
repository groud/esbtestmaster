/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import interfaces.SimulationMessageListener;

/**
 *
 * @author root
 */
public abstract class SimulationEntity {
    private String id;
    SimulationMessageListener listener;

    public SimulationEntity(String id) {
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import datas.*;
import interfaces.SimulationMessageListener;

/**
 *
 * @author root
 */
public abstract class SimulationEntity {

    private String id;
    
    SimulationMessageListener listener;

    public void setListener(SimulationMessageListener listener) {
        this.listener = listener;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getid() {
        return this.id;
    }

    public abstract void startSimulation();
    public abstract void abortSimulation();
}

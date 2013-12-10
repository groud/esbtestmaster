/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

/**
 *
 * @author root
 */
public class ConsumerEntity implements Simulation {

private ArrayList<SimulationStep> steps;
private int id;

    public ConsumerEntity(ArrayList<SimulationStep> steps, int id) {
        this.steps = steps;
        this.id = id;
    }



    public void startSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stopSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setParameters() {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}

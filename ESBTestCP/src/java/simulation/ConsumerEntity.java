/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

import java.util.ArrayList;

import datas.*;
/**
 *
 * @author mariata
 */
public class ConsumerEntity extends SimulationEntity {


    private ArrayList<SimulationStep> steps;

    public ConsumerEntity() {

       
    }

public void configureConsumer( ArrayList<SimulationStep> steps) {

      this.steps = steps;
}


    @Override
    public void startSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void abortSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

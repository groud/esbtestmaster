/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

/**
 *
 * @author root
 */




public class Provider implements Simulation {

    private int id;
    private float responseTime;
    private float responseSize;


      public Provider(int id, float responseTime, float responseSize) {
        this.id = id;
        this.responseTime = responseTime;
        this.responseSize = responseSize;
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

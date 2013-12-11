/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package simulation;

/**
*
* @author mariata
*/
public class ProducerEntity extends SimulationEntity {

    private float responseTime;
    private float responseSize;

    //constructor
    public ProducerEntity() {


    }

    public void configureProducer(int responseTime, int messageLength){

        this.responseTime=responseTime;
        this.responseSize=messageLength;

    }

    public void startSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void abortSimulation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
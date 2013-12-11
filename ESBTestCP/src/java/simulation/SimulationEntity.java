/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package simulation;

/**
*
* @author root
*/
public abstract class SimulationEntity {

private int id;

public void setId( int id){

    this.id= id;
}

public abstract void startSimulation();
public abstract void abortSimulation();
}

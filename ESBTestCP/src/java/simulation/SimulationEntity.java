/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package simulation;

import datas.*;

/**
*
* @author root
*/
public abstract class SimulationEntity {

private int id;

public void setId( int id){

    this.id= id;

}

public int getid(){

return this.id;

}

public abstract void startSimulation();

public abstract void abortSimulation();

public abstract void writeSimulationEvent(AgentType agent, EventType event);

}
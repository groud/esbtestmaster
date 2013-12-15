/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monitoring;

import datas.AgentConfiguration;
import datas.SimulationScenario;

/**
 *
 * @author bambaLamine
 */
public class MessageFromJMSEntity_TEST {

    private AgentConfiguration receiverAgent;
    private SimulationScenario simulationScenario;
    private int messageType;
    private int responseTime;
    private int responseSize;

    public AgentConfiguration getAgent(){
        return this.receiverAgent;

    }

   public SimulationScenario getScenario(){
        return this.simulationScenario;

    }
   public int getMessageType(){
        return this.messageType;

   }
   public int getResponseTime(){
        return this.responseTime;

    }
   public int getResponseSize(){
        return this.responseSize;

    }

     public void setAgent(AgentConfiguration a){
       this.receiverAgent=a;

    }

   public void setScenario(SimulationScenario s){
        this.simulationScenario=s;

    }
   public  void setMessageType(int m){
       this.messageType=m;

   }
   public void setResponseTime(int r){
        this.responseTime=r;

    }
   public void setResponseSize(int size){
       this.responseSize=size;

    }




}

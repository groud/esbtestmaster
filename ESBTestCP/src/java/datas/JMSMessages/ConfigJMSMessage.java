/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas.JMSMessages;
import datas.AgentConfiguration;
import datas.SimulationScenario;
import java.io.Serializable;

/**
 *
 * @author BambaLamine
 */
public class ConfigJMSMessage implements Serializable{
    private AgentConfiguration agent;
    private SimulationScenario scenario;

    public AgentConfiguration getAgentConfiguration(){
        return this.agent;
    }

    public SimulationScenario getScenario(){

        return this.scenario;
    }

}



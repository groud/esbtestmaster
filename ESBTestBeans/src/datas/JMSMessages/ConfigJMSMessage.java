/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas.JMSMessages;
import datas.AgentConfiguration;
import datas.SimulationScenario;

/**
 *
 * @author BambaLamine
 */
public class ConfigJMSMessage extends JMSAddressedMessage{
    private AgentConfiguration agentConfiguration;
    private SimulationScenario scenario;

    public AgentConfiguration getAgentConfiguration() {
        return agentConfiguration;
    }

    public void setAgentConfiguration(AgentConfiguration agentConfiguration) {
        this.agentConfiguration = agentConfiguration;
    }

    public SimulationScenario getScenario() {
        return scenario;
    }

    public void setScenario(SimulationScenario scenario) {
        this.scenario = scenario;
    }
}



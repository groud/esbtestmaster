package datas.JMSMessages;

import datas.AgentConfiguration;
import datas.SimulationScenario;

/**
 * A JMS message to ask an agent to configure
 */
public class ConfigJMSMessage extends JMSAddressedMessage {

    private AgentConfiguration agentConfiguration;
    private SimulationScenario scenario;

    /**
     * Returns the agent cofiguration.
     * @return
     */
    public AgentConfiguration getAgentConfiguration() {
        return agentConfiguration;
    }

    /**
     * Sets the agent configuration.
     * @param agentConfiguration
     */
    public void setAgentConfiguration(AgentConfiguration agentConfiguration) {
        this.agentConfiguration = agentConfiguration;
    }

    /**
     * Returns the simulation scenario.
     * @return
     */
    public SimulationScenario getScenario() {
        return scenario;
    }

    /**
     * Sets the simulation scenario.
     * @param scenario
     */
    public void setScenario(SimulationScenario scenario) {
        this.scenario = scenario;
    }
}



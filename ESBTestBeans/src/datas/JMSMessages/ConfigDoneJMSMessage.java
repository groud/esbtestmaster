package datas.JMSMessages;

import java.io.Serializable;

/**
 * A JMS message sent by an agent to specify that a donfiguration is done.
 */
public class ConfigDoneJMSMessage implements Serializable {

    private String agentId;

    /**
     * Create a configuration done message
     * @param agentId
     */
    public ConfigDoneJMSMessage(String agentId) {
        this.agentId = agentId;
    }

    /**
     * Get the agent ID
     * @return agentId
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Set the agent ID
     * @param agentId
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}

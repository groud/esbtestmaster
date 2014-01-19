package datas;

import java.io.Serializable;

/**
 * Describes an agent configuration
 */
public abstract class AgentConfiguration implements Serializable {
    protected AgentType agentType;
    private String agentId;

    /**
     * Returns the agent identifier.
     * @return
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets the agent identifier.
     * @return
     */
    public void setAgentId(String name) {
        this.agentId = name;
    }

    /**
     * Returns a String representation of the configuration.
     * @return The String representation of this object.
     */
    @Override
    public String toString() {
        return agentType + " " + this.getAgentId()+"\n";
    }
}

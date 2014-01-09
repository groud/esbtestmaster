/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas.JMSMessages;

/**
 *
 * @author Adrien
 */
public class ConfigDoneJMSMessage {
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

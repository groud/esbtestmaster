package datas.JMSMessages;

import java.io.Serializable;

/**
 * A JMS message used to notify the master that an error occured.
 */
public class FatalErrorOccuredJMSMessage implements Serializable {
    private String agentId;
    private String message;

    /**
     * Returns FatalErrorOccuredJMSMessage instance
     * @param agentId
     * @param message
     */
    public FatalErrorOccuredJMSMessage(String agentId, String message) {
        this.agentId = agentId;
        this.message = message;
    }

    /**
     * Sets the agent identifier
     * @param agentId
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    /**
     * Returns the agent identifier
     * @return
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets a message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the message
     * @return
     */
    public String getMessage() {
        return message;
    }

}

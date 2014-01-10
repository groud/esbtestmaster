/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas.JMSMessages;

import java.io.Serializable;

/**
 *
 * @author gilles
 */
public class FatalErrorOccuredJMSMessage implements Serializable {

    private String agentId;
    private String message;

    public FatalErrorOccuredJMSMessage(String agentId, String message) {
        this.agentId = agentId;
        this.message = message;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

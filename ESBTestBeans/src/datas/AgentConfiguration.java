package datas;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author gilles
 */
public abstract class AgentConfiguration implements Serializable {
    protected AgentType agentType;

    private String agentId;
    private String wsAddress;


    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String name) {
        this.agentId = name;
    }

    public String getWsAddress() {
        return wsAddress;
    }

    public void setWsAddress(String wsAddress) {
        this.wsAddress = wsAddress;
    }

    @Override
    public String toString() {
        return agentType + " " + this.getAgentId() + " at "+ this.getWsAddress()+"\n";
    }
}

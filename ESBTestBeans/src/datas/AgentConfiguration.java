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



    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String name) {
        this.agentId = name;
    }

    @Override
    public String toString() {
        return agentType + " " + this.getAgentId()+"\n";
    }
}

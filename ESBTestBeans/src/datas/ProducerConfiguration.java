package datas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author gilles
 */
public class ProducerConfiguration extends AgentConfiguration {
    private String wsAddress;

    public ProducerConfiguration(){
        this.agentType = AgentType.PRODUCER;
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

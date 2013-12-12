/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas;

/**
 *
 * @author gilles
 */
public abstract class AgentConfiguration {
    protected AgentType agentType;

    private String name;
    private String wsAddress;
    private String monitoringWSAddress;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getMonitoringWSAddress() {
        return monitoringWSAddress;
    }

    public void setMonitoringWSAddress(String monitoringWSAddress) {
        this.monitoringWSAddress = monitoringWSAddress;
    }

    public String getWsAddress() {
        return wsAddress;
    }

    public void setWsAddress(String wsAddress) {
        this.wsAddress = wsAddress;
    }

    @Override
    public String toString() {
        return agentType + " " + this.getName() + "\n";
    }
}

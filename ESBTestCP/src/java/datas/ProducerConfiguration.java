/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas;

/**
 *
 * @author gilles
 */
public class ProducerConfiguration extends AgentConfiguration {
    private int responseTime;
    private int responseSize;  

    public ProducerConfiguration() {
        this.agentType = AgentType.PRODUCER;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

     public int getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(int responseSize) {
        this.responseSize = responseSize;
    }

}

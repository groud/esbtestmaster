/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ESBTestCP;

import datas.AgentConfiguration;
import datas.SimulationScenario;
import interfaces.MonitoringMessageHandler;
import interfaces.MonitoringMsgListener;

/**
 *
 * @author gilles
 */
public class JMSHandler implements MonitoringMessageHandler {
    MonitoringMsgListener mmListener;

    public void setListener(MonitoringMsgListener mmListener) {
        this.mmListener = mmListener;
    }

    public void startSimulationMessage(AgentConfiguration receiverAgent) {
        //TODO JMS message
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stopSimulationMessage(AgentConfiguration receiverAgent) {
        //TODO JMS message
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void configurationMessage(AgentConfiguration receiverAgent, SimulationScenario simulationScenario) {
        //TODO JMS message
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}

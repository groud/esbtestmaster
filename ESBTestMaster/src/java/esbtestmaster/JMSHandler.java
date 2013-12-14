/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

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

    /**
     * Asks an agent to start the simulation
     * @param receiverAgent
     */
    public void startSimulationMessage(AgentConfiguration receiverAgent) {
        //TODO Start JMS message to the receiverAgent
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Asks an agent to abort the simulation
     * @param receiverAgent
     */
    public void stopSimulationMessage(AgentConfiguration receiverAgent) {
        //TODO Stop JMS message to the receiverAgent
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Asks an agent to self configure withthe given AgentConfiguration
     * @param receiverAgent
     * @param simulationScenario
     */
    public void configurationMessage(AgentConfiguration receiverAgent, SimulationScenario simulationScenario) {
        //TODO  Configuration JMS message to the receiverAgent
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}

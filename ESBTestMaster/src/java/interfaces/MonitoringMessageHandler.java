/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import datas.AgentConfiguration;
import datas.SimulationScenario;

/**
 * MonitoringMessageHandler
 */
public interface MonitoringMessageHandler {
    public void startSimulationMessage(String receiver);
    public void abortSimulationMessage(String receiver);
    public void endSimulationMessage(String receiver);
    public void configurationMessage(String receiver, AgentConfiguration receiverAgent, SimulationScenario simulationScenario);
}

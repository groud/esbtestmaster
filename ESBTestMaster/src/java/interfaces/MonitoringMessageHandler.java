/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import datas.AgentConfiguration;
import datas.ProducerConfiguration;
import datas.SimulationScenario;

/**
 *
 * @author gilles
 */
public interface MonitoringMessageHandler {
    public void startSimulationMessage(AgentConfiguration receiverAgent);
    public void stopSimulationMessage(AgentConfiguration receiverAgent);
    public void endSimulationMessage(ProducerConfiguration receiverAgent);
    public void configurationMessage(AgentConfiguration receiverAgent, SimulationScenario simulationScenario);
}

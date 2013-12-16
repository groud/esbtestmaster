/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import datas.AgentConfiguration;
import datas.SimulationScenario;

/**
 *
 * @author gilles
 */
public interface MonitoringMessageListener {
   public void startSimulationMessage();
   public void stopSimulationMessage();
   public void configurationMessage(AgentConfiguration receiverAgent, SimulationScenario simulationScenario);
}

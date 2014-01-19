package interfaces;

import datas.AgentConfiguration;
import datas.SimulationScenario;

/**
 * MonitoringMessageListener
 */
public interface MonitoringMessageListener {
   public void startSimulationMessage();
   public void endSimulationMessage();
   public void abortSimulationMessage();
   public void configurationMessage(AgentConfiguration receiverAgent, SimulationScenario simulationScenario);
}

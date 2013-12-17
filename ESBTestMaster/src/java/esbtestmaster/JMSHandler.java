/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import datas.AgentConfiguration;

import datas.JMSMessages.*;

import datas.ProducerConfiguration;
import datas.SimulationScenario;
import interfaces.MonitoringMessageHandler;
import interfaces.MonitoringMsgListener;

/**
 *
 * @author gilles
 */
public class JMSHandler implements MonitoringMessageHandler, Runnable {
    MonitoringMsgListener mmListener;
    //public void JMSHandler(){
    //    MasterMessageHandler masterMessageHandler = new MasterMessageHandler();
    //}

    /**
     * Sets a listener for the monitoring messages
     * @param mmListener
     */
    public void setListener(MonitoringMsgListener mmListener) {
        this.mmListener = mmListener;
    }


    // -------------------------------
    //   INTERFACES IMPLEMENTATIONS
    // -------------------------------
    /**
     * Asks an agent to start the simulation
     * @param receiverAgent
     */
    public void startSimulationMessage(AgentConfiguration receiverAgent) {
        StartJMSMessage startJMSMessage = new StartJMSMessage();
        MasterMessageHandler masterMessageHandler = new MasterMessageHandler();
        masterMessageHandler.sendToTopic(startJMSMessage);
        //TODO Start JMS message to the receiverAgent
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Asks an agent to abort the simulation
     * @param receiverAgent
     */
    public void stopSimulationMessage(AgentConfiguration receiverAgent) {
     StopJMSMessage stopJMSMessage = new StopJMSMessage();
     MasterMessageHandler masterMessageHandler = new MasterMessageHandler();
     masterMessageHandler.sendToTopic(stopJMSMessage);
        //TODO Stop JMS message to the receiverAgent
        //throw new UnsupportedOperationException("Not supported yet.");
    }


    public void endSimulationMessage(ProducerConfiguration receiverAgent) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Asks an agent to self configure withthe given AgentConfiguration
     * @param receiverAgent
     * @param simulationScenario
     */
    public void configurationMessage(AgentConfiguration receiverAgent, SimulationScenario simulationScenario) {
        ConfigJMSMessage configJMSMessage = new ConfigJMSMessage();
        configJMSMessage.putAgentConfiguration(receiverAgent);
        configJMSMessage.putScenario(simulationScenario);
        MasterMessageHandler masterMessageHandler = new MasterMessageHandler();
        masterMessageHandler.sendToTopic(configJMSMessage);

        //TODO  Configuration JMS message to the receiverAgent
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    // -------------------------------
    //   RUNNABLE
    // -------------------------------
    /**
     * Runs a JMS Messages listening thread
     */
    public void run() {
        //TODO Ecouter les messages et les envoie au MasterController avec :
        //mmListener.simulationDoneForOneAgent(null, null);
        //mmListener.fatalErrorOccured(null, null);
    }


}

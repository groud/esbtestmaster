/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esbtestmaster;

import datas.JMSMessages.*;
import datas.*;
import JMS.*;
import interfaces.*;
import java.io.Serializable;

/**
 *
 * @author gilles
 */
public class MasterMessageHandler implements MonitoringMessageHandler, Runnable {

    MonitoringMsgListener mmListener;
    private JMSEntity jms;

    public MasterMessageHandler(MonitoringMsgListener mmListener) {
        jms = new JMSEntity(DestinationName.CONNECTION_FACTORY, DestinationName.CONFIG_DESTINATION, DestinationName.RESULTS_DESTINATION);

        this.mmListener = mmListener;
    }

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
    public void startSimulationMessage(String receiverId) {
        StartJMSMessage startJMSMessage = new StartJMSMessage();
        startJMSMessage.setReceiver(receiverId);
        jms.send(startJMSMessage);
    }

    /**
     * Asks an agent to abort the simulation
     * @param receiverAgent
     */
    public void abortSimulationMessage(String receiverId) {
        AbortJMSMessage abortJMSMessage = new AbortJMSMessage();
        abortJMSMessage.setReceiver(receiverId);
        jms.send(abortJMSMessage);
    }

    /**
     * Sends a message to a producer notifying that the simulation is over.
     * The producer should then send it results.
     * @param receiverAgent
     */
    public void endSimulationMessage(String receiverId) {
        EndJMSMessage endJMSMessage = new EndJMSMessage();
        endJMSMessage.setReceiver(receiverId);
        jms.send(endJMSMessage);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Asks an agent to self configure withthe given AgentConfiguration
     * @param receiverAgent
     * @param simulationScenario
     */
    public void configurationMessage(String receiver, AgentConfiguration receiverAgent, SimulationScenario simulationScenario) {
        ConfigJMSMessage configJMSMessage = new ConfigJMSMessage();
        configJMSMessage.setAgentConfiguration(receiverAgent);
        configJMSMessage.setScenario(simulationScenario);
        configJMSMessage.setReceiver(receiver);
        jms.send(configJMSMessage);
    }

    // -------------------------------
    //   RUNNABLE
    // -------------------------------
    /**
     * Runs a JMS Messages listening thread.
     */
    public void run() {
        while (true) {
            Serializable message;
            message = jms.receive();
            if (message instanceof ConfigDoneJMSMessage) {
                //System.out.println("JMSHandler : Received a ConfigDoneJMSMessage");
                ConfigDoneJMSMessage myMessage = (ConfigDoneJMSMessage) message;
                mmListener.configurationDoneForOneAgent(myMessage.getAgentId());
            } else if (message instanceof SimulationDoneJMSMessage) {
                SimulationDoneJMSMessage myMessage = (SimulationDoneJMSMessage) message;
                mmListener.simulationDoneForOneAgent(myMessage.getAgentId(), myMessage.getResultSet());
            } else if (message instanceof FatalErrorOccuredJMSMessage) {
                FatalErrorOccuredJMSMessage myMessage = (FatalErrorOccuredJMSMessage) message;
                mmListener.fatalErrorOccured(myMessage.getAgentId(), myMessage.getMessage());
            }
        }
    }
}

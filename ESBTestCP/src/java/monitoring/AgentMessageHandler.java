/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoring;

import JMS.*;
import datas.JMSMessages.*;
import datas.ResultSet;
import interfaces.MonitoringMessageHandler;
import interfaces.MonitoringMessageListener;
import java.io.Serializable;

/**
 * The AgentMessageHandler runs a two-way interface to communicate with the Agents, through a JMS server.
 * It needs 2 JMS topics named : "config" and "results"
 */
public class AgentMessageHandler implements MonitoringMessageHandler, Runnable {

    MonitoringMessageListener listener;
    private JMSEntity jms;
    private String agentId;

    /**
     * Returns an instance of a JMSHandler, then start a listening thread for JMS messages
     * @param agentId the receiver agentId. This id should fit the curren agentId for the message to be interpreted.
     */
    public AgentMessageHandler(String agentId) {
        this.agentId = agentId;
        jms = new JMSEntity(JMSEntity.CONNECTION_FACTORY, JMSEntity.RESULTS_DESTINATION, JMSEntity.CONFIG_DESTINATION);
    }

    /**
     * Set a listener for monitoring messages.
     * @param listener
     */
    public void setListener(MonitoringMessageListener listener) {
        this.listener = listener;
    }

    // -------------------------------
    //   INTERFACES IMPLEMENTATIONS
    // -------------------------------
    /**
     * Send a configuration done message to the master
     * @param agentId The sending agent Id.
     */
    public void configurationDone(String agentId) {
        jms.send(new ConfigDoneJMSMessage(agentId));
    }

    /**
     * Sends a message to the master with the simulation results.
     * @param resultSet
     */
    public void simulationDone(String agentId, ResultSet resultSet) {
        //TODO JMS: Envoyer le message JMS au master avec le résultat
        if (jms.send(new SimulationDoneJMSMessage(resultSet, agentId))) {
            System.out.println("Results sent!");
        } else {
            System.out.println("Error while sending results to the master");
        }
    }

    /**
     * Sends a message to the master notifying that a fatal error occured
     * @param agentId The sending agent Id.
     * @param message The error message
     */
    public void fatalErrorOccured(String agentId, String message) {
        //TODO JMS: Envoyer le message JMS au master pour indiquer que la simulation n'a pu être terminée
        jms.send(new FatalErrorOccuredJMSMessage(agentId, message));
    }

    /**
     * Listen to the messages then send them to the controller
     */
    public void run() {
        //TODO JMS : Ecouter les messages et les envoie au MasterController avec :
        while (true) {
            Serializable message;
            message = jms.receive();
            
            if (((JMSAddressedMessage) message).getReceiver().equals(this.agentId)) {
                if (message instanceof ConfigJMSMessage) {
                    //System.out.println("JMSHandler: Received a configJMSMessage");
                    ConfigJMSMessage myMessage = (ConfigJMSMessage) message;
                    listener.configurationMessage(myMessage.getAgentConfiguration(), myMessage.getScenario());
                } else if (message instanceof StartJMSMessage) {
                    //System.out.println("JMSHandler: Received a startJMSMessage");
                    listener.startSimulationMessage();
                } else if (message instanceof AbortJMSMessage) {
                    //System.out.println("JMSHandler: Received an abortSimulationMessage");
                    listener.abortSimulationMessage();
                } else if (message instanceof EndJMSMessage) {
                    //System.out.println("JMSHandler: Received an endSimulaitonMessage");
                    listener.endSimulationMessage();
                }
            }
        }
    }
}



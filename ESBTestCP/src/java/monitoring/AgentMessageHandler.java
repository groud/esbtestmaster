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
 *
 * @author bambaLamine
 */
public class AgentMessageHandler implements MonitoringMessageHandler, Runnable {

    MonitoringMessageListener listener;
    private JMSEntity jms;

    /**
     * Returns an instance of a JMSHandler, then start a listening thread for JMS messages
     */
    public AgentMessageHandler() {
        //TODO JMS: Créer un thread en écoute des messages JMS. En fonction de ceux-ci, prévenir le controller à l'aide de :
        //listener.configurationMessage(config, config);
        //listener.startMessage()
        //listener.abortMessage()
        //listener.endMessage()
        jms = new JMSEntity(DestinationName.CONNECTION_FACTORY, DestinationName.RESULTS_DESTINATION, DestinationName.CONFIG_DESTINATION);
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
     * @param agentId
     */
    public void configurationDone(String agentId)
    {
        jms.send(new ConfigDoneJMSMessage(agentId));
    }


    /**
     * Sends a message to the master with the simulation results.
     * @param resultSet
     */
    public void simulationDone(String agentId, ResultSet resultSet) {
        //TODO JMS: Envoyer le message JMS au master avec le résultat
        if (jms.send(new SimulationDoneJMSMessage(resultSet, agentId)))
            System.out.println("Results sent!");
        else
            System.out.println("Error while sending results to the master");
    }

    /**
     * Sends a message to the master notifying that a fatal error occured
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
        
        while(true){
        
        Serializable message;
        message = jms.receive();

        if (message instanceof ConfigJMSMessage) {
            System.out.println("JMSHandler: Received a configJMSMessage");
            ConfigJMSMessage myMessage = (ConfigJMSMessage) message;
            listener.configurationMessage(myMessage.getAgentConfiguration(), myMessage.getScenario());
        } else if (message instanceof StartJMSMessage) {
            System.out.println("JMSHandler: Received a startJMSMessage");
            listener.startSimulationMessage();
        } else if (message instanceof AbortJMSMessage) {
            System.out.println("JMSHandler: Received an abortSimulationMessage");
            listener.abortSimulationMessage();
        } else if (message instanceof EndJMSMessage) {
            System.out.println("JMSHandler: Received an endSimulaitonMessage");
            listener.endSimulationMessage();
        }}
    }

}



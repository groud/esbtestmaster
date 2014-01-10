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
    public void startSimulationMessage(AgentConfiguration receiverAgent) {
        System.out.println("-----------startSimulationMessage-----------");
        StartJMSMessage startJMSMessage = new StartJMSMessage();
        jms.send(startJMSMessage);
        System.out.println("-----------startSimulationMessage-----------2");
        //TODO JMS : Start JMS message to the receiverAgent
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Asks an agent to abort the simulation
     * @param receiverAgent
     */
    public void abortSimulationMessage(AgentConfiguration receiverAgent) {
        AbortJMSMessage abortJMSMessage = new AbortJMSMessage();
        jms.send(abortJMSMessage);
        //TODO JMS : Abort JMS message to the receiverAgent
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Sends a message to a producer notifying that the simulation is over.
     * The producer should then send it results.
     * @param receiverAgent
     */
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
        configJMSMessage.setAgentConfiguration(receiverAgent);
        configJMSMessage.setScenario(simulationScenario);
        jms.send(configJMSMessage);


        //TODO JMS : Configuration JMS message to the receiverAgent
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    // -------------------------------
    //   RUNNABLE
    // -------------------------------
    /**
     * Runs a JMS Messages listening thread.
     */
    public void run() {
        //TODO JMS : Ecouter les messages et les envoie au MasterController avec :
        //mmListener.simulationDoneForOneAgent(null, null);
        //mmListener.fatalErrorOccured(null, null);
        
        while (true) {
            Serializable message;
            message = jms.receive();
            if (message instanceof ConfigDoneJMSMessage) {
                System.out.println("JMSHandler : Received a ConfigDoneJMSMessage");
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

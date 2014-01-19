package esbtestmaster;

import datas.JMSMessages.*;
import datas.*;
import JMS.*;
import interfaces.*;
import java.io.Serializable;

/**
 * The MasterMessageHandler runs a two-way interface to communicate with the Agents, through a JMS server.
 * It needs 2 JMS topics named : "config" and "results"
 */
public class MasterMessageHandler implements MonitoringMessageHandler, Runnable {

    MonitoringMsgListener mmListener;
    private JMSEntity jms;

    /**
     * Returns a MasterMessageHandler.
     * @param mmListener A listener that will be notified when a JMS message is received.
     */
    public MasterMessageHandler(MonitoringMsgListener mmListener) {
        jms = new JMSEntity(JMSEntity.CONNECTION_FACTORY, JMSEntity.CONFIG_DESTINATION, JMSEntity.RESULTS_DESTINATION);
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
     * Asks an agent to start the simulation, using a JMS message
     * @param receiverAgent
     */
    public void startSimulationMessage(String receiverId) {
        StartJMSMessage startJMSMessage = new StartJMSMessage();
        startJMSMessage.setReceiver(receiverId);
        jms.send(startJMSMessage);
    }

    /**
     * Asks an agent to abort the simulation, using a JMS message
     * @param receiverAgent
     */
    public void abortSimulationMessage(String receiverId) {
        AbortJMSMessage abortJMSMessage = new AbortJMSMessage();
        abortJMSMessage.setReceiver(receiverId);
        jms.send(abortJMSMessage);
    }

    /**
     * Notify a producer that the simulation is over, using a JMS message.
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
     * Asks an agent to self configure with the given AgentConfiguration, using a JMS message.
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// EN stand by, en attente du JMs
package monitoring;

import datas.AgentConfiguration;
import datas.ConsumerConfiguration;
import datas.ProducerConfiguration;
import datas.ResultSet;
import datas.SimulationScenario;
import interfaces.MonitoringMessageListener;
import interfaces.SimulationMessageListener;
import javax.servlet.ServletContextEvent;
import simulation.*;

/**
 *
 * @author BambaLamine
 */
public class AgentController implements MonitoringMessageListener, SimulationMessageListener, javax.servlet.ServletContextListener {

    private SimulationEntity simulationEntity;
    private AgentMessageHandler msgHandler;
    private Thread jmsThread;

    private String agentId;

    /**
     * Asks the simulationEntity to start the simulation
     */
    public void startSimulationMessage() {
        System.out.println("-- "+ agentId + " starting --");
        simulationEntity.startSimulation();
    }

    /**
     * Asks the simulationEntity to abort the simulation
     */
    public void abortSimulationMessage() {
        System.out.println("-- "+ agentId + " aborting --");
        simulationEntity.abortSimulation();
    }

    /**
     * Asks to the simulationEntity (configured as a producer) to end the simulation
     */
    public void endSimulationMessage() {
        System.out.println("-- "+ agentId + " stopping --");
        if (simulationEntity instanceof ProducerEntity) {
            ((ProducerEntity) simulationEntity).endOfSimulation();
        } 
    }

    /**
     * Configure the simulationEntity
     */
    public void configurationMessage(AgentConfiguration receiverAgent, SimulationScenario simulationScenario) {
        //On crée une simulationEntity correspondant à la configuration reçue
        System.out.println("-- "+ agentId + " received a configuration message --");
        //System.out.println("Id : "+receiverAgent.getAgentId());
        //System.out.println("WS address: "+ receiverAgent.getWsAddress());
        if (this.agentId.equals(receiverAgent.getAgentId())) {
            if (receiverAgent instanceof ConsumerConfiguration) {
                simulationEntity = new ConsumerEntity(this.agentId);
                simulationEntity.setListener(this);
                ((ConsumerEntity) simulationEntity).configureConsumer(simulationScenario);
            } else if (receiverAgent instanceof ProducerConfiguration) {
                simulationEntity = new ProducerEntity(this.agentId);
                simulationEntity.setListener(this);
            }
            configurationDone();
        }

    }

    /**
     * Notifies the master that the configuration is done. 
     */
    public void configurationDone()
    {
        System.out.println("-- "+ agentId + " configuration done --");
        msgHandler.configurationDone(this.agentId);
    }

    /**
     * Send the simulation results to the master
     * @param resultSet
     */
    public void simulationDone(ResultSet resultSet) {
        System.out.println("-- "+ agentId + " simulation done --");
        msgHandler.simulationDone(this.agentId, resultSet);
    }

    /**
     * Notifies the master that a fatal error occured
     */
    public void fatalErrorOccured(String message) {
        msgHandler.fatalErrorOccured(this.agentId, message);
    }

    public void contextInitialized(ServletContextEvent sce) {
       this.agentId = sce.getServletContext().getContextPath().substring(1);
       msgHandler = new AgentMessageHandler(this.agentId);
       msgHandler.setListener(this);

       jmsThread = new Thread(msgHandler);
       jmsThread.start();

       System.out.println("-- Agent started with id : "+this.agentId+" --");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        jmsThread.stop();
        System.out.println("-- Agent stopped --");
    }
}
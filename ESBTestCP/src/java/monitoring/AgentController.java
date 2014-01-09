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
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import simulation.*;

/**
 *
 * @author BambaLamine
 */
public class AgentController implements MonitoringMessageListener, SimulationMessageListener, javax.servlet.ServletContextListener {

    private SimulationEntity simulationEntity;
    private JMSHandler jms;
    
    private String agentId;

    /**
     * Return a new AgentController entity, and start monitoring message handler.
     */
    public AgentController() {
        jms = new JMSHandler();
        jms.setListener(this);

        /*Thread jmsThread = new Thread(jms);
        jmsThread.start();*/
    }

    /**
     * Asks the simulationEntity to start the simulation
     */
    public void startSimulationMessage() {
        simulationEntity.startSimulation();
    }

    /**
     * Asks the simulationEntity to abort the simulation
     */
    public void abortSimulationMessage() {
        simulationEntity.abortSimulation();
    }

    /**
     * Asks to the simulationEntity (configured as a producer) to end the simulation
     */
    public void endSimulationMessage() {
        if (simulationEntity instanceof ProducerEntity) {
            ((ProducerEntity) simulationEntity).endOfSimlation();
        }
    }

    /**
     * Configure the simulationEntity
     */
    public void configurationMessage(AgentConfiguration receiverAgent, SimulationScenario simulationScenario) {
        //On crée une simulationEntity correspondant à la configuration reçue
        System.out.println("Agent configuration - Name: "+receiverAgent.getAgentId());
        System.out.println("Agent configuration - WS address: "+ receiverAgent.getWsAddress());
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

    /**
     * Notifies the master that the configuration is done. 
     */
    public void configurationDone()
    {
        jms.configurationDone(this.agentId);
    }

    /**
     * Send the simulation results to the master
     * @param resultSet
     */
    public void simulationDone(ResultSet resultSet) {
        jms.simulationDone(resultSet);
    }

    /**
     * Notifies the master that a fatal error occured
     */
    public void fatalErrorOccured() {
        jms.fatalErrorOccured();
    }

    public void contextInitialized(ServletContextEvent sce) {
        this.agentId = sce.getServletContext().getInitParameter("agentId");
       System.out.println("-- Agent started with id : "+this.agentId+" --");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("-- Agent stopped --");
    }
}
/* public void actualiserController(interfaceObservableJMS a){
if(a instanceof JMSHandler){
jms=(JMSHandler) a;
MessageFromJMSEntity_TEST mess;
mess=jms.getMessageFRomJMS();
System.out.println(mess.getMessageType());

//if messsadeTYpe=1 ==> configuration of agents
if(jms.getMessageFRomJMS().getMessageType()==0)
{
if(jms.getMessageFRomJMS().getAgent().getWsAgentType() == jms.getMessageFRomJMS().getAgent().getWsAgentType().CONSUMER)
{
consumer = new  ConsumerEntity();

//ICI creer le thread du producteur et enregister son pid

consumer.configureConsumer(jms.getMessageFRomJMS().getScenario().getTabScenario());
} else {
producer = new  ProducerEntity();

//ICI creer le thread du producteur et enregister son pid
// hMapProducer.put(jms.getMessageFRomJMS().getAgent().getName(), producer);

//????? la configuration du provider c'est a dire son temps de reponse et son id,
producer.configureProducer(jms.getMessageFRomJMS().getResponseTime(),jms.getMessageFRomJMS().getResponseSize());

}

}
//If we receive a message of startSimulation (messageType = 1)
else if(jms.getMessageFRomJMS().getMessageType()==1)
{

if(jms.getMessageFRomJMS().getAgent().getWsAgentType() == jms.getMessageFRomJMS().getAgent().getWsAgentType().CONSUMER)
{

String wsAdresse=jms.getMessageFRomJMS().getAgent().getWsAddress();
String monitoringWsAdresse=jms.getMessageFRomJMS().getAgent().getMonitoringWSAddress();

//recuperer le consumer à demarrer ??

consumer.startSimulation();
}
else
{
String wsAdresse=jms.getMessageFRomJMS().getAgent().getWsAddress();
String monitoringWsAdresse=jms.getMessageFRomJMS().getAgent().getMonitoringWSAddress();

//recuperer le provider à demarrer ???

producer.startSimulation();
}
}

//If we receive a message of abortSimulation (messageType = 2)
else if(jms.getMessageFRomJMS().getMessageType()==1)
{

if(jms.getMessageFRomJMS().getAgent().getWsAgentType() == jms.getMessageFRomJMS().getAgent().getWsAgentType().CONSUMER)
{

String wsAdresse=jms.getMessageFRomJMS().getAgent().getWsAddress();
String monitoringWsAdresse=jms.getMessageFRomJMS().getAgent().getMonitoringWSAddress();

//recuperer le consumer à ABORTER ??

consumer.abortSimulation();
}
else
{
String wsAdresse=jms.getMessageFRomJMS().getAgent().getWsAddress();
String monitoringWsAdresse=jms.getMessageFRomJMS().getAgent().getMonitoringWSAddress();

//recuperer le provider à ABORTER ???

producer.abortSimulation();
}
}
}

}
 */

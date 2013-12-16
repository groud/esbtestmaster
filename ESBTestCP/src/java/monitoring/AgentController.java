/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/



// EN stand by, en attente du JMs


package monitoring;

import datas.AgentConfiguration;
import datas.ConsumerConfiguration;
import datas.ProducerConfiguration;
import datas.SimulationScenario;
import interfaces.MonitoringMessageListener;
import simulation.*;


/**
<<<<<<< HEAD
*
* @author bambaLamine
*/



public class AgentController implements MonitoringMessageListener {
    private SimulationEntity simulationEntity;
    private JMSHandler jms;

    public AgentController() {
        jms = new JMSHandler();

        Thread jmsThread = new Thread (jms);
        jmsThread.start();
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
    public void startSimulationMessage(AgentConfiguration receiverAgent) {
        simulationEntity.startSimulation();
    }

    public void stopSimulationMessage(AgentConfiguration receiverAgent) {
        simulationEntity.abortSimulation();
    }

    public void configurationMessage(AgentConfiguration receiverAgent, SimulationScenario simulationScenario) {
        //On crée une simulationEntity correspondant à la configuration reçue
        if (receiverAgent instanceof ConsumerConfiguration) {
            simulationEntity = new ConsumerEntity();
            //Passer la configuration au simulationEntity
        } else if (receiverAgent instanceof ProducerConfiguration) {
            simulationEntity = new ProducerEntity();
            //Passer la configuration au simulationEntity 
        }
        simulationEntity.setId(receiverAgent.getName());
    }
}
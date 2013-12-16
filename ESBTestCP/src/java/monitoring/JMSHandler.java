/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monitoring;

import datas.JMSMessages.ConfigJMSMessage;
import datas.JMSMessages.StartJMSMessage;
import datas.JMSMessages.StopJMSMessage;
import datas.JMSMessages.*;
import datas.ResultSet;
import interfaces.MonitoringMessageHandler;
import interfaces.MonitoringMessageListener;
import java.io.Serializable;

/**
 *
 * @author bambaLamine
 */
public class JMSHandler implements MonitoringMessageHandler, Runnable{
    MonitoringMessageListener listener;

    public JMSHandler() {
        //TODO : Créer un thread en écoute des messages JMS. En fonction de ceux-ci, prévenir le controller à l'aide de :
        //listener.configurationMessage(config, config);
        //listener.configurationMessage(config, config);
        //listener.configurationMessage(config, config);
    }


    public void setListener(MonitoringMessageListener listener) {
        this.listener = listener;
    }

    public void simulationDone(ResultSet resultSet) {
        //TODO : Envoyer le message JMS au master pour indiquer que la simulation est terminée
    }

    public void fatalErrorOccured() {
        //TODO : Envoyer le message JMS au master pour indiquer que la simulation n'a pu être terminée
    }

    public void run() {
        //TODO Ecouter les messages et les envoie au MasterController avec :
        AgentMessageHandler myAgentMsgHandler= new AgentMessageHandler();
        Serializable message;
        message=myAgentMsgHandler.receiveFromTopic();

        if(message instanceof ConfigJMSMessage){
              ConfigJMSMessage myMessage=(ConfigJMSMessage) message;
              listener.configurationMessage(myMessage.getAgentConfiguration(), myMessage.getScenario());
        }
        else if(message instanceof StartJMSMessage){
             listener.startSimulationMessage();
        }
        else if(message instanceof StopJMSMessage){
             listener.stopSimulationMessage();
        }
        else{}
 
    }


}

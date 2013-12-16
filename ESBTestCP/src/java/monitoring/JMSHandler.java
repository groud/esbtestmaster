/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monitoring;

import datas.ResultSet;
import interfaces.MonitoringMessageHandler;
import interfaces.MonitoringMessageListener;

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
        //listener.configurationMessage(null, null);
        //listener.startSimulationMessage(null);
        //listener.stopSimulationMessage(null);
    }


}

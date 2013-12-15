/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monitoring;

import datas.AgentConfiguration;
import datas.SimulationScenario;
import interfaces.MonitoringMessageHandler;
import interfaces.interfaceObservableJMS;

/**
 *
 * @author bambaLamine
 */
public class JMSHandler implements interfaceObservableJMS {
    AgentController monitor;
    MessageFromJMSEntity_TEST messageRecue;

    public void addListener(AgentController agent) {
        this.monitor = agent;
    }

    public void notifyMonitor() {
        this.monitor.actualiserController(this);

    }
    public void receivedJMSMessage(MessageFromJMSEntity_TEST messageRecue) {
       this.messageRecue=messageRecue;

    }


    public MessageFromJMSEntity_TEST getMessageFRomJMS(){
        return this.messageRecue;
    }


}

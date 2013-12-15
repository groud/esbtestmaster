/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import datas.AgentConfiguration;
import datas.ResultSet;
import monitoring.AgentController;

/**
 *
 * @author bambaLamine
 */
public interface interfaceObservableJMS {
    //--->Les types sont à mettre à jour
    //il doit recevoir le fichier depuis le JMS 
    
    public void addListener(AgentController agent);
    public void notifyMonitor();

}
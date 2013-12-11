/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import datas.ResultSet;

/**
 *
 * @author root
 */
public interface MonitoringMsgListener {
    //--->Les types sont à mettre à jour
    //il doit recevoir le fichier depuis le JMS 
    public void simulationDoneForOneAgent(ResultSet resultSet);
    public void fatalErrorOccured(String agentId, String msg);
}
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
    public void configurationDoneForOneAgent(String agentID);
    public void simulationDoneForOneAgent(String agentID, ResultSet resultSet);
    public void fatalErrorOccured(String agentID, String msg);

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

//import datas.*;

import datas.ResultSet;


/**
 * MonitoringMessageHandler
 */
public interface MonitoringMessageHandler {
    public void simulationDone(String agentId, ResultSet resultSet);
    public void fatalErrorOccured(String agentId, String message);
}

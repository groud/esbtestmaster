/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

//import datas.*;

import datas.ResultSet;


/**
 *
 * @author root
 */
public interface MonitoringMessageHandler {
    public void simulationDone(ResultSet resultSet);
    public void fatalErrorOccured();
}

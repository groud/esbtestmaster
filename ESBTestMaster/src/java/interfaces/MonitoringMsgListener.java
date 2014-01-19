package interfaces;

import datas.ResultSet;

/**
 * MonitoringMsgListener
 */
public interface MonitoringMsgListener {

    public void configurationDoneForOneAgent(String agentID);

    public void simulationDoneForOneAgent(String agentID, ResultSet resultSet);

    public void fatalErrorOccured(String agentID, String msg);
}

package interfaces;

import datas.ResultSet;

/**
 * SimulationMessageListener
 */
public interface SimulationMessageListener {
    public void simulationDone(ResultSet resultSet);
    public void fatalErrorOccured(String message);
}

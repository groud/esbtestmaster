package interfaces;

import datas.SimulationScenario;

/**
 * UserOutputsInterface
 */
public interface UserOutputsInterface {

    public void displayErrorMessage(String msg);

    public void notifySimulationDone();

    public void notifySimulationAborted();

    public void notifyConfigurationLoaded(SimulationScenario ss);
}

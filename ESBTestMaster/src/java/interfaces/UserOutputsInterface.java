/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import datas.SimulationScenario;

/**
 *
 * @author gilles
 */
public interface UserOutputsInterface {
    public void displayErrorMessage(String msg);
    public void notifySimulationDone();
    public void notifySimulationAborted();
    public void notifyConfigurationLoaded(SimulationScenario ss);
}

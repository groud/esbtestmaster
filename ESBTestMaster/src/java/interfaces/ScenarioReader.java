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
public interface ScenarioReader {
    public SimulationScenario getScenarioFromFile(String filename);
    
}

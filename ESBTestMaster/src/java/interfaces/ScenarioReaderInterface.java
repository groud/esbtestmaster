/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import Exceptions.BadXMLException;
import datas.SimulationScenario;
import java.io.IOException;

/**
 *
 * @author gilles
 */
public interface ScenarioReaderInterface {
    public SimulationScenario readXMLFile(String file) throws BadXMLException, IOException;
}

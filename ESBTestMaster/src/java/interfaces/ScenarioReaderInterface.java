package interfaces;

import Exceptions.BadXMLException;
import datas.SimulationScenario;
import java.io.IOException;

/**
 * ScenarioReaderInterface
 */
public interface ScenarioReaderInterface {
    public SimulationScenario readXMLFile(String file) throws BadXMLException, IOException;
}

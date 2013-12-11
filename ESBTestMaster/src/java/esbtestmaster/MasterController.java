/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import Exceptions.BadXMLException;
import datas.ResultSet;
import interfaces.MonitoringMsgListener;
import interfaces.UserInputsListener;
import java.io.IOException;

/**
 *
 * @author gilles
 */
public class MasterController implements UserInputsListener, MonitoringMsgListener {
    private CLI shell;
    private ScenarioReader scenarioReader;
    private KPICalculator kpiCalculator;

    public MasterController() {
        shell = new CLI(this);
        scenarioReader = new ScenarioReader();
        kpiCalculator = new KPICalculator();

        shell.launch();
    }

    public void startSimulation() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stopSimulation() {
        shell.notifySimulationAborted();
    }

    /**
     * Charge un scenario à partir d'un fichier XML
     * @param XMLfile
     */
    public void loadScenario(String XMLfile) {
        try {
            shell.notifyConfigurationLoaded(scenarioReader.readXMLFile(XMLfile));
        } catch (IOException ex) {
            shell.displayErrorMessage(ex.getMessage());
        } catch (BadXMLException ex) {
            shell.displayErrorMessage(ex.getMessage());
        }
    }

    public void calculateKPI(String XMLfile) {
        kpiCalculator.calculateKPIfromXML(XMLfile);
    }

    public void calculateKPI(String inXMLfile, String outXMLfile) {
        kpiCalculator.calculateKPIfromXML(inXMLfile);
        kpiCalculator.saveKPItoXMLFile(outXMLfile);
    }

    public void simulationDoneForOneAgent(ResultSet resultSet) {
        //TODO : On verifie que tous les agents ont terminé la simulation.
        shell.notifySimulationDone();
    }

    public void fatalErrorOccured(String agentID, String msg) {
        shell.displayErrorMessage("A error on the agent " + agentID + " : " +msg+"\n");
        this.stopSimulation();
    }
}

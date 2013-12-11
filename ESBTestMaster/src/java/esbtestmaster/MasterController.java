/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import Exceptions.BadXMLException;
import datas.ResultSet;
import datas.SimulationScenario;
import interfaces.*;
import java.io.IOException;

/**
 *
 * @author gilles
 */
public class MasterController implements UserInputsListener, MonitoringMsgListener {
    private CLI shell;
    private ScenarioReaderInterface scenarioReader;
    private KPICalculatorInterface kpiCalculator;
    private MonitoringMessageHandler monitoringMsgHandler;

    private SimulationScenario currentScenario;

    public MasterController() {
        shell = new CLI(this);
        scenarioReader = new ScenarioReader();
        kpiCalculator = new KPICalculator();
        monitoringMsgHandler = new JMSHandler();

        shell.launch();
    }

    public void startSimulation() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stopSimulation() {
        //On envoie l'ordre à tous les agents de stopper la simulation.
        for (int i=0;i<this.currentScenario.getAgentsconfiguration().size();i++) {
            monitoringMsgHandler.stopSimulationMessage(this.currentScenario.getAgentsconfiguration().get(i));
        }
        //On notifie l'utilisateur que la simulation a bien été stoppée.
        shell.notifySimulationAborted();
    }

    /**
     * Charge un scenario à partir d'un fichier XML
     * @param XMLfile
     */
    public void loadScenario(String XMLfile) {
        try {
            //Recupère le scenario depuis un XML
            this.currentScenario = scenarioReader.readXMLFile(XMLfile);
            //On envoie l'ordre à tous les agents de se configurer
            for (int i=0;i<this.currentScenario.getAgentsconfiguration().size();i++) {
                monitoringMsgHandler.configurationMessage(this.currentScenario.getAgentsconfiguration().get(i), this.currentScenario);
            }
            //On notifie l'utilisateur pour lui indiquer que tout s'est bien passé.
            shell.notifyConfigurationLoaded(this.currentScenario);
        } catch (IOException ex) {
            shell.displayErrorMessage(ex.getMessage());
            this.stopSimulation(); //TODO : A Verifier
        } catch (BadXMLException ex) {
            shell.displayErrorMessage(ex.getMessage());
            this.stopSimulation(); //TODO : A Verifier
        }
    }

    public void calculateKPI(String XMLfile) {
        try {
            kpiCalculator.calculateKPIfromXML(XMLfile);
        } catch (BadXMLException ex) {
            shell.displayErrorMessage(ex.getMessage());
        } catch (IOException ex) {
            shell.displayErrorMessage(ex.getMessage());
        }
    }

    public void calculateKPI(String inXMLfile, String outXMLfile) {
        try {
            kpiCalculator.calculateKPIfromXML(inXMLfile);
            kpiCalculator.saveKPItoXMLFile(outXMLfile);
        } catch (BadXMLException ex) {
            shell.displayErrorMessage(ex.getMessage());
        } catch (IOException ex) {
            shell.displayErrorMessage(ex.getMessage());
        }
    }

    public void simulationDoneForOneAgent(ResultSet resultSet) {
        //TODO : On verifie que tous les agents ont terminé la simulation.
        shell.notifySimulationDone();
    }

    public void fatalErrorOccured(String agentID, String msg) {
        shell.displayErrorMessage("A error on the agent " + agentID + " : " +msg+"\n");
        this.stopSimulation();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MasterController masterController = new MasterController();
    }

}

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
import java.util.HashMap;

/**
 *
 * @author gilles
 */
public class MasterController implements UserInputsListener, MonitoringMsgListener {
    private CLI shell;
    private ScenarioReaderInterface scenarioReader;
    private KPICalculatorInterface kpiCalculator;
    private MonitoringMessageHandler monitoringMsgHandler;
    private ResultKeeperInterface resultsKeeper;

    private SimulationScenario currentScenario;

    private HashMap<String,Boolean> finishedMap;

    public MasterController() {
        shell = new CLI(this);
        scenarioReader = new ScenarioReader();
        kpiCalculator = new KPICalculator();
        monitoringMsgHandler = new JMSHandler();

        shell.launch();
    }

    public void startSimulation(String resultsFilename) {
        if (this.currentScenario != null) {
             resultsKeeper = new XMLResultKeeper(resultsFilename);
             finishedMap = new HashMap<String,Boolean>();
            //TODO
            //throw new UnsupportedOperationException("Not supported yet.");
        } else {
            shell.displayErrorMessage("Aborting failed : no configuration has been provided.");
        }
    }

    public void stopSimulation() {
        if (this.currentScenario != null) {
            //On envoie l'ordre à tous les agents de stopper la simulation.
            for (int i=0;i<this.currentScenario.getAgentsconfiguration().size();i++) {
                monitoringMsgHandler.stopSimulationMessage(this.currentScenario.getAgentsconfiguration().get(i));
            }
            //On notifie l'utilisateur que la simulation a bien été stoppée.
            shell.notifySimulationAborted();
        } else {
            shell.displayErrorMessage("Aborting failed : no configuration has been provided.");
        }
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

    public void simulationDoneForOneAgent(String agentID, ResultSet resultSet) {
        //On ajoute le résultat de l'agent au set de résultat
        if(this.resultsKeeper != null) {
            if(finishedMap.get(agentID) != true) {
                resultsKeeper.addLog(resultSet);
                finishedMap.put(agentID, true);
            }

            //On verifie que tous les agents ont terminé la simulation.
            boolean terminated = true;
            for (int i=0;i<this.currentScenario.getAgentsconfiguration().size();i++) {
                if(finishedMap.get(this.currentScenario.getAgentsconfiguration().get(i).getName()) != true) {
                    terminated = false;
                }
            }

            if (terminated) {
                shell.notifySimulationDone();
            }
        } else {
            shell.displayErrorMessage("A simulation done message has been received, but no results logger is available");
        }
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

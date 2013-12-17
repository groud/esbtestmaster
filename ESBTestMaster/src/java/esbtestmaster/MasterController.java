/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import Exceptions.BadXMLException;
import datas.ProducerConfiguration;
import datas.ResultSet;
import datas.SimulationScenario;
import interfaces.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilles
 */
public class MasterController implements UserInputsListener, MonitoringMsgListener {
    private CLI shell;
    private ScenarioReaderInterface scenarioReader;
    private KPICalculatorInterface kpiCalculator;
    private JMSHandler monitoringMsgHandler;
    private XMLResultKeeper resultsKeeper;

    private SimulationScenario currentScenario;

    private HashMap<String,Boolean> finishedMap;



    public MasterController() {
        //We instanciate the different components.
        shell = new CLI(this);
        scenarioReader = new ScenarioReader();
        kpiCalculator = new KPICalculator();
        monitoringMsgHandler = new JMSHandler();

        //We start the Thread listening to the JMS messages
        Thread monitoringMsgThread = new Thread (monitoringMsgHandler);
        monitoringMsgThread.start();

        //We start the CLI
        shell.launch();
    }

    /**
     * Starts the simulation and save the results in the provided file name.
     * @param resultsFilename
     */
    public void startSimulation(String resultsFilename) {
        try {
            if (this.currentScenario != null) {

                resultsKeeper = new XMLResultKeeper(resultsFilename);
                finishedMap = new HashMap<String, Boolean>();
                //TODO
                //throw new UnsupportedOperationException("Not supported yet.");
            } else {
                shell.displayErrorMessage("Starting failed : no configuration has been provided.");
            }
        } catch (IOException ex) {
            shell.displayErrorMessage(ex.getMessage());
        }
    }

    /**
     * Aborts the simulation.
     */
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
     * Load a scenario from an XML file, then asks the agent to configure themselves.
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

    /**
     * Calculates the KPI from an XML file.
     * @param XMLfile
     */
    public void calculateKPI(String XMLfile) {
        try {
            kpiCalculator.calculateKPI(XMLResultKeeper.getLog(XMLfile));
        } catch (BadXMLException ex) {
            shell.displayErrorMessage(ex.getMessage());
        } catch (IOException ex) {
            shell.displayErrorMessage(ex.getMessage());
        }
    }

    /**
     * Calculates the KPI from an XML file, then writes the results in outXMLfile.
     * @param XMLfile
     */
    public void calculateKPI(String inXMLfile, String outXMLfile) {
        try {
            kpiCalculator.calculateKPI(XMLResultKeeper.getLog(inXMLfile));
            kpiCalculator.saveKPItoXMLFile(outXMLfile);
        } catch (BadXMLException ex) {
            shell.displayErrorMessage(ex.getMessage());
        } catch (IOException ex) {
            shell.displayErrorMessage(ex.getMessage());
        }
    }

    /**
     * Notify the MasterController that an agent is done with the simulation.
     * @param agentID
     * @param resultSet
     */
    public void simulationDoneForOneAgent(String agentID, ResultSet resultSet) {
        //On ajoute le résultat de l'agent au set de résultat
        if(this.resultsKeeper != null) {
            if(finishedMap.get(agentID) != true) {
                try {
                    resultsKeeper.addLog(resultSet);
                } catch (IOException ex) {
                    shell.displayErrorMessage(ex.getMessage());
                } catch (BadXMLException ex) {
                    shell.displayErrorMessage(ex.getMessage());
                }
                finishedMap.put(agentID, true);
            }
            
            //On verifie que tous les consumers ont terminé la simulation
            boolean consumersTerminated = true;
            for (int i=0;i<this.currentScenario.getAgentsconfiguration().size();i++) {
                if(this.currentScenario.getAgentsconfiguration().get(i) instanceof ProducerConfiguration && finishedMap.get(this.currentScenario.getAgentsconfiguration().get(i).getName()) != true) {
                    consumersTerminated = false;
                }
            }
            
            
            //TODO : SEND A MESSAGE TO THE PRODUCERS IF THE SIMULATION IS OVER


            //On verifie que tous les agents ont terminé la simulation.
            if (consumersTerminated) {
                boolean terminated = true;
                for (int i=0;i<this.currentScenario.getAgentsconfiguration().size();i++) {
                    if(finishedMap.get(this.currentScenario.getAgentsconfiguration().get(i).getName()) != true) {
                        terminated = false;
                    }
                }
                if (terminated) {
                    shell.notifySimulationDone();
                }
            }           
        } else {
            shell.displayErrorMessage("A simulation done message has been received, but no results logger is available");
        }
    }

    /**
     * Notify an agent that an error occured to an agent.
     * @param agentID
     * @param msg
     */
    public void fatalErrorOccured(String agentID, String msg) {
        shell.displayErrorMessage("A error on the agent " + agentID + " : " +msg+"\n");
        this.stopSimulation();
    }

    /**
     * Starts the Master
     * @param args
     */
    public static void main(String[] args) {
        MasterController masterController = new MasterController();
    }

}

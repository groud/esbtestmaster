/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esbtestmaster;

import Exceptions.BadXMLException;
import datas.*;
import interfaces.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

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
    private HashMap<String, Boolean> finishedMap;

    private boolean consumersTerminated;
    /**
     * Returns an instance of a master controller then start it.
     */
    public MasterController() {
        //We instanciate the different components.
        shell = new CLI(this);
        scenarioReader = new ScenarioReader();
        kpiCalculator = new KPICalculator();
        monitoringMsgHandler = new JMSHandler();

        //We start the Thread listening to the JMS messages
        Thread monitoringMsgThread = new Thread(monitoringMsgHandler);
        monitoringMsgThread.start();

        //We start the CLI
        shell.launch();
    }

    // -------------------------------
    //   INTERFACES IMPLEMENTATIONS
    // -------------------------------
    /**
     * Starts the simulation and save the results in the provided file name.
     * @param resultsFilename
     */
    public void startSimulation(String resultsFilename) {
        try {
            if (this.currentScenario != null) {
                resultsKeeper = new XMLResultKeeper(resultsFilename);
                finishedMap = new HashMap<String, Boolean>();
                this.consumersTerminated = false;

                for (Iterator<AgentConfiguration> i = this.currentScenario.getAgentsconfiguration().iterator(); i.hasNext();) {
                    AgentConfiguration agentConfiguration = i.next();
                    monitoringMsgHandler.startSimulationMessage(agentConfiguration);
                }
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
    public void abortSimulation() {
        if (this.currentScenario != null) {
            //We ask to the agents to abort the simulation.
            for (int i = 0; i < this.currentScenario.getAgentsconfiguration().size(); i++) {
                monitoringMsgHandler.abortSimulationMessage(this.currentScenario.getAgentsconfiguration().get(i));
            }
            //We notify the user that the simulation has been aborted.
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
            //We get the scenario XML
            this.currentScenario = scenarioReader.readXMLFile(XMLfile);
            //We the send a configuration message to the agents
            for (AgentConfiguration agentConfiguration :this.currentScenario.getAgentsconfiguration()){
                monitoringMsgHandler.configurationMessage(agentConfiguration, this.currentScenario);
            }
            //We notify the user that everything happened well.
            shell.notifyConfigurationLoaded(this.currentScenario);
        } catch (IOException ex) {
            shell.displayErrorMessage(ex.getMessage());
            this.abortSimulation();
        } catch (BadXMLException ex) {
            shell.displayErrorMessage(ex.getMessage());
            this.abortSimulation();
        }
    }

    /**
     * Calculates the KPI from an XML file.
     * @param XMLfile
     */
    public void calculateKPI(String XMLfile) {
        try {
            KPISet kpiSet = kpiCalculator.calculateKPI(XMLResultKeeper.getLog(XMLfile));
            System.out.println(kpiSet.toString());
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
            KPISet kpiSet = kpiCalculator.calculateKPI(XMLResultKeeper.getLog(inXMLfile));
            kpiCalculator.saveKPItoXMLFile(kpiSet, outXMLfile);
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
        if (this.resultsKeeper != null) {
            if (finishedMap.get(agentID) != true) {
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
            if (this.consumersTerminated == false) {
                for (AgentConfiguration agentConfiguration:this.currentScenario.getAgentsconfiguration()) {
                    if (agentConfiguration instanceof ConsumerConfiguration && finishedMap.get(agentConfiguration.getAgentId()) != true) {
                        consumersTerminated = false;
                    }
                }
                
                //If the simulation is over for the consumers, we send a message to the producers
                if(this.consumersTerminated) {
                    for (AgentConfiguration agentConfiguration:this.currentScenario.getAgentsconfiguration()) {
                        if (agentConfiguration instanceof ProducerConfiguration) {
                            monitoringMsgHandler.endSimulationMessage((ProducerConfiguration)agentConfiguration);
                        }
                    }
                }
            }

            //On verifie que tous les agents ont terminé la simulation.
            if (consumersTerminated) {
                boolean terminated = true;
                for (AgentConfiguration agentConfiguration:this.currentScenario.getAgentsconfiguration()) {
                    if (finishedMap.get(agentConfiguration.getAgentId()) != true) {
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
        shell.displayErrorMessage("A error on the agent " + agentID + " : " + msg + "\n");
        this.abortSimulation();
    }

    /**
     * Starts the Master
     * @param args
     */
    public static void main(String[] args) {
        MasterController masterController = new MasterController();
    }
}

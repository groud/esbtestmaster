/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import datas.SimulationScenario;
import interfaces.UserInputsListener;
import interfaces.UserOutputsInterface;
import java.util.*;
import java.util.logging.*;
import org.naturalcli.*;
import org.naturalcli.commands.HelpCommand;
import utils.Debug;

/**
 *
 * @author root
 */
public class CLI implements UserOutputsInterface {
     private UserInputsListener listener;
     private Set<Command> cs = new HashSet<Command>();
     private Scanner input = new Scanner(System.in);

     public CLI(UserInputsListener uil){
        setListener(uil);
        initCommands();
     }

     /**
      * Run the command line interface
      */
     public void launch() {
         String cmd = "";
          while(true){
                try {
                    // Execute
                    System.out.print("ESB# ");
                    cmd = input.nextLine();
                    new NaturalCLI(cs).execute(cmd);
                } catch (ExecutionException ex) {
                    System.out.println("Invalid command, run \"help\" for usage.");
                }
          }
     }

     /**
      * select the listener
      */
     private void setListener(UserInputsListener uil) {
        this.listener = uil;
     }

     /**
      * Init the commands
      */
     private void initCommands() {
        try {
            Command loadconf = new Command("loadconf <filename:string>", "Loads an XML simulation configration file.", new ICommandExecutor() {
                public void execute(ParseResult pr) {
                    if (pr.getParameterCount() > 0) {
                        String filename = pr.getParameterValue(0).toString();
                        System.out.println("Loading configuration from " + filename + " ...");
                        listener.loadScenario(filename);
                    }
                }
            });

            //start command
            Command start = new Command("start [<outputfile:string>]", "Starts the simulation. If the outputfile is provided, the raw results of the simulation should be stored in outputfile. If it is not provided, the default XMLfilename is \"results.xml\".", new ICommandExecutor() {
                public void execute(ParseResult pr) {
                    String filename;
                    if (pr.getParameterCount() > 0 && pr.getParameterValue(0).toString() != null) { //Si un fichier a été proposé
                        filename = pr.getParameterValue(0).toString();
                    } else {
                        filename = "results.xml";
                    }
                    System.out.println("Starting simulation ...");
                    listener.startSimulation();
                    //System.out.println("Simulation done. Results in "+filename+".");
                }
            });

            //processfile
            Command processfile = new Command("processfile <inputfile:string> [<outputfile:string>]", "Generates the KPIs (Key Performance Indicators) from the inputfile XML raw result file. If outputfile is provided, generates an XML file containing the KPIs, if not, displays them using the standart output. ", new ICommandExecutor() {
                public void execute(ParseResult pr) {
                    String infilename = null;
                    String outfilename = null;

                    infilename = (String)pr.getParameterValue(0);
                    if (pr.getParameterCount() > 1 && pr.getParameterValue(1) != null) { //Si un fichier a été proposé
                        outfilename = (String)pr.getParameterValue(1);
                    } else {
                        outfilename = null;
                    }

                    System.out.println("Calculating KPI from "+ infilename +"...");
                    if (outfilename != null) {
                        System.out.println("(Results in "+ outfilename +".)");
                        listener.calculateKPI(infilename,outfilename);
                    } else {
                        listener.calculateKPI(infilename);
                    }
                    
                }
            });

            //abort command
            Command abort = new Command("abort", "Aborts the simulation.", new ICommandExecutor() {
                public void execute(ParseResult pr) {
                    System.out.println("Aborting simulation.");
                    listener.stopSimulation();
                }
            });

            //debug command
            Command debug = new Command("debug", "Switch on/off the debug mode.", new ICommandExecutor() {
                public void execute(ParseResult pr) {
                    if (Debug.isActivated()) {
                        Debug.setActivated(false);
                        System.out.println("Debug mode off.");
                    } else {
                        Debug.setActivated(true);
                        System.out.println("Debug mode on.");
                    }
                }
            });

            //exit command
            Command exit = new Command("exit", "Exits the ESB qualification tool (and stops the simulation).", new ICommandExecutor() {
                public void execute(ParseResult pr) {
                    //EXECUTION
                    System.exit(0);
                }
            });

          //Addind the commands to the list of available commands.
          cs.add(loadconf);
          cs.add(start);
          cs.add(processfile);
          cs.add(abort);
          cs.add(exit);
          cs.add(debug);
          cs.add(new HelpCommand(cs));

        } catch (InvalidSyntaxException ex) {
            Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
        }

     }


    /**
     * Displays an error message
     * @param msg
     */
    public void displayErrorMessage(String msg) {
        System.err.println("ERROR : " + msg);
    }

    // -------------------------------
    //   INTERFACES IMPLEMENTATIONS
    // -------------------------------
    /**
     * Displays a message indicating that the simulation is done
     * @param msg
     */
    public void notifySimulationDone() {
        System.out.println("Simulation done.");
    }

    /**
     * Displays a message indicating that the simulation has been aborted
     * @param msg
     */
    public void notifySimulationAborted() {
        System.out.println("Simulation aborted.");
    }

    /**
     * Displays a message indicating that the configuration has been loaded
     * @param msg
     */
    public void notifyConfigurationLoaded(SimulationScenario ss) {
        System.out.println("System configuration done. Ready for the simulation.");
    }

}

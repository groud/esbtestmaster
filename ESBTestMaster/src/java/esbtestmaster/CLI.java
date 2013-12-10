/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.naturalcli.*;
import org.naturalcli.commands.HelpCommand;

/**
 *
 * @author root
 */
public class CLI {

     Set<Command> cs = new HashSet<Command>();

     private Scanner input = new Scanner(System.in);

     public CLI(){
         super();
         init();
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
      * Init the command line interface
      */
     private void init() {
        try {
            //loadconf command
            Command loadconf = new Command("loadconf <filename:string>", "Loads an XML simulation configration file.", new ICommandExecutor() {
                public void execute(ParseResult pr) {
                    if (pr.getParameterCount() > 0) {
                        String filename = pr.getParameterValue(0).toString();
                        System.out.println("Loading configuration from " + filename + " ...");
                        //EXECUTION
                        System.out.println("System configuration done. Ready for the simulation.");
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
                    //EXECUTION
                    System.out.println("Simulation done. Results in "+filename+".");
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
                    //EXECUTION
                    if (outfilename != null) System.out.println("Results in "+ outfilename +".");
                }
            });

            //abort command
            Command abort = new Command("abort", "Aborts the simulation.", new ICommandExecutor() {
                public void execute(ParseResult pr) {
                    System.out.println("Aborting simulation.");
                    //EXECUTION
                    System.out.println("Simulation aborted.");
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
          cs.add(new HelpCommand(cs));

        } catch (InvalidSyntaxException ex) {
            Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
        }

     }

}

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

     public void launch(){
         String cmd = "";
          while(true){
            try {
                // Execute
                System.out.print("ESB# ");
                cmd = input.nextLine();
                new NaturalCLI(cs).execute(cmd);
            } catch (ExecutionException ex) {

                System.out.println("Commande inconnue");    
            }
          }
     }

     private void init(){
        try {
            Command helloWorldCommand = new Command("hello world <name:string>", "Says hello to the world and especially to some one.", new ICommandExecutor() {

                public void execute(ParseResult pr) {
                    System.out.println("Hello world! And hello especially to " + pr.getParameterValue(0));
                }
            });
            Command run = new Command("run <scenario:string>", "Starts the test described in scenario and creates a XML results file.", new ICommandExecutor() {

                public void execute(ParseResult pr) {
                    System.out.println("Run Scenario " + pr.getParameterValue(0));
                }
            });
            Command process = new Command("process <testresult:string>", "processes the raw XML output file and generates the processed output XML file containing the processed data.", new ICommandExecutor() {

                public void execute(ParseResult pr) {
                    System.out.println("Process File " + pr.getParameterValue(0));
                }
            });
            
          cs.add(helloWorldCommand);
          cs.add(run);
          cs.add(process);
        } catch (InvalidSyntaxException ex) {
            Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
        }

     }


}

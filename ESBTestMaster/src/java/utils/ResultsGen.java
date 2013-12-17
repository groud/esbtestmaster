package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class ResultsGen {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
                int i=0, j=0;
                long sum = 0;
		try {
                        System.out.println("Saisir le chemin ou enregistrer le fichier results.xml et le fichier kpi :  ");
                        String path = in.nextLine();
                        File file = new File(path);
                        if (file.isDirectory()){

                            PrintWriter writer = new PrintWriter(path+ "/results.xml");
                            //Les kpis du fichier results xml générés sont stockés dans le fichier kpi, pour pouvoir tester le calcul de ces derniers
                            PrintWriter writer2 = new PrintWriter(path + "/kpi");
                            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> \n ") ;
                            writer.println("<results xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XSD/results.xsd\">");
                            for (i=0;i<5; i=i+1){
                                for(j=1;j<6;j++){
                                    if(j==4 || j==5){
                                       writeEvents(writer, (i*5)+j, i+1, true);
                                    } else {
                                       sum += (writeEvents(writer, (i*5)+j, i+1, false));
                                    }
                                }
                                writer2.println("cons" + (i+1) + " : averageRespTime = " + (sum/3) + " nbReqSent=5 nbReqLost=2");
                                sum=0;
                            }

                            writer.println("</results>");
                            writer.close();
                            writer2.close();
                            
                        } else {
                            System.out.println("Chemin saisi innexistant");
                        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

                


	}

        //retourne le temps de réponse de l'enchainement d'events créé
        // lost indique si on veut que le paquet soit perdu lors de l'échange
        public static int writeEvents(PrintWriter writer,int i, int agentId,  boolean lost) {
            int result=0;
            String agenttype = "CONSUMER";
            int eventdate1 = randInt(0,(Integer.MAX_VALUE/4)) ;
            String eventtype = "REQUEST_SENT";

            writeEvent(writer, "cons"+ agentId, agenttype, eventdate1, eventtype, i);

            agenttype = "PRODUCER";
            int eventdate = randInt(eventdate1,(Integer.MAX_VALUE/2));
            eventtype = "REQUEST_RECEIVED";

            writeEvent(writer, "prod" + agentId, agenttype, eventdate, eventtype, i);

            agenttype = "PRODUCER";
            eventdate = randInt(eventdate,(Integer.MAX_VALUE/4)*3);
            eventtype = "RESPONSE_SENT";

            writeEvent(writer,"prod"+agentId , agenttype, eventdate, eventtype, i);

            agenttype = "CONSUMER";
            int eventdate2 = randInt(eventdate,Integer.MAX_VALUE);
            eventtype = "RESPONSE_RECEIVED";

            if(!lost){
              writeEvent(writer, "cons" + agentId, agenttype, eventdate2, eventtype, i);
              result = (Math.abs(eventdate2-eventdate1));
            }

            return result;

	}

        public static void writeError(PrintWriter writer, String agentid, String agenttype, int eventdate, String eventtype) {
		//Création du simulationEvent
                writer.print("  <event");
		writer.print(" eventDate=\"" + eventdate + "\"");
		writer.print(" eventType=\"" + eventtype + "\"");
		writer.print(" agentType=\"" + agenttype + "\"");
		writer.print(" agentId=\"" + agentid + "\"");
		writer.print(" /> \n");

	}


	public static void writeEvent(PrintWriter writer, String agentid, String agenttype, int eventdate, String eventtype,int requestId) {
		//Création du simulationEvent
                writer.print("      <simulationEvent");
		writer.print(" eventDate=\"" + eventdate + "\"");
		writer.print(" eventType=\"" + eventtype + "\"");
		writer.print(" agentType=\"" + agenttype + "\"");
		writer.print(" agentId=\"" + agentid + "\"");
		writer.print("> \n");
                //on ajoute l'attribute requestId
                writer.println("        <requestId>" + requestId + "</requestId>");
                //On ferme la balise
                writer.println("    </simulationEvent>");
	}


	public static int randInt(int min, int max) {
	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

}

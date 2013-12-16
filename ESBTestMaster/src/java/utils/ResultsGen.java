package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class ResultsGen {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] agenttypes = {"CONSUMER", "PRODUCER"};
		String[] eventtypes = {"REQUEST_SENT","RESPONSE_RECEIVED", "REQUEST_RECEIVED" , "RESPONSE_SENT"};
		int i=0;
		int eventdate;
		try {
			PrintWriter writer = new PrintWriter("/home/matthieu/results.xml");
			writer.println("<?xml version=\"1.0\"?> \n ") ;
			writer.println("<results>");
			for (i=1 ; i<51; i=i+1){

                            if((i%10)==0){
                                writeError(writer, "cons"+i, "CONSUMER", randInt(1,Integer.MAX_VALUE), "CONNEXION_ERR");

                            } else {
				String agenttype = "CONSUMER";
				eventdate = randInt(0,(Integer.MAX_VALUE/4)) ;
				String eventtype = "REQUEST_SENT";

				writeEvent(writer, "cons"+ i, agenttype, eventdate, eventtype, i);

				agenttype = "PRODUCER";
				eventdate = randInt(eventdate,(Integer.MAX_VALUE/2));
				eventtype = "REQUEST_RECEIVED";

				writeEvent(writer, "prod" + i, agenttype, eventdate, eventtype, i);

				agenttype = "PRODUCER";
				eventdate = randInt(eventdate,(Integer.MAX_VALUE/4)*3);
				eventtype = "RESPONSE_SENT";

				writeEvent(writer,"prod"+i , agenttype, eventdate, eventtype, i);

				agenttype = "CONSUMER";
				eventdate = randInt(eventdate,Integer.MAX_VALUE);
				eventtype = "RESPONSE_RECEIVED";

				writeEvent(writer, "cons" + i, agenttype, eventdate, eventtype, i);

                            }

			}
			writer.println("</results>");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

                


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

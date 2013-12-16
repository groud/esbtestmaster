/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package simulation;

import java.util.*;
import datas.*;
import java.util.TimerTask;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import javax.xml.ws.BindingProvider;
/**
*
* @author mariata
*/
public class ConsumerEntity extends SimulationEntity  {

    
    private volatile boolean abortSimulation=false;
    private boolean stepsConfigured=false;
    private ArrayList<SimulationStep> steps;
    private SortedSet<ResultEvent> events;
    private Timer timer;
    private SimulationStep step;
    private ResultEvent currentEvent;

    //constructor
    public ConsumerEntity() {

    }

public void configureConsumer( ArrayList<SimulationStep> steps) {
     this.steps = steps;
     stepsConfigured=true;
}

 @Override
public void writeSimulationEvent(AgentType agent, EventType event){
 currentEvent.setAgentId(this.getid());
 currentEvent.setAgentType(agent);
 // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
 currentEvent.setEventDate(0);// TODOOOO
 currentEvent.setEventType(event);

 //add in list of events
 events.add(currentEvent);

}


 


 /**
     *
     * @param req
     * @param producerUrl e.g. "http://localhost:8090/CompositeAppProxyService1/casaPort1"
     */
 /**
  *
  * @param producerId
  * @param reqPayloadSize size in bytes of thedummy data to put in the request
  * @param respTime producer processing time in ms
  * @param respSize producer response size
  * @param producerUrl producer web service url (or SOAP port on the ESB)
  */
    public void sendRequest(String producerId, int reqPayloadSize, int respTime, int respSize, String producerUrl) {
        String requestData = null;

       try { // Call Web Service Operation
            simulationRef.SimulationWSService service = new simulationRef.SimulationWSService();
            simulationRef.SimulationWS port = service.getSimulationWSPort();

             // Dynamic URL binding
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, producerUrl);

            // Fill the same answer String with reqPayloadSize characters
            if (reqPayloadSize > 0) {
                char[] array = new char[respSize];
                Arrays.fill(array, 'A');
                requestData = new String(array);
            }
            
            // TODO : log events before and after request
            java.lang.String result = port.requestOperation(producerId, requestData, respTime, respSize);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }


//inner class response Listener, for second thread
public class ResponseListener implements Runnable{

        public void run() {
           while(true){
           System.out.println("steps have not been configured");
           }
        }

}
Thread threadResListener = new Thread(new ResponseListener());


    @Override
  public void startSimulation( ) {        
        int i;
        //send request
        if(stepsConfigured){
            //start thread to receive response
             threadResListener.start();

            for(i=0;i<steps.size();i++){
               step = steps.get(i);
               timer = new Timer();
               long nbRequest = (long) (step.getBurstDuration() * step.getBurstRate());
               //interval between two request
               long period = (long) (step.getBurstDuration() / nbRequest);

               //configure timer,and start senario
                timer.scheduleAtFixedRate(new TimerTask(){
                @Override

                  public void run() {
                   //code send request
                    // ****************************************************
                    // TODO : store respTime and respSize for each producer
                    // instead of sending the same values everytime
                    // ****************************************************
                    int respTime = 1000; // ms
                    int respSize = 32; // bytes
                    String producerUrl = "http://localhost:8090/ESBTestCompositeService1/casaPort1";
                    
                    sendRequest(step.getProviderID(), step.getDataPayloadSize(),
                                respTime, respSize, producerUrl);
                  }

                }, step.getBurstStartDate(),period );

                //if abort simulation, exit boucle
                if(abortSimulation){
                     timer.cancel();
                     threadResListener.stop();
                    break;
                }
            }
        }
        else{
         System.out.println("steps have not been configured");
        }

        //TO DO : add receive response code
    }

    @Override
    public void abortSimulation() {
        //terminate  timer and cancel current task
         //timer.cancel();
        this.abortSimulation=true;

    }
}

 // ------------------- PAyload ------------------------//
    /*"MD5 + message " = total
     * total.indexof('+')
     * MD5(string)
     * MD5(string).length -> lgt
     * indexof(lgt) -> mess
     * md5(message)

    */
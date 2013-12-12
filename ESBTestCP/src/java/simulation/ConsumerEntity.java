/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package simulation;

import java.util.ArrayList;
import java.util.Timer;

import datas.*;

import java.util.TimerTask;
/**
*
* @author mariata
*/
public class ConsumerEntity extends SimulationEntity {

    private boolean abortSimulation=false;

    private boolean stepsConfigured=false;

    private ArrayList<SimulationStep> steps;

    private SortedSet<ResultEvent> events;

    private Timer timer;

    private  SimulationStep step;

  private  ResultEvent currentEvent;

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

Date date=new Date();

currentEvent.setEventDate(date);

currentEvent.setEventType(event);

//add in list of events

events.add(currentEvent);

}

//call web service to send request

public void sendRequest (String providerID,float dataPayload){

 //generate fake payload ; give provider id and consumer id

 //call web service

 //write simulation event

    writeSimulationEvent(AgentType.CONSUMER,EventType.REQUEST_SENT);

 }



    @Override

 public void startSimulation( ) {

       int i;

       //send request

       if(stepsConfigured){

           for(i=0;i<steps.size();i++){

              step = steps.get(i);

              timer = new Timer();

              int nbRequest = (int) (step.getBurstDuration() * step.getBurstRate());

              //interval between two request

              long period = (long) (step.getBurstDuration() / nbRequest);

              //configure timer

               timer.scheduleAtFixedRate(new TimerTask(){

               @Override

                 public void run() {

                  //code send resquest

                   sendRequest(step.getProviderID(),step.getDataPayloadSize());

                 }

               }, step.getBurstStartDate(),period );

               //if abort simulation, exit boucle

               if(abortSimulation){

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

       timer.cancel();

       this.abortSimulation=true;


    }

}
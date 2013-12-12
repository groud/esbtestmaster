/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package simulation;

import java.util.*;
import datas.*;

/**
*
* @author mariata
*/
public class ProducerEntity extends SimulationEntity {

    private float responseTime;

    private float responseSize;

    private  boolean abortSimulation=false;

    private SortedSet<ResultEvent> events;

    private ResultEvent currentEvent;

    //constructor

    public ProducerEntity() {

    }

    public void configureProducer(int responseTime, int messageLength){

       this.responseTime=responseTime;

       this.responseSize=messageLength;

    }

    @Override

    public void writeSimulationEvent(AgentType agent, EventType event) {

    currentEvent.setAgentId(this.getid());

    currentEvent.setAgentType(agent);

    // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    Date date=new Date();

    currentEvent.setEventDate(date);

    currentEvent.setEventType(event);

    //add in list of events

     events.add(currentEvent);

    }

public void sendResponse (String consumerID,float dataPayload){

 //generate fake payload

 //call web service

 //write simulation event

    writeSimulationEvent(AgentType.PRODUCER,EventType.RESPONSE_SENT);

 }



    public void startSimulation() {

       String consumerID = null;

       float dataPayload = 0;



       if (!abortSimulation){

        //listen request,and grab consumerID

       //when a request arrive, send response

       sendResponse(consumerID, dataPayload);

       }

    }

    public void abortSimulation() {

       abortSimulation=true;

    }



}
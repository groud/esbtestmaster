/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import java.util.*;
import datas.*;
import interfaces.SimulationMessageListener;

/**
 *
 * @author mariata
 */
public class ProducerEntity extends SimulationEntity {

    private float responseTime;
    private float responseSize;
    private boolean abortSimulation = false;
    //private SortedSet<ResultEvent> events;
    ResultSet resultSet;
    private ResultEvent currentEvent;



    //constructor
    public ProducerEntity() {
    }

    public void configureProducer(int responseTime, int messageLength) {
        this.responseTime = responseTime;
        this.responseSize = messageLength;
    }


    public void writeSimulationEvent(AgentType agent, EventType event) {
        currentEvent.setAgentId(this.getid());
        currentEvent.setAgentType(agent);
        currentEvent.setEventDate(0);//TODOOOOO !!!
        currentEvent.setEventType(event);

        //add in list of events
        resultSet.getEvents().add(currentEvent);
    }

    public void sendResponse(String consumerID, float dataPayload) {

        //generate fake payload

        //call web service

        //write simulation event

        writeSimulationEvent(AgentType.PRODUCER, EventType.RESPONSE_SENT);

    }

    public void startSimulation() {

        String consumerID = null;

        float dataPayload = 0;



        if (!abortSimulation) {

            //listen request,and grab consumerID

            //when a request arrive, send response

            sendResponse(consumerID, dataPayload);

        }

    }

    public void abortSimulation() {

        abortSimulation = true;

    }

    /**
     * TODO : change return type to byte[] or a Response object
     * @param reqType
     * @param reqData
     * @return
     */
    public String processRequest(char reqType, String reqData) {
        return "DUMMY";
    }

   //A REVOIR : proposition lever un timer au niveau du controller et dire au producer que c'est fini a la fin de la simulation
   public void endOfSimlation() {

        //TO DO : send the results to the agent controller, wait the end of all thread
        // listener.simulationDone(resultSet);
    }
}

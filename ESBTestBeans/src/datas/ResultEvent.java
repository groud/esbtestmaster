package datas;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author gilles
 */
public class ResultEvent implements Comparable<ResultEvent>, Serializable{
    private long eventDate;
    private EventType eventType;
    private AgentType agentType;
    private String agentId;
    


    // ----------------------------------
    //   ACCESSORS
    // ----------------------------------
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    //Comparison method
    public int compareTo(ResultEvent event) {
        if (this.getEventDate() < event.getEventDate()) return -1;
        if (this.getEventDate() < event.getEventDate()) return 0;
        else return 1;
    }

    @Override
    public String toString() {
        return "Event from "+this.getAgentType()+" "+this.getAgentId()+" - "+this.getEventType()+" at "+this.getEventDate();
    }
}

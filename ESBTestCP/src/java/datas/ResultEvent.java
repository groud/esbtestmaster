/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas;

import java.util.Date;


/**
 *
 * @author gilles
 */
public class ResultEvent implements Comparable<ResultEvent>{
    private Date eventDate;
    private EventType eventType;
    private AgentType agentType;
    private int agentId;


    // ----------------------------------
    //   ACCESSORS
    // ----------------------------------
    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
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
        if (this.getEventDate().before(event.getEventDate())) return -1;
        if (this.getEventDate().equals(event.getEventDate())) return 0;
        else return 1;
    }
}

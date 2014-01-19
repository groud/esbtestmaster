package datas;

import java.io.Serializable;

/**
 * Describes an event that happened during the simulation
 */
public class ResultEvent implements Comparable<ResultEvent>, Serializable {

    private long eventDate;
    private EventType eventType;
    private AgentType agentType;
    private String agentId;

    // ----------------------------------
    //   ACCESSORS
    // ----------------------------------
    /**
     * Returns the agent identifier, corresponding to the agent which thrown the event.
     * @return
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Set the agent identifier, corresponding to the agent which thrown the event.
     * @param agentId
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    /**
     * Get the origin agent type.
     * @return The agent type
     */
    public AgentType getAgentType() {
        return agentType;
    }

    /**
     * Set the origin agent type.
     * @param agentType
     */
    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    /**
     * Returns the event date.
     * @return The event date in milliseconds, from the beginning of the simulation.
     */
    public long getEventDate() {
        return eventDate;
    }

    /**
     * Set the event date.
     * @param eventDate The event date in milliseconds, from the beginning of the simulation.
     */
    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Returns the event type
     * @return The event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Set the event type
     * @param eventType The event type.
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Compares two events using their event date
     * @param event The event to compare this event to.
     * @return The comparison result
     */
    public int compareTo(ResultEvent event) {
        if (this.getEventDate() < event.getEventDate()) {
            return -1;
        }
        if (this.getEventDate() < event.getEventDate()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Returns a String representation of this object.
     * @return The String representation.
     */
    @Override
    public String toString() {
        return "Event from " + this.getAgentType() + " " + this.getAgentId() + " - " + this.getEventType() + " at " + this.getEventDate();
    }
}

package datas;

/**
 * Describes an event than will be used to follow request and responses events.
 */
public class ResultSimulationEvent extends ResultEvent {

    private int requestId;

    /**
     * Returns the request identifier.
     * @return
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Sets the request identifier.
     * @param requestId
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * Returns a string representation of this object.
     * @return
     */
    @Override
    public String toString() {
        return "Event from " + this.getAgentType() + " " + this.getAgentId() + " - " + this.getEventType() + " at " + this.getEventDate() + " (RequestId=" + this.getRequestId() + ")";
    }
}

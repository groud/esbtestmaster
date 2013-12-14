/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas;

/**
 *
 * @author gilles
 */
public class ResultSimulationEvent extends ResultEvent {
    private int requestId;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "Event from "+this.getAgentType()+" "+this.getAgentId()+" - "+this.getEventType()+" at "+this.getEventDate()+" (RequestId="+this.getRequestId();
    }
}

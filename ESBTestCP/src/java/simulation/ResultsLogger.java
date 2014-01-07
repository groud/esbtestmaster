/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

import datas.AgentType;
import datas.EventType;
import datas.ResultEvent;
import datas.ResultSet;
import java.util.Date;

/**
 *
 * @author samy
 */
public class ResultsLogger {
    private ResultSet resultSet;
    private Date startDate;
    private String agentID;

    /**
     *create a ResultSet to save simulation events, and attribute a agent id to this resultsLogger
     * @param agentID, ID of the local agent logging the results
     */
    public ResultsLogger(String agentID) {
        resultSet = new ResultSet();
        this.agentID = agentID;
    }
     /**
      * set simulation start date
      * @param date
      */
    public void setStartDate(Date date) {
        startDate = date;
    }

    /**
     * Logs a simulation event
     * @param agent
     * @param event
     */
    public void writeSimulationEvent(int reqId, AgentType agent, EventType event) throws Exception {
        if(startDate == null) {
            throw new Exception("The simulation start date was not saved");
        }

        ResultEvent currentEvent = new ResultEvent();
        currentEvent.setAgentId(agentID);
        currentEvent.setAgentType(agent);
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        currentEvent.setEventDate(now.getTime() - startDate.getTime());
        currentEvent.setEventType(event);
        currentEvent.setReqId(reqId);

        //add in list of events
        resultSet.addEvent(currentEvent);

        //*************************
        //TODO : write results to file after X events and clear ResultSet
        //*************************
    }
    /**
     * grab resultSet
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

}

package simulation;

import datas.AgentType;
import datas.EventType;
import datas.ResultSet;
import datas.ResultSimulationEvent;
import java.util.Date;

/**
 * The result logger is able to log event as a set of events.
 * It uses RAM storage using a ResultSet object.
 * Be careful with big simulation, this could trigger out of meomry issues.
 */
public class ResultsLogger {

    private ResultSet resultSet;
    private Date startDate;
    private String agentID;

    /**
     * Return a ResultSet instance.
     * @param The agent identifier.
     */
    public ResultsLogger(String agentID) {
        resultSet = new ResultSet();
        this.agentID = agentID;
    }

    /**
     * Sets simulation start date
     * @param date
     */
    public void setStartDate(Date date) {
        startDate = date;
    }

    /**
     * Logs a simulation event
     * @param agent The agent type (ex: CONSUMER, PROVIDER, ...)
     * @param event The event type (ex: REQUEST_SENT, REQUEST_RECEIVED ...)
     */
    public void writeSimulationEvent(int reqId, AgentType agentType, EventType eventType) throws Exception {
        if (startDate == null) {
            throw new Exception("The simulation start date was not saved");
        }

        ResultSimulationEvent currentEvent = new ResultSimulationEvent();
        currentEvent.setAgentId(agentID);
        currentEvent.setAgentType(agentType);

        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        currentEvent.setEventDate(now.getTime() - startDate.getTime());
        currentEvent.setEventType(eventType);
        currentEvent.setRequestId(reqId);

        // Adds in list of events
        resultSet.addEvent(currentEvent);

    }

    /**
     * Returns the result set
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }
}

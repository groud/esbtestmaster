package datas.JMSMessages;

import datas.ResultSet;
import java.io.Serializable;

/**
 * A JMS message to notify the master about the end of the simulation with a result of events.
 */
public class SimulationDoneJMSMessage implements Serializable {

    ResultSet resultSet;
    String agentId;

    /**
     * Returns a SimulationDoneJMSMessage instance
     * @param resultSet The result set tobe sent
     * @param agentId The sending agent Id
     */
    public SimulationDoneJMSMessage(ResultSet resultSet, String agentId) {
        this.resultSet = resultSet;
        this.agentId = agentId;
    }

    /**
     * Returns the results set
     * @return
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Sets the result set
     * @param resultSet
     */
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * Returns the sending agent identifier
     * @return
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * Sets the sending agent identifier
     * @param agentId
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}

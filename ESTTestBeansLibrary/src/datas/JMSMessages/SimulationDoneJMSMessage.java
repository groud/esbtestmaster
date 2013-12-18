/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas.JMSMessages;

import datas.ResultSet;
import java.io.Serializable;

/**
 *
 * @author gilles
 */
public class SimulationDoneJMSMessage implements Serializable {
    ResultSet resultSet;
    String agentId;

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}

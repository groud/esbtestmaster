/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

import datas.AgentType;
import datas.EventType;
import java.util.Date;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author samy
 */
public class ResultsLoggerTest {

    public ResultsLoggerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of setStartDate method, of class ResultsLogger.
     */
    @Test
    public void testSetStartDate() {
        System.out.println("setStartDate");
        Date date = null;
        ResultsLogger instance = null;
        instance.setStartDate(date);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeSimulationEvent method, of class ResultsLogger.
     */
    @Test
    public void testWriteSimulationEvent() throws Exception {
        System.out.println("writeSimulationEvent");
        int reqId = 0;
        AgentType agent = AgentType.CONSUMER;
        EventType event = EventType.REQUEST_SENT;
        ResultsLogger instance = new ResultsLogger("agentID");
        instance.setStartDate(new Date());
        
        instance.writeSimulationEvent(reqId, agent, event);
        // TODO review the generated test code and remove the default call to fail.
        
        System.out.println(instance.getResultSet().toString());
        assertTrue(false);
    }

}
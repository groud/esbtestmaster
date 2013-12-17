/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author samy
 */
@WebService()
public class SimulationWS {

    /**
     * Note : processing time = respTime + time to create the response
     * 
     * @param agentId of agent : which should be a producer
     * @param requestData : dummy data -> do not read
     * @param respTime : fake processing time (ms) before sending the response
     * @param respSize : response size in bytes
     * @return
     */
    @WebMethod(operationName = "requestOperation")
    public String requestOperation(@WebParam(name = "agentId")
    String agentId, @WebParam(name = "requestId")
    int requestId, @WebParam(name = "requestData")
    String requestData, @WebParam(name = "respTime")
    int respTime, @WebParam(name = "respSize")
    int respSize) {

        String ret;

        // TODO : remove (only for tests)
        ret = "agentId = " + agentId + "\n";
        ret += "requestId = " + requestId + "\n";
        ret += "requestData = " + requestData + "\n";
        ret += "respTime = " + respTime + "\n";
        ret += "respSize = " + respSize + "\n";

        // TODO : log the request
        
        // Wait for respTime ms
        try {
            Thread.sleep(respTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimulationWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Fill the same answer String with respSize characters
        if (respSize > 0) {
            char[] array = new char[respSize];
            Arrays.fill(array, 'A');
            ret += new String(array);
        }
        else {
            ret += "Error : response size not strictly positive" ;
        }

        return ret;
    }



}

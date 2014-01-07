/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

import interfaces.ProducerWSListener;
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
    private static ProducerWSListener listener;

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
    long respTime, @WebParam(name = "respSize")
    int respSize) {
        //Log the request
        if(listener instanceof ProducerWSListener) {
            listener.requestReceived(requestId,requestData,respTime,respSize);
        } else System.out.print("No listener has been provided. The event will not be logged.");
          
        // Wait for respTime ms
        try {
            Thread.sleep(respTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimulationWS.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Fill the same answer String with respSize characters
        String ret = "";
        if (respSize > 0) {
            char[] array = new char[respSize];
            Arrays.fill(array, 'A');
            ret += new String(array);
        } else {
            ret += "Error : response size not strictly positive" ;
        }

        //Log the response
        if(listener instanceof ProducerWSListener) {
            listener.responseSent(requestId);
        } else System.out.print("No listener has been provided. The event will not be logged.");

        return ret;
    }

    /**
     * Set the listener
     * @param listener
     */
    public static void setListener(ProducerWSListener listener) {
        SimulationWS.listener = listener;
    }
}

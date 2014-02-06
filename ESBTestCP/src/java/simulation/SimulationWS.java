package simulation;

import interfaces.ProducerWSListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * The SimulationWS responds with dummy data.
 * It can notify a ProducerWSListener about request received and responses sent.
 * When a request is received, it
 */
@WebService()
public class SimulationWS {

    private static ProducerWSListener listener;

    /**
     * Dummy request.
     * Waits the request time before sending the response.
     * The response payload has also to be specified.
     * @param The agent identifyer (should be a producer identifiyer).
     * @param Dummy data (This data is not used, but this size is important to qualify the ESB).
     * @param Fake processing time (ms) before sending the response.
     * @param Response size in bytes
     * @return A fake response (or eventully an error messages)
     */
    @WebMethod(operationName = "requestOperation")
    public String requestOperation(@WebParam(name = "agentId") String agentId, @WebParam(name = "requestId") int requestId, @WebParam(name = "requestData") String requestData, @WebParam(name = "respTime") long respTime, @WebParam(name = "respSize") int respSize) {
        //Log the request
        
        if (listener instanceof ProducerWSListener) {
            listener.requestReceived(requestId, requestData, respTime, respSize);
        } else {
            System.out.print("No listener has been provided. The event will not be logged.");
        }
        
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
            ret += "Error : response size not strictly positive";
        }
        
        //Log the response
        if (listener instanceof ProducerWSListener) {
            listener.responseSent(requestId);
        } else {
            System.out.print("No listener has been provided. The event will not be logged.");
        }
        
        return ret;
    }

    /**
     * Set the listener
     * @param listener
     */
    public static void setListener(ProducerWSListener listener) {
        SimulationWS.listener = listener;
        System.out.println(listener);
    }
}

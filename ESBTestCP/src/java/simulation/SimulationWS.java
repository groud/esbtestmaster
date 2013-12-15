/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

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
     *
     * @param id of agent : which should be a producer
     * @param requestData : dummy data -> do not read
     * @param respTime : fake processing time (ms) before sending the response
     * @param respSize : response size in bytes
     * @return
     */
    @WebMethod(operationName = "requestOperation")
    public String requestOperation(@WebParam(name = "id")
    int id, @WebParam(name = "requestData")
    String requestData, @WebParam(name = "respTime")
    int respTime, @WebParam(name = "respSize")
    int respSize) {
        
        return "It's alive !" ;
    }



}

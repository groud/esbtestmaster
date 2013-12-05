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
 * @author gilles
 */
@WebService()
public class SimWS {
    SimEntity sim;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Requestoperation")
    public String Requestoperation(@WebParam(name = "name")
    String name, @WebParam(name = "requestType")
    char requestType, @WebParam(name = "requestData")
    String requestData) {
        //TODO write your implementation code here:
        if(sim != null) {
            return sim.processRequest(requestType, requestData);
        }
        else {
            return null;
        }
    }
    /**
     * To be called by the AgentMonitor
     * @param sim
     */
    public void registerSim(SimEntity sim) {
        this.sim = sim;
    }

}

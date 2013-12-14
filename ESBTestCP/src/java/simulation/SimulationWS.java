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
    ProducerEntity prod;
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "Requestoperation")
    public String Requestoperation(@WebParam(name = "name")
    String name, @WebParam(name = "requestType")
    char requestType, @WebParam(name = "requestData")
    String requestData) {
        
        if(prod != null) {
            return prod.processRequest(requestType, requestData);
        }
        else {
            return null;
        }
    }
    
    /**
     * To be called by the AgentController after creating the producer
     * @param sim
     */
    public void registerProducer(ProducerEntity prod) {
        this.prod = prod;
    }
}

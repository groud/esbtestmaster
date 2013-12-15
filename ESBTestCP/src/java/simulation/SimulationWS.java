/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import monitoring.AgentController;

/**
 *
 * @author samy
 */
@WebService()
public class SimulationWS {
    // TODO : how to set a WS attribute without a reference to the WS object ?
    //ProducerEntity prod;
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "Requestoperation")
    public String Requestoperation(@WebParam(name = "name")
    String name, @WebParam(name = "requestType")
    char requestType, @WebParam(name = "requestData")
    String requestData) {

        // TODO : get the producer from another class (doesn't work yet)
        ProducerEntity prod = AgentController.getProducer();
        
        if(prod != null) {
            return prod.processRequest(requestType, requestData);
        }
        else {
            return "Error : Producer not configured";
        }
    }
    
    /**
     * To be called by the AgentController after creating the producer
     * @param sim*/
     
    public static void registerProducer(ProducerEntity prodArg) {
        prod = prodArg;
    }
     
}

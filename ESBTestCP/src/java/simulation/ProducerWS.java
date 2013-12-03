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
public class ProducerWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Requestoperation")
    public String Requestoperation(@WebParam(name = "id")
    String id, @WebParam(name = "requestType")
    char requestType) {
        //TODO write your implementation code here:
        return null;
    }

}

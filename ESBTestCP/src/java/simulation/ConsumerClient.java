/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

import javax.xml.ws.BindingProvider;

/**
 *
 * @author samy
 */
public class ConsumerClient {

    public ConsumerClient() {
        //TODO
    }

    /**
     *
     * @param req
     * @param producerUrl e.g. "http://localhost:8090/CompositeAppProxyService1/casaPort1"
     */
    public void sendRequest(String req, String producerUrl) {
        try { // Call Web Service Operation
            simulationRef.SimulationWSService service = new simulationRef.SimulationWSService();
            simulationRef.SimulationWS port = service.getSimulationWSPort();

            // Dynamic URL binding
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, producerUrl);
            
            java.lang.String name = req;
            int requestType = 0;
            java.lang.String requestData = "";
            // TODO process result here
            java.lang.String result = port.requestoperation(name, requestType, requestData);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

}

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

            // TODO initialize WS operation arguments here
            int id = 0;
            java.lang.String requestData = "aaaaaaabbbbbbccccc";
            int respTime = 150;
            int respSize = 256;
            // TODO process result here
            java.lang.String result = port.requestOperation(id, requestData, respTime, respSize);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }


    }

}

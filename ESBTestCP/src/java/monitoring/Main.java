/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monitoring;

import javax.xml.ws.BindingProvider;
import simulation.ConsumerEntity;
//import simulation.ProducerEntity;
//import simulation.SimulationWS;

/**
 *
 * @author samy
 */
public class Main {

    public static void main(String[] args) {
      
        String producerUrl = "http://localhost:8090/ESBTestCompositeService1/casaPort1";
        
         // Test ConsumerEntity
        ConsumerEntity cons = new ConsumerEntity();

        cons.sendRequest("id", 16, 1000, 32, producerUrl);
         
    }
 
}

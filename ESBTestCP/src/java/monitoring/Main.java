/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monitoring;

import simulation.ConsumerClient;
import simulation.ProducerEntity;
import simulation.SimulationWS;

/**
 *
 * @author samy
 */
public class Main {

    public static void main(String[] args) {
        AgentController ctrl = new AgentController();

        // Create a new producer
        ctrl.configureAS(1, 0);

        if(AgentController.getProducer() == null) {
            System.out.println("NULL PRODUCER");
        }

        // WS client test
        String url = "http://localhost:8080/ESBTestCP/SimulationWSService";
        ConsumerClient client = new ConsumerClient();
        System.out.println("SENDING REQUEST");
        client.sendRequest("Test WS", url);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monitoring;
import datas.*;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public interface AgentControllerInterface {
    public void configureAS(int behavior, int id);
    public void configureProducer(int responseTime, int messageLength);

    public void configureConsumer(ArrayList<SimulationStep> steps);
   // void configureConsumer(SimulationScenario scenario);

}

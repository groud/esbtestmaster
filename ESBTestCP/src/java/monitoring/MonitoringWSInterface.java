/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monitoring;

//import datas.*;

/**
 *
 * @author root
 */
public interface MonitoringWSInterface {
    public void configureAS(int behavior, int id);
    public void configureProvider(int responseTime, int messageLength);
   // void configureConsumer(SimulationScenario scenario);

}

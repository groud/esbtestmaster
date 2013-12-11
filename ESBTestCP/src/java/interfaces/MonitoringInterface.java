/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

//import datas.*;

/**
 *
 * @author root
 */
public interface MonitoringInterface {
    public void configureAS(int behavior, int id);
    public void configureProvider(int responseTime, int messageLength);
    //void configureConsumer(SimulationScenario scenario);
    void startSimulation();
    void stopSimulation();

}

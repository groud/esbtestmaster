/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

/**
 *
 * @author gilles
 */
public interface UserInputsListener {
    public void startSimulation();
    public void stopSimulation();
    public void loadScenario(String XMLfile);
    public void calculateKPI(String XMLfile);
}

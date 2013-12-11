/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import interfaces.UserInputsListener;

/**
 *
 * @author gilles
 */
public class MasterController implements UserInputsListener {
    private CLI shell;
    private ScenarioReader sr;

    public MasterController() {
        shell = new CLI(this);
        sr = new ScenarioReader();

        shell.launch();
    }

    public void startSimulation() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stopSimulation() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void loadScenario(String XMLfile) {
        sr.readXMLFile(XMLfile);
    }

    public void calculateKPI(String XMLfile) {
        //
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import datas.SimulationScenario;

/**
 *
 * @author gilles
 */
public class Debug {
    static private boolean activated = false;

    static public void info(Object msg) {
        System.out.println("INFO : "+msg.toString());
    }


    static public boolean isActivated() {
        return activated;
    }

    static public void setActivated(boolean activated) {
        Debug.activated = activated;
    }
}

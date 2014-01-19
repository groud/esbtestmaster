/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 * Debug is useful to display debugging information when it is activated.
 * It does displays messages using the console
 */
public class Debug {
    static private boolean activated = false;

    /**
     * Display an informationnal message if the debug mode is activated.
     * @param msg
     */
    static public void info(Object msg) {
        if(Debug.isActivated()) System.out.println("INFO : "+msg.toString());
    }

    /**
     * Returns true if the debug mode in on.
     * @return
     */
    static public boolean isActivated() {
        return activated;
    }

    /**
     * Activate or desactivate the debug mode.
     * @param activated
     */
    static public void setActivated(boolean activated) {
        Debug.activated = activated;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import datas.ResultSet;
import interfaces.ResultKeeperInterface;

/**
 *
 * @author gilles
 */
public class XMLResultKeeper implements ResultKeeperInterface{
    private String filename;

    public XMLResultKeeper (String filename) {
        this.filename = filename;
        this.init();
    }

    private void init() {
        //TODO : Erase the file if it exists, create the file and put the firsts tags.
    }

    // -------------------------------
    //   INTERFACES IMPLEMENTATIONS
    // -------------------------------
    public void addLog(ResultSet resultSet) {
        //TODO : Add a log to the selected file
    }

    public ResultSet getLog() {
        //TODO : Get the results from the file
        return null;
    }

    public void clearLog() {
         //TODO : Empty the file
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import datas.ResultSet;

/**
 *
 * @author gilles
 */
public interface SimulationMessageListener {
    public void simulationDone(ResultSet resultSet);
    public void fatalErrorOccured();
}

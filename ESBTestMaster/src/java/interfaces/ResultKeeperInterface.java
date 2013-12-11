/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;
import datas.ResultSet;

/**
 *
 * @author root
 */
public interface ResultKeeperInterface {
    public void addLog(ResultSet resultSet);
    public ResultSet getLog();
    public void clearLog();
}

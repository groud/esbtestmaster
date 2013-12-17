/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;
import Exceptions.BadXMLException;
import datas.ResultSet;
import java.io.IOException;

/**
 *
 * @author root
 */
public interface ResultKeeperInterface {
    public void addLog(ResultSet resultSet) throws IOException, BadXMLException;
    public ResultSet getLog() throws IOException, BadXMLException;
    public void clearLog();
}

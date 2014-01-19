package interfaces;

import Exceptions.BadXMLException;
import datas.ResultSet;
import java.io.IOException;

/**
 * ResultKeeperInterface
 */
public interface ResultKeeperInterface {
    public void addLog(ResultSet resultSet) throws IOException, BadXMLException;
    public ResultSet getLog() throws IOException, BadXMLException;
    public void clearLog();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;
import datas.KPISet;
import datas.ResultSet;
import java.io.IOException;

/**
 *
 * @author bamba
 */
public interface KPICalculatorInterface {
    public KPISet calculateKPI(ResultSet resultSet);
    public void saveKPItoXMLFile(String filename) throws IOException;
}

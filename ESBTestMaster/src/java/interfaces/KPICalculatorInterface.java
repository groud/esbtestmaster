/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;
import Exceptions.BadXMLException;
import datas.KPISet;
import java.io.IOException;

/**
 *
 * @author bamba
 */
public interface KPICalculatorInterface {
    public KPISet calculateKPIfromXML(String filename) throws BadXMLException, IOException;
    public void saveKPItoXMLFile(String filename) throws IOException;
}

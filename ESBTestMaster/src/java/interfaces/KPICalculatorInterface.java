/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;
import datas.KPISet;

/**
 *
 * @author bamba
 */
public interface KPICalculatorInterface {
    public KPISet calculateKPIfromXML(String filename);
    public void saveKPItoXMLFile(String filename);
}

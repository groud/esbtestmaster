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
public interface resulLogInterface {
    public void writeLOg(Object dataLog);
    public ResultSet getLogForKPICalcul(Object dataLog);

}

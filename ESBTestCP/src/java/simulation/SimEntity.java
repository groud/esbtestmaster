/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

/**
 *
 * @author samy
 */
public abstract class SimEntity {
    private String name;


    /**
     * TODO : change return type to byte[] or a Response object
     * @param reqType
     * @param reqData
     * @return
     */
    public String processRequest(char reqType, String reqData) {
        return "DUMMY";
    }
}

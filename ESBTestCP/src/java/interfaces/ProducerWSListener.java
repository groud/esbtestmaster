/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

/**
 *
 * @author gilles
 */
public interface ProducerWSListener {
    public void requestReceived(int requestId, String requestData, long respTime, int respSize);
    public void responseSent(int requestId);
}

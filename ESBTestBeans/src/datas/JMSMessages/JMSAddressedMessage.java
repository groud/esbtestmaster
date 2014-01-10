/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas.JMSMessages;

import java.io.Serializable;

/**
 *
 * @author gilles
 */
public class JMSAddressedMessage implements Serializable{
    String receiver;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

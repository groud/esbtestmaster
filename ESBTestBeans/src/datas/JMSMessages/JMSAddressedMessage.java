package datas.JMSMessages;

import java.io.Serializable;

/**
 * A message to be sent to an agent. The receiver id is used to address the message (using JMS)
 */
public class JMSAddressedMessage implements Serializable {

    String receiver;

    /**
     * Returns the receiver id.
     * @return
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver identifier
     * @param receiver
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

package interfaces;

/**
 * ProducerWSListener
 */
public interface ProducerWSListener {
    public void requestReceived(int requestId, String requestData, long respTime, int respSize);
    public void responseSent(int requestId);
}

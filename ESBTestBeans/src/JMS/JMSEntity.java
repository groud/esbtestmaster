package JMS;

import java.io.Serializable;
import javax.jms.*;
import javax.naming.*;

/**
 * Generic JMS handler.
 * The JMSEntity is able to send serializable objects using JMS. It needs
 * Works with both queue and topic, the choice come from the object in JNDI
 */
public class JMSEntity {

    public static String CONNECTION_FACTORY = "TopicConnectionFactory";
    public static String CONFIG_DESTINATION = "config";
    public static String RESULTS_DESTINATION = "results";
    private Context context = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Destination toDestination = null;
    private Destination fromDestination = null;
    private Session sessionOut = null;
    private Session sessionIn = null;
    private MessageProducer sender = null;
    private MessageConsumer receiver = null;

    /**
     * Return and init a JMSEntity instance.
     * @param connectionFactory The connection factory identifier.
     * @param destinationOut The topic/queue identifier used to send messages.
     * @param destinationIn The topic/queue identifier used to receive messages.
     */
    public JMSEntity(String connectionFactory, String destinationOut, String destinationIn) {
        try {
            //To get to JNDI context
            context = new InitialContext();

            //To get a ConnectionFactory from JNDI
            factory = (ConnectionFactory) context.lookup(connectionFactory);

            //Creating a connection from the factory
            connection = factory.createConnection();

            //Creating 2 sessions from the connection
            sessionOut = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            sessionIn = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creating a producer to send config objects
            toDestination = (Destination) context.lookup(destinationOut);
            sender = sessionOut.createProducer(toDestination);

            //Creating a consumer to receive results objects
            fromDestination = (Destination) context.lookup(destinationIn);
            receiver = sessionIn.createConsumer(fromDestination);

            //Starting the connection
            connection.start();

        } catch (Exception e) {
            e.printStackTrace();
            finalize();
        }
    }

    //send an object to the topic
    public boolean send(Serializable objectToSend) {

        try {
            ObjectMessage message = sessionOut.createObjectMessage(objectToSend);
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            finalize();
        }
        return true;
    }

    //receive an object from the topic
    public Serializable receive() {
        Serializable result = null;
        try {
            Message message = receiver.receive();
            if (message instanceof ObjectMessage) {
                ObjectMessage object = (ObjectMessage) message;
                result = object.getObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            finalize();
        }
        return result;
    }

    @Override
    protected void finalize() {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

package JMS;

import java.io.Serializable;
import javax.jms.*;
import javax.naming.*;


/**
 *
 * @author Adrien
 */

  //generic JMS handler
  //works with both queue and topic, the choice come from the object in JNDI

public class JMSEntity {

    private Context context = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Destination toDestination = null;
    private Destination fromDestination = null;
    private Session session = null;
    private MessageProducer sender = null;
    private MessageConsumer receiver = null;


    public JMSEntity(String connectionFactory, String destinationOut, String destinationIn) {
        try {
            //To get to JNDI context
            context = new InitialContext();

            //To get a ConnectionFactory from JNDI
            factory = (ConnectionFactory) context.lookup(connectionFactory);

            //Creating a connection from the factory
            connection = factory.createConnection();

            //Creating a session from the connection
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creating a producer to send config objects
            toDestination = (Destination) context.lookup(destinationOut);
            sender = session.createProducer(toDestination);

            //Creating a consumer to receive results objects
            fromDestination = (Destination) context.lookup(destinationIn);
            receiver = session.createConsumer(fromDestination);

            //Starting the connection
            connection.start();

        } catch (Exception e) {
            finalize();
        }
    }

    //send an object to the topic
    public boolean send(Serializable objectToSend) {
        try {
            final ObjectMessage message = session.createObjectMessage();
            message.setObject(objectToSend);
            sender.send(message);
        } catch (Exception e) {
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

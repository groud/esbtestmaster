package monitoring;

import java.io.Serializable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 *
 * @author Adrien
 */

  //generic JMS handler
  //works with both queue and topic, the choice come from the object in JNDI

public class AgentMessageHandler {

    Context context = null;
    ConnectionFactory factory = null;
    Connection connection = null;
    Destination toTopic = null;
    Destination fromTopic = null;
    Session session = null;
    MessageProducer sender = null;
    MessageConsumer receiver = null;


    public AgentMessageHandler() {
        try {
            //To get to JNDI context
            context = new InitialContext();

            //To get a ConnectionFactory from JNDI
            factory = (ConnectionFactory) context.lookup("TopicConnectionFactory");

            //Creating a connection from the factory
            connection = factory.createConnection();

            //Creating a session from the connection
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creating a producer to send config objects
            toTopic = (Destination) context.lookup("results"); //Topic named "results"
            sender = session.createProducer(toTopic);

            //Creating a consumer to receive results objects
            fromTopic = (Destination) context.lookup("config"); //Topic named "config"
            receiver = session.createConsumer(fromTopic);

            //Starting the connection
            connection.start();
        } catch (Exception e) {
            e.printStackTrace();
            finalize();
        }
    }

    //send an object to the topic
    public boolean sendToTopic(Serializable objectToSend) {
        try {
            final ObjectMessage message = session.createObjectMessage();
            message.setObject(objectToSend);
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            finalize();
        }
        return true;
    }

    //receive an object from the topic
    public Serializable receiveFromTopic() {
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

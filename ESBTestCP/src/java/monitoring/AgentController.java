/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 
package monitoring;


import interfaces.MonitoringInterface;
import javax.jws.WebService;

import datas.*;
import java.util.ArrayList;
import simulation.*;

/**
 *
 * @author root
 */


public class AgentController implements AgentControllerInterface{

    ConsumerEntity consumer;
    ProducerEntity producer;

public void configureAS(int behavior, int id)
     {
        if(behavior==0){

            ConsumerEntity consumer = new ConsumerEntity();
            consumer.setId(id);

            System.out.print("you are consumer and your id is " + id);//just a test
            //Here We will invoke the configuration method
        }
        else{

            ProducerEntity producer = new ProducerEntity();
            consumer.setId(id);
             System.out.print("you are provider and your id is" +  id);//just a test
              //Here We will invoke the configuration method

        }
     }

    public void configureProducer(int responseTime, int messageLength)
    {

        producer.configureProducer(responseTime, messageLength);
        System.out.print("response Time is" +responseTime+ "and Message Length" + messageLength);//just a test
         //Here We will invoke the provider method
    }

    public void configureConsumer(ArrayList<SimulationStep> steps) {

        consumer.configureConsumer(steps);
        throw new UnsupportedOperationException("Not supported yet.");
    }
}


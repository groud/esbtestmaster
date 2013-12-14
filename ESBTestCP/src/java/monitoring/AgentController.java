/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/



// EN stand by, en attente du JMs


package monitoring;


import interfaces.MonitoringInterface;
import javax.jws.WebService;

import datas.*;
import java.util.ArrayList;
import simulation2.*;

/**
<<<<<<< HEAD
*
* @author mariata
*/



public class AgentController implements AgentControllerInterface{

<<<<<<< HEAD
    public void configureAS(int behavior, int id)
=======
    // static keywords so that SimulationWS can access producer
    // -> this method DOES NOT WORK yet
    static ConsumerEntity consumer;
    static ProducerEntity producer;
    
public void configureAS(int behavior, int id)
>>>>>>> 9a779a26906010e85820516bd3a20b26b0f82c5b

    {
       if(behavior==0){

           consumer = new ConsumerEntity();

           consumer.setId(id);

           System.out.println("you are consumer and your id is " + id);//just a test

           //Here We will invoke the configuration method

       }

       else{

           producer = new ProducerEntity();

           producer.setId(id);

            System.out.println("you are provider and your id is" +  id);//just a test

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

    public static ProducerEntity getProducer() {
        return producer;
    }

   
}
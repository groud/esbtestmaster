/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD:ESBTestCP/src/java/monitoring/MonitoringWS.java
 
package monitoring;


import interfaces.MonitoringInterface;
import javax.jws.WebService;
=======
>>>>>>> 175bba9b70d6d3fca82561b0ed19027b8acfd999:ESBTestCP/src/java/monitoring/AgentController.java

package monitoring;

import datas.*;
/**
 *
 * @author root
 */
<<<<<<< HEAD:ESBTestCP/src/java/monitoring/MonitoringWS.java
@WebService()
public class MonitoringWS implements MonitoringInterface{

=======
public class AgentController implements AgentControllerInterface{
>>>>>>> 175bba9b70d6d3fca82561b0ed19027b8acfd999:ESBTestCP/src/java/monitoring/AgentController.java


public void configureAS(int behavior, int id)
     {
        if(behavior==0)

           

            System.out.print("you are consumer and your id is " + id);//just a test
            //Here We will invoke the configuration method
        else
             System.out.print("you are provider and your id is" +  id);//just a test
              //Here We will invoke the configuration method

     }

    public void configureProducer(int responseTime, int messageLength)
    {
        System.out.print("response Time is" +responseTime+ "and Message Length" + messageLength);//just a test
         //Here We will invoke the provider method
    }

    public void configureConsumer(ArrayList<SimulationStep> steps) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}


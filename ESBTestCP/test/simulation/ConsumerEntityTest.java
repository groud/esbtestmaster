/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

import datas.AgentType;
import datas.EventType;
import datas.ProducerConfiguration;
import datas.SimulationScenario;
import datas.SimulationStep;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author samy
 */
public class ConsumerEntityTest {

    public ConsumerEntityTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of configureConsumer method, of class ConsumerEntity.
     */
    @Ignore
    @Test
    public void testConfigureConsumer() {
        System.out.println("configureConsumer");
        String id = "";
        SimulationScenario simulationScenario = null;
        ConsumerEntity instance = new ConsumerEntity(id);
        instance.configureConsumer(simulationScenario);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startSimulation method, of class ConsumerEntity.
     * //TODO : test max rate, max respSize, max requestSize
     */
    //@Ignore
    @Test
    public void testStartSimulation() {
        System.out.println("startSimulation");
        String producerId = "producer1";
        String consumerId = "consumer1";
        
        
        int burstStartDate1 = 0;
        int burstStopDate1 = 5000;
        int burstRate1 = 1;
        int requestPayloadSize1 = 16;
        long processTime1 = 0;
        int responsePayloadSize1 = 32;

        int burstStartDate2 = 2500;
        int burstStopDate2 = 7000;
        int burstRate2 = 10;
        int requestPayloadSize2 = 16;
        long processTime2 = 0;
        int responsePayloadSize2 = 32;

        // Test ConsumerEntity
        ConsumerEntity instance = new ConsumerEntity(consumerId);        

        SimulationScenario ss = new SimulationScenario();
        ProducerConfiguration pc = new ProducerConfiguration();
        pc.setAgentId(producerId);
        pc.setWsAddress("http://localhost:8090/ESBTestCompositeService1/casaPort1");
        ss.getAgentsconfiguration().add(pc);

        ss.addStep(new SimulationStep(consumerId, producerId, burstStartDate1, burstStopDate1, burstRate1, requestPayloadSize1, processTime1, responsePayloadSize1));
        //ss.addStep(new SimulationStep(consumerId, producerId, burstStartDate2, burstStopDate2, burstRate2, requestPayloadSize2, processTime2, responsePayloadSize2));
        

        instance.configureConsumer(ss);
        instance.startSimulation();
        
        try {
            Thread.sleep(15000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsumerEntityTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(instance.getLogger().getResultSet().toString());
    }

    /**
     * Test of abortSimulation method, of class ConsumerEntity.
     */
    @Ignore
    @Test
    public void testAbortSimulation() {
       System.out.println("startSimulation");
        String producerId = "producer1";
        String consumerId = "consumer1";
        // Test ConsumerEntity
        ConsumerEntity instance = new ConsumerEntity(consumerId);

        SimulationScenario ss = new SimulationScenario();
        ProducerConfiguration pc = new ProducerConfiguration();
        pc.setAgentId(producerId);
        pc.setWsAddress("http://localhost:8090/ESBTestCompositeService1/casaPort1");
        ss.getAgentsconfiguration().add(pc);

        ss.addStep(new SimulationStep(consumerId, producerId, 0, 5000, 1, 16, 1000L, 20));
        ss.addStep(new SimulationStep(consumerId, producerId, 5000, 7500, 2, 16, 1000L, 20));

        instance.configureConsumer(ss);
        instance.startSimulation();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsumerEntityTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        instance.abortSimulation();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsumerEntityTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(instance.getLogger().getResultSet().toString());
    }    

   

}
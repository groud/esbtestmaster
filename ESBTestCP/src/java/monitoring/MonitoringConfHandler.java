/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monitoring;

import simulation.*;



/**
 *
 * @author root
 */
public class MonitoringConfHandler {

       

    public  int id;
    public int agentType;


     // Provider
    private float responseTime;
    private float responseSize;

    // Consumer

   private ArrayLIst<SimulationStep> steps;


   //create C/P
   public void createAgent(){
   if (agentType == 1) {
        ProviderEntity simulation = new ProviderEntity(id, responseTime, responseSize);}
   else {
       ConsumerEntity simulation = new ConsumerEntity( steps, id);}
   }
   

    // Constructeur

   // public MonitoringConfHandler()




}

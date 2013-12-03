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
        Provider simulation = new Provider(id, responseTime, responseSize);}
   else {
       Consumer simulation = new Consumer( steps, id);}
   }
   

    // Constructeur

   // public MonitoringConfHandler()




}

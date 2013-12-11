/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

package simulation;

import java.util.ArrayList;
import java.util.Timer;

import datas.*;

import java.util.TimerTask;
/**
*
* @author mariata
*/
public class ConsumerEntity extends SimulationEntity {


    private ArrayList<SimulationStep> steps;

    Timer timer;

    //constructor

    public ConsumerEntity() {

    }

public void configureConsumer( ArrayList<SimulationStep> steps) {

     this.steps = steps;

}

    
    @Override

  public void startSimulation( ) {

    SimulationStep step = steps.get(0);



     

       timer = new Timer();

       int nbRequest = (int) (step.getBurstDuration() * step.getBurstRate());

       long period = (long) (step.getBurstDuration() / nbRequest);

        timer.scheduleAtFixedRate(new TimerTask(){

        @Override

          public void run() {

           //code send resquest

          }

        }, step.getBurstStartDate(),period );



    }

    @Override

    public void abortSimulation() {



    }

}
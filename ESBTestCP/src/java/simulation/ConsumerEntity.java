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

    public void sendReqest(){

      // call web service

    }





    @Override

    public void startSimulation( ) {



        timer = new Timer();

        int period = 10;


         timer.scheduleAtFixedRate(new TimerTask(){

         @Override

           public void run() {

            //code send resquest

           }

         }, 0, period);



    }

    @Override

    public void abortSimulation() {



    }

}
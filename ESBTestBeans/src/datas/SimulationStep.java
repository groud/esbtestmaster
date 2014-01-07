package datas;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author gilles
 */
public class SimulationStep implements Comparable<SimulationStep>, Serializable {

    private String srcID;
    private String destID;
    private long burstStartDate;    //ms from simulation start
    private long burstStopDate;     //ms from simulation start
    private float burstRate;        // number of requests per second
    private int requestPayloadSize;
    private long processTime;
    private int responsePayloadSize;

    public SimulationStep(){
    }
    
    public SimulationStep(String srcID, String destID, int burstStartDate, int burstStopDate, int burstRate, int requestPayloadSize, long processTime, int responsePayloadSize) {
        this.srcID = srcID;
        this.destID = destID;
        this.burstStartDate = burstStartDate;
        this.burstStopDate = burstStopDate;
        this.burstRate = burstRate;
        this.requestPayloadSize = requestPayloadSize;
        this.processTime = processTime;
        this.responsePayloadSize = responsePayloadSize;
    }

    public float getBurstRate() {
        return burstRate;
    }

    public void setBurstRate(float burstRate) {
        this.burstRate = burstRate;
    }

    public long getBurstStartDate() {
        return burstStartDate;
    }

    public void setBurstStartDate(long burstStartDate) {
        this.burstStartDate = burstStartDate;
    }

    public long getBurstStopDate() {
        return burstStopDate;
    }

    public void setBurstStopDate(long burstStopDate) {
        this.burstStopDate = burstStopDate;
    }

    public String getDestID() {
        return destID;
    }

    public void setDestID(String destID) {
        this.destID = destID;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }

    public int getRequestPayloadSize() {
        return requestPayloadSize;
    }

    public void setRequestPayloadSize(int requestPayloadSize) {
        this.requestPayloadSize = requestPayloadSize;
    }

    public int getResponsePayloadSize() {
        return responsePayloadSize;
    }

    public void setResponsePayloadSize(int responsePayloadSize) {
        this.responsePayloadSize = responsePayloadSize;
    }

    public String getSrcID() {
        return srcID;
    }

    public void setSrcID(String srcID) {
        this.srcID = srcID;
    }

    // ----------------------------------
    //   ACCESSORS
    // ----------------------------------
    //Other
    public long getBurstDuration() {
        return this.getBurstStopDate() - this.getBurstStartDate();
    }

    //Comparison method
    public int compareTo(SimulationStep step) {
        if (this.getBurstStartDate() < step.getBurstStartDate()) {
            return -1;
        }
        if (this.getBurstStartDate() < step.getBurstStartDate()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return this.getSrcID() + " -> " + this.getDestID() + " : " + this.getBurstStartDate() + "-" + this.getBurstStopDate() + " at " + this.getBurstRate() + "req/s (->"+this.getRequestPayloadSize()+" bytes,"+this.getProcessTime()+"ms,<-"+this.getResponsePayloadSize()+" bytes)\n";
    }
}

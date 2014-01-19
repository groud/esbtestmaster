package datas;

import java.io.Serializable;

/**
 * Decribes a burst of requests in a scenario.
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

    /**
     * Returns a non configurated SimulationStep instance.
     */
    public SimulationStep() {
    }

    /**
     * Returns a SimulationStep instance
     * @param srcID The source consumer identifier, that will send the request.
     * @param destID The destination producer identifier, that will receive the request.
     * @param burstStartDate The burst start date, after starting the simulation (in milliseconds)
     * @param burstStopDate The burst stop date, after starting the simulation (in milliseconds)
     * @param burstRate The burst rate (in requests per second)
     * @param requestPayloadSize The request payload size
     * @param processTime The fake processing time of each request
     * @param responsePayloadSize The fake response payload size.
     */
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

    // ----------------------------------
    //   ACCESSORS
    // ----------------------------------
    /**
     * Returns the burst rate (in requests per second)
     * @return
     */
    public float getBurstRate() {
        return burstRate;
    }

    /**
     * Set the burst rate (in requests per seconds)
     * @param burstRate
     */
    public void setBurstRate(float burstRate) {
        this.burstRate = burstRate;
    }

    /**
     * Returns the burst start rate (in milliseconds from the beginning of the simulation)
     * @return
     */
    public long getBurstStartDate() {
        return burstStartDate;
    }

    /**
     * Sets the burst start date  (in milliseconds from the beginning of the simulation)
     * @param burstStartDate
     */
    public void setBurstStartDate(long burstStartDate) {
        this.burstStartDate = burstStartDate;
    }

    /**
     * Returns the burst stop date (in milliseconds from the beginning of the simulation)
     * @return
     */
    public long getBurstStopDate() {
        return burstStopDate;
    }

    /**
     * Sets the burst stop date (in milliseconds from the beginning of the simulation)
     * @param burstStopDate
     */
    public void setBurstStopDate(long burstStopDate) {
        this.burstStopDate = burstStopDate;
    }

    /**
     * Returns the receiver agent identifier
     * @return
     */
    public String getDestID() {
        return destID;
    }

    /**
     * Sets the receiver agent identifier
     * @param destID THe agent id
     */
    public void setDestID(String destID) {
        this.destID = destID;
    }

    /**
     * Returns the fake processing time for each request (in milliseconds)
     * @return
     */
    public long getProcessTime() {
        return processTime;
    }

    /**
     * Returns the fake processing time for each request (inmilliseconds)
     * @param processTime
     */
    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }

    /**
     * Returns the request payload size (in bytes)
     * @return
     */
    public int getRequestPayloadSize() {
        return requestPayloadSize;
    }

    /**
     * Sets the request payload size (in bytes)
     * @param requestPayloadSize
     */
    public void setRequestPayloadSize(int requestPayloadSize) {
        this.requestPayloadSize = requestPayloadSize;
    }

    /**
     * Returns the response payload size (in bytes)
     * @return
     */
    public int getResponsePayloadSize() {
        return responsePayloadSize;
    }

    /**
     * Sets the response payload size (in bytes)
     * @param responsePayloadSize
     */
    public void setResponsePayloadSize(int responsePayloadSize) {
        this.responsePayloadSize = responsePayloadSize;
    }

    /**
     * Returns the sending consumer identifier.
     * @return
     */
    public String getSrcID() {
        return srcID;
    }

    /**
     * Sets the sending consumer identifier.
     * @param srcID
     */
    public void setSrcID(String srcID) {
        this.srcID = srcID;
    }

    //Other
    /**
     * Returns the burst duration (in milliseconds)
     * @return
     */
    public long getBurstDuration() {
        return this.getBurstStopDate() - this.getBurstStartDate();
    }

    /**
     * Compares this step with another using the burst start date
     * @param step The step to compare this step to.
     * @return The comparison result
     */
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

    /**
     * Returns a String representation of this object.
     * @return
     */
    @Override
    public String toString() {
        return this.getSrcID() + " -> " + this.getDestID() + " : " + this.getBurstStartDate() + "-" + this.getBurstStopDate() + " at " + this.getBurstRate() + "req/s (->" + this.getRequestPayloadSize() + " bytes," + this.getProcessTime() + "ms,<-" + this.getResponsePayloadSize() + " bytes)\n";
    }
}

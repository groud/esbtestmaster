/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas;

import java.util.Date;

/**
 *
 * @author gilles
 */
public class SimulationStep implements Comparable<SimulationStep> {
    private String ConsumerID;
    private String ProviderID;
    private Date burstStartDate;
    private float burstDuration;
    private float burstRate;
    private float dataPayloadSize;

    // ----------------------------------
    //   ACCESSORS
    // ----------------------------------
    public String getConsumerID() {
        return ConsumerID;
    }

    public void setConsumerID(String ConsumerID) {
        this.ConsumerID = ConsumerID;
    }

    public String getProviderID() {
        return ProviderID;
    }

    public void setProviderID(String ProviderID) {
        this.ProviderID = ProviderID;
    }

    public float getBurstDuration() {
        return burstDuration;
    }

    public void setBurstDuration(float burstDuration) {
        this.burstDuration = burstDuration;
    }

    public float getBurstRate() {
        return burstRate;
    }

    public void setBurstRate(float burstRate) {
        this.burstRate = burstRate;
    }

    public Date getBurstStartDate() {
        return burstStartDate;
    }

    public void setBurstStartDate(Date burstStartDate) {
        this.burstStartDate = burstStartDate;
    }

    public float getDataPayloadSize() {
        return dataPayloadSize;
    }

    public void setDataPayloadSize(float dataPayloadSize) {
        this.dataPayloadSize = dataPayloadSize;
    }

    //Comparison method
    public int compareTo(SimulationStep step) {
        if (this.getBurstStartDate().before(step.getBurstStartDate())) return -1;
        if (this.getBurstStartDate().equals(step.getBurstStartDate())) return 0;
        else return 1;
    }
}

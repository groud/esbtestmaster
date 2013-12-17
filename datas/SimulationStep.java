/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas;


/**
 *
 * @author gilles
 */
public class SimulationStep implements Comparable<SimulationStep> {
    private String ConsumerID;
    private String ProviderID;

    private long burstStartDate;
    private long burstStopDate;

    private float burstRate;
    private int dataPayloadSize;

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

    public int getDataPayloadSize() {
        return dataPayloadSize;
    }

    public void setDataPayloadSize(int dataPayloadSize) {
        this.dataPayloadSize = dataPayloadSize;
    }
    public long getBurstStopDate() {
        return burstStopDate;
    }

    public void setBurstStopDate(long burstStopDate) {
        this.burstStopDate = burstStopDate;
    }

    //Other
    public long getBurstDuration() {
        return this.getBurstStopDate() - this.getBurstStartDate();
    }


    //Comparison method
    public int compareTo(SimulationStep step) {
        if (this.getBurstStartDate()<step.getBurstStartDate()) return -1;
        if (this.getBurstStartDate()<step.getBurstStartDate()) return 0;
        else return 1;
    }

    @Override
    public String toString() {
        return this.getConsumerID()+" -> "+this.getProviderID()+" : "+this.getBurstStartDate()+"-"+this.getBurstStopDate()
                +" at "+this.getBurstRate()+"req/s\n";
    }
}

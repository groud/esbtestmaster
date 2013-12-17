/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datas;

import java.util.HashMap;

/**
 *
 * @author gilles
 */
public class KPISet {
    private HashMap<String,Integer> numberOfRequestSent;
    private HashMap<String,Integer> numberOfRequestLost;
    private HashMap<String, Long> averageResponseTime;

    public HashMap<String, Long> getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(HashMap<String, Long> averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public HashMap<String, Integer> getNumberOfRequestLost() {
        return numberOfRequestLost;
    }

    public void setNumberOfRequestLost(HashMap<String, Integer> numberOfRequestLost) {
        this.numberOfRequestLost = numberOfRequestLost;
    }

    public HashMap<String, Integer> getNumberOfRequestSent() {
        return numberOfRequestSent;
    }

    public void setNumberOfRequestSent(HashMap<String, Integer> numberOfRequestSent) {
        this.numberOfRequestSent = numberOfRequestSent;
    }

}

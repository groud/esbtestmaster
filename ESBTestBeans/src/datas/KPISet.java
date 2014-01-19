package datas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Describes a set of Key Perforamance Indicators.
 */
public class KPISet {
    private HashMap<String,Integer> numberOfRequestSent;
    private HashMap<String,Integer> numberOfRequestLost;
    private HashMap<String, Long> averageResponseTime;

    /**
     * Returns a hashmap containing the average respose time indexed by consumers ids.
     * @return The Hashmap
     */
    public HashMap<String, Long> getAverageResponseTime() {
        return averageResponseTime;
    }

    /**
     * Set the average response time.
     * @param averageResponseTime A hashmap containing the average respose time indexed by consumers ids
     */
    public void setAverageResponseTime(HashMap<String, Long> averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    /**
     * Returns a hashmap containing the number of requests lost indexed by consumers ids.
     * @return The hashmap
     */
    public HashMap<String, Integer> getNumberOfRequestLost() {
        return numberOfRequestLost;
    }

    /**
     * Set the number of requests lost.
     * @param numberOfRequestLost A hashmap containing the number of requests lost indexed by consumers ids.
     */
    public void setNumberOfRequestLost(HashMap<String, Integer> numberOfRequestLost) {
        this.numberOfRequestLost = numberOfRequestLost;
    }

    /**
     * Get an hashmap containing the number of requests sent indexed by consumers ids.
     * @return The hashmap
     */
    public HashMap<String, Integer> getNumberOfRequestSent() {
        return numberOfRequestSent;
    }

    /**
     * Set the number of requests sent
     * @param numberOfRequestSent A hashmap containing the number of requests sent indexed by consumers ids.
     */
    public void setNumberOfRequestSent(HashMap<String, Integer> numberOfRequestSent) {
        this.numberOfRequestSent = numberOfRequestSent;
    }

    /**
     * Returns a String representation of this object.
     * @return
     */
    @Override
    public String toString(){
        String resultat = new String("");
        Iterator it = numberOfRequestSent.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String)entry.getKey();
            Integer val = (Integer)entry.getValue();
            resultat += "Id Agent = " + key + "\n";
            resultat+= "\t Nombre de requêtes envoyées: " + val + "\n";
            if(numberOfRequestLost.containsKey(key)){
                resultat+= "\t Nombre de requêtes perdues : " + numberOfRequestLost.get(key) + "\n";
            } else {
                resultat+= "\t Nombre de requêtes perdues : Undefined \n";
            }
           if(averageResponseTime.containsKey(key)){
                resultat+= "\t Temps de réponse moyen : " + averageResponseTime.get(key) + "\n";
            } else {
                resultat+= "\t Temps de réponse moyen : Undefined \n";
            }
       }

       return resultat;
   }

}

package datas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
                resultat+= "\t Temps de réponse moyen : " + numberOfRequestLost.get(key) + "\n";
            } else {
                resultat+= "\t Temps de réponse moyen : Undefined \n";
            }
       }

       return resultat;
   }

}

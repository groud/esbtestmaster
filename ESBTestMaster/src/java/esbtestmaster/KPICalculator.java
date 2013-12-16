/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import datas.EventType;
import datas.KPISet;
import datas.ResultEvent;
import datas.ResultSet;
import datas.ResultSimulationEvent;
import datas.ResultSimulationSet;
import interfaces.KPICalculatorInterface;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;

/**
 *
 * @author gilles
 */
public class KPICalculator implements KPICalculatorInterface {
    /**
     * Calculates a KPISet from a result file.
     * @param filename
     * @return
     */
    public KPISet calculateKPI(ResultSet resultSet) {

        //A modifier le calcul du tps de rep
        
        KPISet kpi = new KPISet();
        HashMap<Integer,Long> responseTime = new HashMap<Integer,Long>();
        HashMap<String,Integer> numberOfRequestSent = new HashMap<String,Integer>();
        HashMap<String,Integer> numberOfRequestLost = new HashMap<String,Integer>();

        SortedSet<ResultEvent> events = resultSet.getEvents();

        //On parcourt les événements
        for(Iterator it = events.iterator(); it.hasNext();) {
            //Pour chaque évènement de type REQUEST_SENT on regarde si on a recu une réponse
             ResultSimulationEvent event = (ResultSimulationEvent) it.next();
             if(event.getEventType()==EventType.REQUEST_SENT){
                numberOfRequestSent.put(event.getAgentId(), numberOfRequestSent.get(event.getAgentId())+1);
                for(Iterator it2 = events.iterator(); it2.hasNext();) {
                    ResultSimulationEvent event2 = (ResultSimulationEvent)it2.next();
                    if(event2.getRequestId()==event.getRequestId() && event2.getEventType()==EventType.RESPONSE_RECEIVED){
                        //Si il y a une réponse on calcule le temps de réponse
                        responseTime.put(event.getRequestId(), Math.abs(event2.getEventDate()-event.getEventDate()));
                    }else{
                        //Si pas de réponse on considére la requête comme perdue
                        numberOfRequestLost.put(event.getAgentId(), numberOfRequestLost.get(event.getAgentId())+1);
                    }
                }
             } else if (event.getEventType()==EventType.CONNEXION_ERR){
                 //Si un événement est de type CONNEXION_ERR on considère la requête comme perdue
                  numberOfRequestLost.put(event.getAgentId(), numberOfRequestLost.get(event.getAgentId())+1);
             }
        }


        //Modifier le calcul du temps
        kpi.setAverageResponseTime(responseTime);
        kpi.setNumberOfRequestLost(numberOfRequestLost);
        kpi.setNumberOfRequestSent(numberOfRequestSent);

        return null;
    }

    /**
     * Saves a KPISet as an XML file
     * @param filename
     */
    public void saveKPItoXMLFile(String filename) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }


}

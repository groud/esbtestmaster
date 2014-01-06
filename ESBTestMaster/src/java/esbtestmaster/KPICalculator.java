/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esbtestmaster;

import datas.*;
import interfaces.KPICalculatorInterface;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;

/**
 *
 * @author gilles
 */
public class KPICalculator implements KPICalculatorInterface {
    // -------------------------------
    //   INTERFACES IMPLEMENTATIONS
    // -------------------------------

    /**
     * Calculates a KPISet from a result file.
     * @param filename
     * @return
     */
    public KPISet calculateKPI(ResultSet resultSet) {
        KPISet kpi = new KPISet();
        boolean found = false;
        HashMap<String, Long> averageResponseTime = new HashMap<String, Long>();
        HashMap<String, Integer> nbResponseTime = new HashMap<String, Integer>();
        HashMap<String, Integer> numberOfRequestSent = new HashMap<String, Integer>();
        HashMap<String, Integer> numberOfRequestLost = new HashMap<String, Integer>();

        SortedSet<ResultEvent> events = resultSet.getEvents();

        //On parcourt les événements
        for (Iterator it = events.iterator(); it.hasNext();) {
            //Pour chaque évènement de type REQUEST_SENT on regarde si on a recu une réponse
            ResultEvent resultEvent = (ResultEvent) it.next();
            if (resultEvent instanceof ResultSimulationEvent) {
                ResultSimulationEvent event = (ResultSimulationEvent) resultEvent;
                if (event.getEventType() == EventType.REQUEST_SENT) {
                    numberOfRequestSent.put(event.getAgentId(), numberOfRequestSent.get(event.getAgentId()) == null ? 1 : numberOfRequestSent.get(event.getAgentId()) + 1);
                    for (Iterator it2 = events.iterator(); it2.hasNext();) {
                        ResultEvent resultEvent2 = (ResultEvent) it2.next();
                        if (resultEvent2 instanceof ResultSimulationEvent) {
                            ResultSimulationEvent event2 = (ResultSimulationEvent) resultEvent2;
                            if (event2.getRequestId() == event.getRequestId() && event2.getEventType() == EventType.RESPONSE_RECEIVED) {
                                //Si il y a une réponse on calcule le temps de réponse
                                averageResponseTime.put(event.getAgentId(), averageResponseTime.get(event.getAgentId()) == null ? Math.abs(event2.getEventDate() - event.getEventDate()) : averageResponseTime.get(event.getAgentId()) + Math.abs(event2.getEventDate() - event.getEventDate()));
                                nbResponseTime.put(event.getAgentId(), nbResponseTime.get(event.getAgentId()) == null ? 1 : nbResponseTime.get(event.getAgentId()) + 1);
                                found = true;
                            }
                        }
                    }
                    if (!found) {
                        //Si pas de réponse on considére la requête comme perdue
                        numberOfRequestLost.put(event.getAgentId(), numberOfRequestLost.get(event.getAgentId()) == null ? 1 : numberOfRequestLost.get(event.getAgentId()) + 1);
                    }
                    found = false;
                }
            }
        }

        //Calcul du temps de réponse moyen
        Iterator it3 = nbResponseTime.entrySet().iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            String key = (String) entry.getKey();
            Integer val = (Integer) entry.getValue();
            averageResponseTime.put(key, averageResponseTime.get(key) / val);
        }

        kpi.setAverageResponseTime(averageResponseTime);
        kpi.setNumberOfRequestLost(numberOfRequestLost);
        kpi.setNumberOfRequestSent(numberOfRequestSent);

        return kpi;
    }

    /**
     * Saves a KPISet as an XML file
     * @param filename
     */
    public void saveKPItoXMLFile(KPISet kpiSet, String filename) {
        //TODO : Implement KPI saving as an XML File, ADD a KPISet as an Argument
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}

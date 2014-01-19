/*
 * TODO : Create a unit Test
 */

package utils;

import Exceptions.BadXMLException;
import datas.KPISet;
import datas.ResultSet;
import esbtestmaster.KPICalculator;
import esbtestmaster.XMLResultKeeper;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matthieu
 */
public class KPICalcTester {

    public static void main(String[] args) {
        try {
            //Remplacer par le chemin du fichier resultats
          // XMLResultKeeper xml = new XMLResultKeeper("/home/matthieu/results.xml");
            Scanner in = new Scanner(System.in);
            System.out.println("Saisir le chemin du fichier résultat :   ");
            String path = in.nextLine();
            
            ResultSet results = XMLResultKeeper.getLog(path);
            KPICalculator calc = new KPICalculator();
            KPISet kpi = calc.calculateKPI(results);

            Iterator it = kpi.getAverageResponseTime().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String)entry.getKey();
                Long val = (Long)entry.getValue();
                System.out.println("agentId, averageResponseTime: " + key + "," + val);
            }

           Iterator it2 = kpi.getNumberOfRequestLost().entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry entry = (Map.Entry) it2.next();
                String key = (String)entry.getKey();
                Integer val = (Integer)entry.getValue();
                System.out.println("agentId, reqLost: " + key + "," + val);
            }

            Iterator it3 = kpi.getNumberOfRequestSent().entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry entry = (Map.Entry) it3.next();
                String key = (String)entry.getKey();
                Integer val = (Integer)entry.getValue();
                System.out.println("agentId, reqSent: " + key + "," + val);
            }

            
        } catch (IOException ex) {
            Logger.getLogger(KPICalcTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadXMLException ex) {
            Logger.getLogger(KPICalcTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import datas.SimulationScenario;
import interfaces.ScenarioReaderInterface;

import java.io.*;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.input.sax.XMLReaders;

/**
 *
 * @author gilles
 */
public class ScenarioReader implements ScenarioReaderInterface{

    public SimulationScenario readXMLFile(String filename) {
	SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
	File xmlFile = new File(filename);

        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement(); //Get the scenario object
           
            //Configuration
            Element configuration = rootNode.getChild("configuration");//Get the configuration object
            List agentslist = configuration.getChildren();//Get the configuration object
            
            for (int i = 0; i < agentslist.size(); i++) {
               Element agent = (Element) agentslist.get(i);
               if (agent.getName().equals("provider")) {
                   System.out.println("Provider : " + agent.getChildText("name")+" at "+agent.getChildText("address"));
               } else if (agent.getName().equals("consumer")) {
                   System.out.println("Consumer : " + agent.getChildText("name")+" at "+agent.getChildText("address"));
               }
            }

            //Run
            Element run = rootNode.getChild("run");//Get the configuration object
            List burstslist = run.getChildren("burst");//Get the configuration object

            for (int i = 0; i < burstslist.size(); i++) {
               Element burst = (Element) burstslist.get(i);
               System.out.println(  " src : "+burst.getAttributeValue("src") +
                                    " dest : "+burst.getAttributeValue("dest") +
                                    " startdate : "+burst.getAttributeValue("startdate") +
                                    " stopdate : "+burst.getAttributeValue("stopdate") +
                                    " rate : "+burst.getChildText("rate") +
                                    " payloadsize : "+burst.getChildText("payloadsize"));
            }
	} catch (IOException io) {
            System.out.println(io.getMessage());
	} catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
	}

        return null;
    }
}

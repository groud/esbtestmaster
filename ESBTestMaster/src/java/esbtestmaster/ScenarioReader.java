/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import Exceptions.BadXMLException;
import datas.*;
import interfaces.ScenarioReaderInterface;

import java.io.*;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.input.sax.XMLReaders;
import utils.Debug;

/**
 *
 * @author gilles
 */
public class ScenarioReader implements ScenarioReaderInterface  {

    /**
     * Returns a SimulationScenario read from an XML file.
     * @param filename
     * @return
     * @throws IOException
     * @throws BadXMLException
     */
    public SimulationScenario readXMLFile(String filename) throws IOException, BadXMLException {
	SAXBuilder builder = new SAXBuilder();//XMLReaders.XSDVALIDATING);
	File xmlFile = new File(filename);

        SimulationScenario simulationScenario = new SimulationScenario();

        Document document;
        try {
            document = (Document) builder.build(xmlFile);

            Element rootNode = document.getRootElement(); //Get the scenario object

            //Configuration
            Element configuration = rootNode.getChild("configuration");//Get the configuration object
            List agentslist = configuration.getChildren();//Get the configuration object

            for (int i = 0; i < agentslist.size(); i++) {
               Element agent = (Element) agentslist.get(i);
               if (agent.getName().equals("provider")) {
                   ProducerConfiguration prodConf = new ProducerConfiguration();
                   prodConf.setWsAddress(agent.getChildText("address"));
                   prodConf.setMonitoringWSAddress(agent.getChildText("monitoringAddress"));
                   prodConf.setName(agent.getChildText("name"));
                   prodConf.setResponseTime(Integer.parseInt(agent.getChildText("responsetime")));
                   simulationScenario.getAgentsconfiguration().add(prodConf);
               } else if (agent.getName().equals("consumer")) {
                   ConsumerConfiguration consConf = new ConsumerConfiguration();
                   consConf.setWsAddress(agent.getChildText("address"));
                   consConf.setMonitoringWSAddress(agent.getChildText("monitoringAddress"));
                   consConf.setName(agent.getChildText("name"));
                   simulationScenario.getAgentsconfiguration().add(consConf);
              }
            }

            //Run
            Element run = rootNode.getChild("run");//Get the configuration object
            List burstslist = run.getChildren("burst");//Get the configuration object

            for (int i = 0; i < burstslist.size(); i++) {
               Element burst = (Element) burstslist.get(i);
               SimulationStep simulationStep = new SimulationStep();

               simulationStep.setConsumerID(burst.getAttributeValue("src"));
               simulationStep.setProviderID(burst.getAttributeValue("dest"));
               simulationStep.setBurstStartDate(Long.parseLong(burst.getAttributeValue("startdate")));
               simulationStep.setBurstStopDate(Long.parseLong(burst.getAttributeValue("stopdate")));
               simulationStep.setBurstRate(Float.parseFloat(burst.getChildText("rate")));
               simulationStep.setDataPayloadSize(Integer.parseInt(burst.getChildText("payloadsize")));

               simulationScenario.getSteps().add(simulationStep);
            }
             Debug.info(simulationScenario);
        } catch (JDOMException ex) {
            throw new BadXMLException("Bad XML file : \n"+ex.getMessage());
        }
        return simulationScenario;
    }
}

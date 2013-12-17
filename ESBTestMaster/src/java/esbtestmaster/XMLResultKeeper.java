/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esbtestmaster;

import Exceptions.BadXMLException;
import datas.*;
import interfaces.ResultKeeperInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import utils.Debug;

/**
 *
 * @author gilles
 */
public class XMLResultKeeper implements ResultKeeperInterface {

    private String XMLfilename;

    public XMLResultKeeper(String filename) throws IOException {
        this.XMLfilename = filename;
        this.init();
    }

    /**
     * Erase the XML results file and create a new one
     * @throws IOException
     */
    private void init() throws IOException {
        this.clearLog();
        File xmlFile = new File(XMLfilename);
        xmlFile.createNewFile();
        //TODO :Add the firsts tags to the XML
        Debug.info("ResultKeeper : " + XMLfilename + " initialization done.");
    }

    // -------------------------------
    //   INTERFACES IMPLEMENTATIONS
    // -------------------------------
    /**
     * Add a results set to the XML File
     * @param resultSet
     * @throws IOException
     *og @throws BadXMLException
     */
    public void addLog(ResultSet resultSet) throws IOException, BadXMLException {
        try {
            Document document;
            Element root;

            //Opens the XML File
            File xmlFile = new File(XMLfilename);
            if (xmlFile.exists()) {
                FileInputStream fis = new FileInputStream(xmlFile);
                SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
                // parse the xml content provided by the file input stream and create a Document object
                document = (Document) builder.build(xmlFile);

                // get the root element of the document
                root = document.getRootElement();
                fis.close();
            } else {
                // if it does not exist create a new document and new root
                document = new Document();
                root = new Element("results");
            }

            //Add the resultset to the document
            for (Iterator<ResultEvent> it = resultSet.getEvents().iterator(); it.hasNext();) {
                ResultEvent event = it.next();
                Element child;
                if (event instanceof ResultSimulationEvent) {
                    child = new Element("simulationEvent");
                    child.addContent(new Element("requestId").setText(String.valueOf(((ResultSimulationEvent) event).getRequestId())));
                } else {
                    child = new Element("event");
                }
                child.setAttribute("agentType", event.getAgentType().toString());
                child.setAttribute("agentId", event.getAgentId());
                child.setAttribute("eventDate", String.valueOf(event.getEventDate()));
                child.setAttribute("eventType", String.valueOf(event.getAgentType()));
                root.addContent(child);
            }

            document.setContent(root);

            //Writes the XMLFile
            FileWriter writer = new FileWriter(XMLfilename);
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            outputter.output(document, writer);
            outputter.output(document, System.out);
            writer.close();
        } catch (JDOMException ex) {
            throw new BadXMLException();
        }
    }

    /**
     * Return a result set corresponding to the used XML file
     * @return
     * @throws IOException
     * @throws BadXMLException
     */
    public ResultSet getLog() throws IOException, BadXMLException {
        return getLog(XMLfilename);
    }

    /**
     * Clear the log erasing the file
     */
    public void clearLog() {
        File file = new File(XMLfilename);
        if (file.delete()) {
            Debug.info("ResultKeeper : " + XMLfilename + " deleted (log cleared).");
        } else {
            Debug.info("ResultKeeper : The XML result file does not exists.");
        }
    }

    /**
     * Get a ResultSet from an XMLFile
     * @param filename
     * @return ResultSet
     * @throws IOException
     * @throws BadXMLException
     */
    public static ResultSet getLog(String filename) throws IOException, BadXMLException {
        SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
        File xmlFile = new File(filename);

        ResultSet resultSet = new ResultSet();

        Document document;
        try {
            document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement(); //Get the results object
            //Configuration
            List<Element> eventList = rootNode.getChildren();//Get the result events

            for (int i = 0; i < eventList.size(); i++) {
                Element eventElement = (Element) eventList.get(i);
                ResultEvent resultEvent;
                if (eventElement.getName().equals("simulationEvent")) {
                    resultEvent = new ResultSimulationEvent();
                    ((ResultSimulationEvent) resultEvent).setRequestId(Integer.parseInt(eventElement.getChildText("requestId")));
                } else {
                    resultEvent = new ResultEvent();
                }
                resultEvent.setAgentType(AgentType.valueOf(eventElement.getAttributeValue("agentType")));
                resultEvent.setAgentId(eventElement.getAttributeValue("agentId"));
                resultEvent.setEventDate(Long.parseLong(eventElement.getAttributeValue("eventDate")));
                resultEvent.setEventType(EventType.valueOf(eventElement.getAttributeValue("eventType")));

                resultSet.getEvents().add(resultEvent);
            }
            Debug.info("ResultKeeper : Events list :\n" + resultSet);
        } catch (JDOMException ex) {
            throw new BadXMLException("ResultKeeper : Bad XML file : \n" + ex.getMessage());
        }
        return resultSet;
    }
}

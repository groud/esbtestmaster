/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import Exceptions.BadXMLException;
import datas.*;
import interfaces.ResultKeeperInterface;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.input.sax.XMLReaders;
import utils.Debug;

/**
 *
 * @author gilles
 */
public class XMLResultKeeper implements ResultKeeperInterface{
    private String filename;

    public XMLResultKeeper (String filename) {
        this.filename = filename;
        this.init();
    }

    private void init() {
        //TODO : Erase the file if it exists, create the file and put the firsts tags.
    }

    // -------------------------------
    //   INTERFACES IMPLEMENTATIONS
    // -------------------------------
    public void addLog(ResultSet resultSet) {
        //TODO : Add a log to the selected file
    }

    public ResultSet getLog() throws IOException, BadXMLException {
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
               ResultEvent resultEvent = new ResultEvent();
               resultEvent.setAgentType(AgentType.valueOf(eventElement.getAttributeValue("agenttype")));
               resultEvent.setAgentId(eventElement.getAttributeValue("agentid"));
               resultEvent.setEventDate(Long.parseLong(eventElement.getAttributeValue("eventdate")));
               resultEvent.setEventType(EventType.valueOf(eventElement.getAttributeValue("eventtype")));
               resultSet.getEvents().add(resultEvent);
            }
             Debug.info(resultSet);
        } catch (JDOMException ex) {
            throw new BadXMLException("Bad XML file : \n"+ex.getMessage());
        }
        return null;
    }

    public void clearLog() {
         //TODO : Empty the file
    }

}

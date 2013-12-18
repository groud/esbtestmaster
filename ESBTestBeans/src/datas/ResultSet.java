package datas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author gilles
 */
public class ResultSet {
    private SortedSet<ResultEvent> events;

    public ResultSet() {
        events = new TreeSet();
    }

    public SortedSet<ResultEvent> getEvents() {
        return events;
    }

    public void setEvents(SortedSet<ResultEvent> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        String str = "-- ResultSet -- \n";
        for(Iterator it = events.iterator(); it.hasNext();) {
            str = str + it.next() +"\n";
        }
        str = str + "--------------\n";
        return str;
    }

}

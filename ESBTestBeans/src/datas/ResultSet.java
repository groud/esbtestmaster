package datas;

import java.io.Serializable;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Describes a set of event
 */
public class ResultSet implements Serializable {
    private SortedSet<ResultEvent> events;

    /**
     * Returns a ResultSet entity.
     */
    public ResultSet() {
        events = new TreeSet();
    }

    /**
     * Returns the set of ResultEvent.
     * @return The result set
     */
    public SortedSet<ResultEvent> getEvents() {
        return events;
    }

    /**
     * Set the set of events
     * @param events The SortedSet of events.
     */
    public void setEvents(SortedSet<ResultEvent> events) {
        this.events = events;
    }

    /**
     * Adds en event to the set
     * @param event
     */
    public void addEvent(ResultEvent event) {
        events.add(event);
    }

    /**
     * Retruns a String representation of this object.
     * @return
     */
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

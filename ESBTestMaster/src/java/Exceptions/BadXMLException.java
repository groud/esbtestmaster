/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

/**
 *
 * @author gilles
 */
public class BadXMLException extends Exception {

    /**
     * Creates a new instance of <code>BadXMLException</code> without detail message.
     */
    public BadXMLException() {
    }


    /**
     * Constructs an instance of <code>BadXMLException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BadXMLException(String msg) {
        super(msg);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

/**
 *
 * @author root
 */
public interface masterInterface {
    //--->Les types sont à mettre à jour
    //il doit recevoir le fichier depuis le JMS 
    public void receiveFile(Object file);
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testJMS;

/**
 *
 * @author root
 */
public class Message {
       
    private String message;

    public  Message(String message){

       this.message=message;
    }


    public String message(){

       return this.getMessage();
    }

    public String getMessage(){

        return this.message;
    }

   public void afficherMessage(){

        System.out.println("message ReCU: " +this.getMessage());
    }
}

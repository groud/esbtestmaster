/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;

import java.util.Arrays;

/**
 *
 * @author samy
 */
public class Utils {

    public static String getDummyString(int size, char character) {
            String ret = null;

            // Fill the same answer String with reqPayloadSize characters
            if (size > 0) {
                char[] array = new char[size];
                Arrays.fill(array, character);
                ret = new String(array);
            }

            return ret;
    }
}

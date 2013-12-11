/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package esbtestmaster;

import interfaces.MonitoringMsgListener;

/**
 *
 * @author gilles
 */
public class MasterMessageHandler {
    MonitoringMsgListener mmListener;

    public void setListener(MonitoringMsgListener mmListener) {
        this.mmListener = mmListener;
    }
}

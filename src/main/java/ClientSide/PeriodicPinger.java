/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author sarthakmanna
 */
public class PeriodicPinger extends Thread {
    public static final double TRANS_SPEED = 0.1; // in metres per nanosecond
    public static final int ITERATIONS = 1000, DELAY = 500;
    
    PeriodicPinger(Client servercontact, JRadioButton rbconn, JTextField tfip, JButton btsubmit,
            JTextField tftranstime, JTextField tfapproxdist, JTextArea tanotif) {
        serverContact = servercontact;
        rbConn = rbconn;
        tfIP = tfip; tfTransTime = tftranstime; tfApproxDist = tfapproxdist;
        btSubmit = btsubmit;
        taNotif = tanotif;
    }
    
    Client serverContact;
    JRadioButton rbConn;
    JTextField tfIP, tfTransTime, tfApproxDist;
    JButton btSubmit;
    JTextArea taNotif;
    
    public void run() {
        rbConn.setSelected(true);
        rbConn.setText("Connected");
        tfIP.setEditable(false);
        btSubmit.setEnabled(false);
        
        try {
            while (serverContact.isConnected()) {
                double timeDiff = serverContact.getTimeDiffInNanos(ITERATIONS);
                tfTransTime.setText(timeDiff + "");
                tfApproxDist.setText(TRANS_SPEED * timeDiff + "");
                Thread.sleep(DELAY);
            }
        } catch (Exception e) {}
        
        rbConn.setSelected(false);
        rbConn.setText("Disconnected");
        tfIP.setEditable(true);
        btSubmit.setEnabled(true);
        taNotif.setText("Connection interrupted...");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author sarthakmanna
 */

public class PeriodicPinger extends Thread {
    public static final double TRANS_SPEED = 3e-7; // in metres per nanosecond
    public static final int ITERATIONS = 10, DELAY = 100;
    
    PeriodicPinger(Client servercontact, JRadioButton rbconn, JButton btstop, JTextField tfip, 
            JButton btsubmit, JTextField tftranstime, JTextField tfapproxdist, JTextArea tanotif) {
        serverContact = servercontact;
        rbConn = rbconn; btStop = btstop;
        tfIP = tfip; tfTransTime = tftranstime; tfApproxDist = tfapproxdist;
        btSubmit = btsubmit;
        taNotif = tanotif;
    }
    
    Client serverContact;
    JRadioButton rbConn;
    JTextField tfIP, tfTransTime, tfApproxDist;
    JButton btStop, btSubmit;
    JTextArea taNotif;
    
    public void run() {
        rbConn.setSelected(true);
        rbConn.setText("Connected");
        btStop.setEnabled(true);
        tfIP.setEditable(false);
        btSubmit.setEnabled(false);
        
        try {
            while (serverContact.isConnected()) {
                DecimalFormat format = new DecimalFormat("0.000000");
                double timeDiff = serverContact.getTimeDiffInNanos(ITERATIONS);
                double approxDist = TRANS_SPEED * timeDiff;
                
                tfTransTime.setText(format.format(timeDiff));
                tfApproxDist.setText(format.format(approxDist));
                if (approxDist < 10) {
                    taNotif.setText("Too close !!! Maintain social distancing...");
                } else if (approxDist < 100) {
                    taNotif.setText("Infected person within 100m detected !!!");
                } else {
                    taNotif.setText("");
                }
                
                Thread.sleep(DELAY);
            }
        } catch (Exception e) {}
        
        rbConn.setSelected(false);
        rbConn.setText("Disconnected");
        btStop.setEnabled(false);
        tfIP.setEditable(true);
        btSubmit.setEnabled(true);
        taNotif.setText("Connection interrupted...");
    }
}

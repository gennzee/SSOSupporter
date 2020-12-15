package com.xa;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by tanks on 11/28/2020.
 */
@SuppressWarnings("Since15")
public class SSOSupporter extends JFrame implements WindowListener, NativeKeyListener {
    public JPanel panel1;
    public JLabel content;
    public JLabel l_key1;
    public JLabel l_key2;
    public JLabel l_key3;
    public JLabel l_key4;
    public JLabel l_key5;
    public JLabel l_key6;
    public JLabel l_status;
    public JComboBox comboBox1;
    public JComboBox comboBox2;
    public JComboBox comboBox3;
    public JComboBox comboBox4;
    public JComboBox comboBox5;
    public JComboBox comboBox6;
    public JButton b_run;
    private JButton b_auto;
    private JTextField delay1;
    private JTextField delay2;
    private JTextField delay3;
    private JTextField delay4;
    private JTextField delay5;
    private JTextField delay6;
    private JLabel l_key7;
    private JLabel l_key8;
    private JLabel l_key9;
    private JLabel l_key10;
    private JComboBox comboBox7;
    private JComboBox comboBox8;
    private JComboBox comboBox9;
    private JComboBox comboBox10;
    private JTextField delay7;
    private JTextField delay8;
    private JTextField delay9;
    private JTextField delay10;
    private static boolean isRunning = false;
    private static final String running = "Running. Type A to start attack.";
    private static final String stopped = "Stopped.";
    private static final String auto = "Running. Click to SSO to start attack.";
    private static final int MAX_TITLE_LENGTH = 1024;
    private static boolean isClicked_A = false;
    private static boolean isAuto = false;
    private static List<Integer> listKeys = Arrays.asList(0, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4A, 0x4B, 0x4C, 0x4D, 0x4E, 0x4F, 0x50, 0x51, 0x52,
            0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5A, 0x20, 0x11, 0x21, 0x22, 0x23, 0x24, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39);
    private SwingWorker<Void, Void> doAttacking;

    private SSOSupporter() throws Exception {
        this.setTitle("Genn's Team");
        this.setIconImage(new ImageIcon("image/icon/SSOSupporter.jpg").getImage());
        panel1.setPreferredSize(new Dimension(380, 450));
        this.setContentPane(panel1);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.addWindowListener(this);
        this.setVisible(true);
        l_status.setText(stopped);
        b_run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    setEnableZone();
                    isRunning = false;
                    l_status.setText(stopped);
                    b_run.setText("Run");
                    b_auto.setVisible(true);

                    if (!doAttacking.isCancelled()) doAttacking.cancel(true);
                } else {
                    setDisableZone();
                    isRunning = true;
                    l_status.setText(running);
                    b_run.setText("Stop");
                    b_auto.setVisible(false);

                    createSwingWorker();
                    doAttacking.execute();
                }
            }
        });
        b_auto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    setEnableZone();
                    isRunning = false;
                    isAuto = false;
                    l_status.setText(stopped);
                    b_auto.setText("Auto");
                    b_run.setVisible(true);

                    if (!doAttacking.isCancelled()) doAttacking.cancel(true);
                } else {
                    setDisableZone();
                    isRunning = true;
                    isAuto = true;
                    l_status.setText(auto);
                    b_auto.setText("Stop");
                    b_run.setVisible(false);

                    createSwingWorker();
                    doAttacking.execute();
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new SSOSupporter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDisableZone() {
        delay1.setEditable(false);
        delay2.setEditable(false);
        delay3.setEditable(false);
        delay4.setEditable(false);
        delay5.setEditable(false);
        delay6.setEditable(false);
        delay7.setEditable(false);
        delay8.setEditable(false);
        delay9.setEditable(false);
        delay10.setEditable(false);
        comboBox1.setEditable(false);
        comboBox2.setEditable(false);
        comboBox3.setEditable(false);
        comboBox4.setEditable(false);
        comboBox5.setEditable(false);
        comboBox6.setEditable(false);
        comboBox7.setEditable(false);
        comboBox8.setEditable(false);
        comboBox9.setEditable(false);
        comboBox10.setEditable(false);
        comboBox1.setEnabled(false);
        comboBox2.setEnabled(false);
        comboBox3.setEnabled(false);
        comboBox4.setEnabled(false);
        comboBox5.setEnabled(false);
        comboBox6.setEnabled(false);
        comboBox7.setEnabled(false);
        comboBox8.setEnabled(false);
        comboBox9.setEnabled(false);
        comboBox10.setEnabled(false);
    }

    private void setEnableZone() {
        delay1.setEditable(true);
        delay2.setEditable(true);
        delay3.setEditable(true);
        delay4.setEditable(true);
        delay5.setEditable(true);
        delay6.setEditable(true);
        delay7.setEditable(true);
        delay8.setEditable(true);
        delay9.setEditable(true);
        delay10.setEditable(true);
        comboBox1.setEditable(true);
        comboBox2.setEditable(true);
        comboBox3.setEditable(true);
        comboBox4.setEditable(true);
        comboBox5.setEditable(true);
        comboBox6.setEditable(true);
        comboBox7.setEditable(true);
        comboBox8.setEditable(true);
        comboBox9.setEditable(true);
        comboBox10.setEditable(true);
        comboBox1.setEnabled(true);
        comboBox2.setEnabled(true);
        comboBox3.setEnabled(true);
        comboBox4.setEnabled(true);
        comboBox5.setEnabled(true);
        comboBox6.setEnabled(true);
        comboBox7.setEnabled(true);
        comboBox8.setEnabled(true);
        comboBox9.setEnabled(true);
        comboBox10.setEnabled(true);
    }

    private void createSwingWorker() {
        int delay11 = Integer.parseInt(delay1.getText());
        int delay22 = Integer.parseInt(delay2.getText());
        int delay33 = Integer.parseInt(delay3.getText());
        int delay44 = Integer.parseInt(delay4.getText());
        int delay55 = Integer.parseInt(delay5.getText());
        int delay66 = Integer.parseInt(delay6.getText());
        int delay77 = Integer.parseInt(delay7.getText());
        int delay88 = Integer.parseInt(delay8.getText());
        int delay99 = Integer.parseInt(delay9.getText());
        int delay1010 = Integer.parseInt(delay10.getText());
        doAttacking = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (isRunning) {
                    char[] buffer = new char[MAX_TITLE_LENGTH * 2];
                    WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
                    User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
                    if (Native.toString(buffer).equals("SoulSaverOnline") && (isClicked_A || isAuto)) {
                        Robot robot = new Robot();
                        // Simulate a key press
                        //key1
                        doPressKey(robot, comboBox1.getSelectedIndex(), listKeys.get(comboBox1.getSelectedIndex()), delay11);
                        //key2
                        doPressKey(robot, comboBox2.getSelectedIndex(), listKeys.get(comboBox2.getSelectedIndex()), delay22);
                        //key3
                        doPressKey(robot, comboBox3.getSelectedIndex(), listKeys.get(comboBox3.getSelectedIndex()), delay33);
                        //key4
                        doPressKey(robot, comboBox4.getSelectedIndex(), listKeys.get(comboBox4.getSelectedIndex()), delay44);
                        //key5
                        doPressKey(robot, comboBox5.getSelectedIndex(), listKeys.get(comboBox5.getSelectedIndex()), delay55);
                        //key6
                        doPressKey(robot, comboBox6.getSelectedIndex(), listKeys.get(comboBox6.getSelectedIndex()), delay66);
                        //key7
                        doPressKey(robot, comboBox7.getSelectedIndex(), listKeys.get(comboBox7.getSelectedIndex()), delay77);
                        //key8
                        doPressKey(robot, comboBox8.getSelectedIndex(), listKeys.get(comboBox8.getSelectedIndex()), delay88);
                        //key9
                        doPressKey(robot, comboBox9.getSelectedIndex(), listKeys.get(comboBox9.getSelectedIndex()), delay99);
                        //key10
                        doPressKey(robot, comboBox10.getSelectedIndex(), listKeys.get(comboBox10.getSelectedIndex()), delay1010);
                    }
                }
                return null;
            }

            @Override
            protected void done() {
            }
        };
    }

    private void doPressKey(Robot robot, int selectedValue, int key, int delay) throws Exception {
        if (selectedValue != 0) {
            robot.keyPress(key);
            Thread.sleep(26);
            robot.keyRelease(key);
            Thread.sleep(delay);
        }
    }

    public void windowOpened(WindowEvent e) {
        //disable jnativehook log
        // Clear previous logging configurations.
        LogManager.getLogManager().reset();
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        //connect to anhnx.tk website to check if it's available to use.
        try {
            doTrustToCertificates();
            URL url = new URL("https://www.anhnx.tk/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                System.exit(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
        //start service
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e1) {
            e1.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    public void windowClosing(WindowEvent e) {

    }

    public void windowClosed(WindowEvent e) {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            e1.printStackTrace();
        }
        System.runFinalization();
        System.exit(0);
    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {

    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("A")){
            isClicked_A = true;
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("A")){
            isClicked_A = false;
        }
        //toggle combo key
        if(NativeKeyEvent.getKeyText(e.getKeyCode()).equals("F11")){
            if (isRunning) {
                setEnableZone();
                if (!doAttacking.isCancelled()) doAttacking.cancel(true);
                isRunning = false;
                l_status.setText(stopped);
                b_run.setText("Run");
                b_auto.setVisible(true);
            } else {
                setDisableZone();
                createSwingWorker();
                doAttacking.execute();
                isRunning = true;
                l_status.setText(running);
                b_run.setText("Stop");
                b_auto.setVisible(false);
            }
        }
        //toggle auto
        if(NativeKeyEvent.getKeyText(e.getKeyCode()).equals("F12")){
            if (isRunning) {
                setEnableZone();
                isRunning = false;
                isAuto = false;
                l_status.setText(stopped);
                b_auto.setText("Auto");
                b_run.setVisible(true);

                if (!doAttacking.isCancelled()) doAttacking.cancel(true);
            } else {
                setDisableZone();
                isRunning = true;
                isAuto = true;
                l_status.setText(auto);
                b_auto.setText("Stop");
                b_run.setVisible(false);

                createSwingWorker();
                doAttacking.execute();
            }
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public void doTrustToCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    }
}

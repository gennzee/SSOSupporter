package com.xa;

import autoitx4java.AutoItX;
import com.jacob.com.LibraryLoader;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by tanks on 12/19/2020.
 */
public class SSOSupporter2 extends JFrame implements WindowListener, NativeKeyListener {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JLabel content;
    private JComboBox key1;
    private JComboBox key2;
    private JComboBox key3;
    private JComboBox key4;
    private JComboBox key5;
    private JComboBox key6;
    private JComboBox key7;
    private JComboBox key8;
    private JComboBox key9;
    private JComboBox key10;
    private JTextField delay1;
    private JTextField delay2;
    private JTextField delay3;
    private JTextField delay4;
    private JTextField delay5;
    private JTextField delay6;
    private JTextField delay7;
    private JTextField delay8;
    private JTextField delay9;
    private JTextField delay10;
    private JButton b_run;
    private JButton b_auto;
    private JComboBox keyHp;
    private JTextField delayHp;
    private JComboBox keyMp;
    private JTextField delayMp;
    private JTextField delayRunRight;
    private JTextField delayRunLeft;
    private JComboBox keyBuff1;
    private JComboBox keyBuff2;
    private JComboBox keyBuff3;
    private JComboBox keyBuff4;
    private JComboBox keyBuff5;
    private JTextField delayBuff1;
    private JTextField delayBuff2;
    private JTextField delayBuff3;
    private JTextField delayBuff4;
    private JTextField delayBuff5;
    private JLabel l_status;
    private JCheckBox autoBuff;
    private JLabel l_buff1;
    private JLabel l_buff2;
    private JLabel l_buff3;
    private JLabel l_buff4;
    private JLabel l_buff5;
    private JLabel l_buff;
    private JTextField delayBuff;
    private JCheckBox autoRun;
    private JLabel l_runLeft;
    private JLabel l_runRight;

    private SwingWorker<Void, Void> doAttacking;
    private SwingWorker<Void, Void> useHpMp;
    private SwingWorker<Void, Void> doRunning;
    private long buffTime;
    private long hpTime;
    private long mpTime;

    private SSOSupporter2() {
        this.setTitle("Genn's Team");
        this.setIconImage(new ImageIcon("image/icon/SSOSupporter.jpg").getImage());
        this.setContentPane(panel1);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.addWindowListener(this);
        this.setVisible(true);

        b_run.addActionListener(e -> handleNormalAttack());
        b_auto.addActionListener(e -> handleAutoAttack());
        autoBuff.addActionListener(e -> handleEnableDisableUseBuff());
        autoRun.addActionListener(e -> handleEnableDisableUseRun());
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); //Windows Look and feel
        SwingUtilities.invokeLater(SSOSupporter2::new);
    }

    private void handleNormalAttack() {
        if (Service.isRunning) {
            Service.isRunning = false;
            l_status.setText(Constants.stopped);
            b_run.setText(Constants.run);
            b_auto.setVisible(true);

            if (!doAttacking.isCancelled()) doAttacking.cancel(true);
            if (!useHpMp.isCancelled()) useHpMp.cancel(true);
            if (!doRunning.isCancelled()) doRunning.cancel(true);
        } else {
            if (autoBuff.isSelected() || autoRun.isSelected()) {
                JOptionPane.showMessageDialog(null, "Auto buff & auto run only support for Auto.");
                return;
            }
            Service.isRunning = true;
            l_status.setText(Constants.running);
            b_run.setText(Constants.stop);
            b_auto.setVisible(false);
            hpTime = System.currentTimeMillis();
            mpTime = System.currentTimeMillis();

            createAttackSwingWorker();
            doAttacking.execute();
            useHpMp.execute();
        }
    }

    private void handleAutoAttack() {
        if (Service.isRunning) {
            Service.isRunning = false;
            Service.isAuto = false;
            l_status.setText(Constants.stopped);
            b_auto.setText(Constants.auto);
            b_run.setVisible(true);

            if (!doAttacking.isCancelled()) doAttacking.cancel(true);
            if (!useHpMp.isCancelled()) useHpMp.cancel(true);
            if (!doRunning.isCancelled()) doRunning.cancel(true);
        } else {
            Service.isRunning = true;
            Service.isAuto = true;
            l_status.setText(Constants.autoing);
            b_auto.setText(Constants.stop);
            b_run.setVisible(false);
            buffTime = System.currentTimeMillis();
            hpTime = System.currentTimeMillis();
            mpTime = System.currentTimeMillis();

            createAttackSwingWorker();
            doAttacking.execute();
            useHpMp.execute();
            doRunning.execute();
        }
    }

    private void handleEnableDisableUseBuff() {
        if (autoBuff.isSelected()) {
            l_buff.setEnabled(true);
            l_buff1.setEnabled(true);
            l_buff2.setEnabled(true);
            l_buff3.setEnabled(true);
            l_buff4.setEnabled(true);
            l_buff5.setEnabled(true);
            keyBuff1.setEnabled(true);
            keyBuff2.setEnabled(true);
            keyBuff3.setEnabled(true);
            keyBuff4.setEnabled(true);
            keyBuff5.setEnabled(true);
            delayBuff.setEnabled(true);
            delayBuff1.setEnabled(true);
            delayBuff2.setEnabled(true);
            delayBuff3.setEnabled(true);
            delayBuff4.setEnabled(true);
            delayBuff5.setEnabled(true);
        } else {
            l_buff.setEnabled(false);
            l_buff1.setEnabled(false);
            l_buff2.setEnabled(false);
            l_buff3.setEnabled(false);
            l_buff4.setEnabled(false);
            l_buff5.setEnabled(false);
            keyBuff1.setEnabled(false);
            keyBuff2.setEnabled(false);
            keyBuff3.setEnabled(false);
            keyBuff4.setEnabled(false);
            keyBuff5.setEnabled(false);
            delayBuff.setEnabled(false);
            delayBuff1.setEnabled(false);
            delayBuff2.setEnabled(false);
            delayBuff3.setEnabled(false);
            delayBuff4.setEnabled(false);
            delayBuff5.setEnabled(false);
        }
    }

    private void handleEnableDisableUseRun() {
        if (autoRun.isSelected()) {
            l_runLeft.setEnabled(true);
            l_runRight.setEnabled(true);
            delayRunLeft.setEnabled(true);
            delayRunRight.setEnabled(true);
        } else {
            l_runLeft.setEnabled(false);
            l_runRight.setEnabled(false);
            delayRunLeft.setEnabled(false);
            delayRunRight.setEnabled(false);
        }
    }

    private void createAttackSwingWorker() {
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
        int delayBuff11 = Integer.parseInt(delayBuff1.getText());
        int delayBuff22 = Integer.parseInt(delayBuff2.getText());
        int delayBuff33 = Integer.parseInt(delayBuff3.getText());
        int delayBuff44 = Integer.parseInt(delayBuff4.getText());
        int delayBuff55 = Integer.parseInt(delayBuff5.getText());
        int delayHpp = Integer.parseInt(delayHp.getText());
        int delayMpp = Integer.parseInt(delayMp.getText());
        int delayRunLeftt = Integer.parseInt(delayRunLeft.getText());
        int delayRunRightt = Integer.parseInt(delayRunRight.getText());

        AutoItX x = new AutoItX();
        doAttacking = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (Service.isRunning) {
                    char[] buffer = new char[Constants.MAX_TITLE_LENGTH * 2];
                    WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
                    User32.INSTANCE.GetWindowText(hwnd, buffer, Constants.MAX_TITLE_LENGTH);
                    if (Native.toString(buffer).equals("SoulSaverOnline") && (Service.isClicked_A || Service.isAuto)) {
                        Robot robot = new Robot();
                        // Simulate a key press
                        //key1
                        doPressKey(robot, key1.getSelectedIndex(), Constants.listKeys.get(key1.getSelectedIndex()), delay11, 30);
                        //key2
                        doPressKey(robot, key2.getSelectedIndex(), Constants.listKeys.get(key2.getSelectedIndex()), delay22, 30);
                        //key3
                        doPressKey(robot, key3.getSelectedIndex(), Constants.listKeys.get(key3.getSelectedIndex()), delay33, 30);
                        //key4
                        doPressKey(robot, key4.getSelectedIndex(), Constants.listKeys.get(key4.getSelectedIndex()), delay44, 30);
                        //key5
                        doPressKey(robot, key5.getSelectedIndex(), Constants.listKeys.get(key5.getSelectedIndex()), delay55, 30);
                        //key6
                        doPressKey(robot, key6.getSelectedIndex(), Constants.listKeys.get(key6.getSelectedIndex()), delay66, 30);
                        //key7
                        doPressKey(robot, key7.getSelectedIndex(), Constants.listKeys.get(key7.getSelectedIndex()), delay77, 30);
                        //key8
                        doPressKey(robot, key8.getSelectedIndex(), Constants.listKeys.get(key8.getSelectedIndex()), delay88, 30);
                        //key9
                        doPressKey(robot, key9.getSelectedIndex(), Constants.listKeys.get(key9.getSelectedIndex()), delay99, 30);
                        //key10
                        doPressKey(robot, key10.getSelectedIndex(), Constants.listKeys.get(key10.getSelectedIndex()), delay1010, 30);
                        //buff
                        if (autoBuff.isSelected()) {
                            if (System.currentTimeMillis() >= buffTime + Integer.parseInt(delayBuff.getText()) * 1000) {
                                Thread.sleep(1500);
                                doPressKey(robot, keyBuff1.getSelectedIndex(), Constants.listKeys.get(keyBuff1.getSelectedIndex()), delayBuff11, 100);
                                doPressKey(robot, keyBuff2.getSelectedIndex(), Constants.listKeys.get(keyBuff2.getSelectedIndex()), delayBuff22, 100);
                                doPressKey(robot, keyBuff3.getSelectedIndex(), Constants.listKeys.get(keyBuff3.getSelectedIndex()), delayBuff33, 100);
                                doPressKey(robot, keyBuff4.getSelectedIndex(), Constants.listKeys.get(keyBuff4.getSelectedIndex()), delayBuff44, 100);
                                doPressKey(robot, keyBuff5.getSelectedIndex(), Constants.listKeys.get(keyBuff5.getSelectedIndex()), delayBuff55, 100);
                                buffTime = System.currentTimeMillis();
                            }
                        }
                    }
                }
                return null;
            }
        };
        useHpMp = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (Service.isRunning) {
                    char[] buffer = new char[Constants.MAX_TITLE_LENGTH * 2];
                    WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
                    User32.INSTANCE.GetWindowText(hwnd, buffer, Constants.MAX_TITLE_LENGTH);
                    if (Native.toString(buffer).equals("SoulSaverOnline") && (Service.isClicked_A || Service.isAuto)) {
                        Robot robot = new Robot();
                        // Simulate a key press
                        //use hp
                        if (keyHp.getSelectedIndex() != 0 && System.currentTimeMillis() >= (hpTime + delayHpp)) {
                            doPressKey(robot, keyHp.getSelectedIndex(), Constants.listKeys.get(keyHp.getSelectedIndex()), 30, 30);
                            doPressKey(robot, keyHp.getSelectedIndex(), Constants.listKeys.get(keyHp.getSelectedIndex()), 30, 30);
                            doPressKey(robot, keyHp.getSelectedIndex(), Constants.listKeys.get(keyHp.getSelectedIndex()), 30, 30);
                            doPressKey(robot, keyHp.getSelectedIndex(), Constants.listKeys.get(keyHp.getSelectedIndex()), 30, 30);
                            doPressKey(robot, keyHp.getSelectedIndex(), Constants.listKeys.get(keyHp.getSelectedIndex()), 30, 30);
                            hpTime = System.currentTimeMillis();
                        }
                        //use mp
                        if (keyMp.getSelectedIndex() != 0 && System.currentTimeMillis() >= (mpTime + delayMpp)) {
                            doPressKey(robot, keyMp.getSelectedIndex(), Constants.listKeys.get(keyMp.getSelectedIndex()), 30, 30);
                            doPressKey(robot, keyMp.getSelectedIndex(), Constants.listKeys.get(keyMp.getSelectedIndex()), 30, 30);
                            doPressKey(robot, keyMp.getSelectedIndex(), Constants.listKeys.get(keyMp.getSelectedIndex()), 30, 30);
                            doPressKey(robot, keyMp.getSelectedIndex(), Constants.listKeys.get(keyMp.getSelectedIndex()), 30, 30);
                            doPressKey(robot, keyMp.getSelectedIndex(), Constants.listKeys.get(keyMp.getSelectedIndex()), 30, 30);
                            mpTime = System.currentTimeMillis();
                        }
                    }
                }
                return null;
            }
        };
        doRunning = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (Service.isRunning) {
                    char[] buffer = new char[Constants.MAX_TITLE_LENGTH * 2];
                    WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
                    User32.INSTANCE.GetWindowText(hwnd, buffer, Constants.MAX_TITLE_LENGTH);
                    if (Native.toString(buffer).equals("SoulSaverOnline") && (Service.isClicked_A || Service.isAuto) && autoRun.isSelected()) {
                        //Robot robot = new Robot();
                        // Simulate a key press
                        //run left
                        if(delayRunLeftt != 0){
                            Thread.sleep(30);
                            x.send("{LEFT DOWN}", false);
                            Thread.sleep(delayRunLeftt);
                            x.send("{LEFT UP}", false);
                        }
                        //run right
                        if(delayRunRightt != 0){
                            Thread.sleep(30);
                            x.send("{RIGHT DOWN}", false);
                            Thread.sleep(delayRunRightt);
                            x.send("{RIGHT UP}", false);
                        }
                    }
                }
                return null;
            }
        };
    }

    private void doPressKey(Robot robot, int selectedIndex, int key, int delay, int delayBetweenPressAndRelease) throws Exception {
        if (selectedIndex != 0) {
            robot.keyPress(key);
            Thread.sleep(delayBetweenPressAndRelease);
            robot.keyRelease(key);
            Thread.sleep(delay);
        }
    }

    private void doHoldKey(Robot robot, int key, int delay) throws Exception {
        robot.keyPress(key);
        Thread.sleep(delay);
        robot.keyRelease(key);
        Thread.sleep(50);

    }

    @Override
    public void windowOpened(WindowEvent e) {
        //disable jnativehook log
        // Clear previous logging configurations.
        LogManager.getLogManager().reset();
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        //load jcob dll for handle autoit
        File file = new File("dll", "jacob-1.20-x64.dll"); //path to the jacob dll
        System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());

        //connect to anhnx.tk website to check if it's available to use.
        try {
            Service.doTrustToCertificates();
            URL url = new URL("https://www.anhnx.tk/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() != 200) {
                System.exit(0);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
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

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            e1.printStackTrace();
        }
        System.runFinalization();
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("A")) {
            Service.isClicked_A = true;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("A")) {
            Service.isClicked_A = false;
        }
        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("F11")) {
            handleNormalAttack();
        }
        if (NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()).equals("F12")) {
            handleAutoAttack();
        }
    }
}

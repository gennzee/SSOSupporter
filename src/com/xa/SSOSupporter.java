package com.xa;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.List;

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
    private static boolean isRunning = false;
    private static final String running = "Running. Type A to start attack.";
    private static final String stopped = "Stopped.";
    private static final String auto = "Running. Click to SSO to start attack.";
    private static final int MAX_TITLE_LENGTH = 1024;
    private static boolean isClicked_A = false;
    private static boolean isAuto = false;
    private static List<Integer> listKeys = Arrays.asList(0,0x41,0x42,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x4B,0x4C,0x4D,0x4E,0x4F,0x50,0x51,0x52,
            0x53,0x54,0x55,0x56,0x57,0x58,0x59,0x5A,0x20);
    private SwingWorker<Void, Void> doAttacking;

    private SSOSupporter() throws Exception {
        this.setTitle("SSO Supporter");
        this.setIconImage( new ImageIcon("image/icon/SSOSupporter.jpg").getImage());
        panel1.setPreferredSize(new Dimension(300, 400));
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.addWindowListener(this);
        this.setVisible(true);
        l_status.setText(stopped);
        b_run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isRunning){
                    if(!doAttacking.isCancelled()) doAttacking.cancel(true);
                    isRunning = false;
                    b_auto.setVisible(true);
                    setEnableComboBox();
                    l_status.setText(stopped);
                    b_run.setText("Run");
                }else{
                    createSwingWorker();
                    doAttacking.execute();
                    isRunning = true;
                    b_auto.setVisible(false);
                    setDisableComboBox();
                    l_status.setText(running);
                    b_run.setText("Stop");
                }
            }
        });
        b_auto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRunning){
                    b_run.setVisible(true);
                    setEnableComboBox();
                    isRunning = false;
                    isAuto = false;
                    l_status.setText(stopped);
                    b_auto.setText("Auto");

                    if(!doAttacking.isCancelled()) doAttacking.cancel(true);
                }else{
                    b_run.setVisible(false);
                    setDisableComboBox();
                    isRunning = true;
                    isAuto = true;
                    l_status.setText(auto);
                    b_auto.setText("Stop");

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

    private void setDisableComboBox(){
        comboBox1.disable();
        comboBox2.disable();
        comboBox3.disable();
        comboBox4.disable();
        comboBox5.disable();
        comboBox6.disable();
    }

    private void setEnableComboBox(){
        comboBox1.enable();
        comboBox2.enable();
        comboBox3.enable();
        comboBox4.enable();
        comboBox5.enable();
        comboBox6.enable();
    }

    private void createSwingWorker(){
        doAttacking = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while(isRunning){
                    char[] buffer = new char[MAX_TITLE_LENGTH * 2];
                    WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
                    User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
                    if (Native.toString(buffer).equals("SoulSaverOnline") && (isClicked_A || isAuto)) {
                        Robot robot = new Robot();
                        // Simulate a key press
                        doPressKey(robot, comboBox1.getSelectedIndex(), listKeys.get(comboBox1.getSelectedIndex()));
                        doPressKey(robot, comboBox2.getSelectedIndex(), listKeys.get(comboBox2.getSelectedIndex()));
                        doPressKey(robot, comboBox3.getSelectedIndex(), listKeys.get(comboBox3.getSelectedIndex()));
                        doPressKey(robot, comboBox4.getSelectedIndex(), listKeys.get(comboBox4.getSelectedIndex()));
                        doPressKey(robot, comboBox5.getSelectedIndex(), listKeys.get(comboBox5.getSelectedIndex()));
                        doPressKey(robot, comboBox6.getSelectedIndex(), listKeys.get(comboBox6.getSelectedIndex()));
                    }
                }
                return null;
            }

            @Override
            protected void done() {
            }
        };
    }

    private void doPressKey(Robot robot, int selectedValue, int key) throws Exception{
        if(selectedValue != 0){
            robot.keyPress(key);
            Thread.sleep(1);
            robot.keyRelease(key);
            Thread.sleep(1);
        }
    }

    public void windowOpened(WindowEvent e) {
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
        if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("A"))
            isClicked_A = true;
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("A"))
            isClicked_A = false;
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}

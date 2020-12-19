package com.xa;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tanks on 12/19/2020.
 */
class Constants {

    static final String running = "Running. Type A to start attack.";
    static final String stopped = "Stopped.";
    static final String autoing = "Running. Click to SSO to start attack.";
    static final String run = "Run";
    static final String auto = "Auto";
    static final String stop = "Stop";

    static final int MAX_TITLE_LENGTH = 1024;

    static final List<Integer> listKeys = Arrays.asList(
            0, KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K,
            KeyEvent.VK_L, KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q, KeyEvent.VK_R, KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V,
            KeyEvent.VK_W, KeyEvent.VK_X, KeyEvent.VK_Y, KeyEvent.VK_Z
    );

}

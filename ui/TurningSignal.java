/**
 * Copyright 2018 (C) Jiawen Deng, Ann J.S. and Kareem D. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng, Ann J.S. and Kareem D. (I-LU-V-EH)
 *
 * A user interface is like a joke. If you have to explain it, then it is
 * not that good.
 *
 *-----------------------------------------------------------------------------
 * TurningSignal.java
 *-----------------------------------------------------------------------------
 * The graphics for turning signal indicators.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import resources.Resources;
import sound.SoundService;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TurningSignal extends JPanel {

    public static TurningSignal left;       // static reference of left signal
    public static TurningSignal right;      // static reference of right signal

    static final byte LEFT_SIGNAL = 0;      // signal modifier; tells constructor the type of signal
    static final byte RIGHT_SIGNAL = 1;

    private BufferedImage[] states;         // signal states: on/off
    private BufferedImage current_state;    // current signal state

    private Timer timing_controller;        // controls the flashing animation
    private int signal_type;                // the type of signal (set to signal modifier)

    private boolean activated;  // whether the signal is activated
    private boolean emergency;  // whether the hazard mode is on

    /**
     * Constructor
     * @param signal_type   Signal type modifier
     */
    TurningSignal (byte signal_type) {

        setBounds(0,0,300,300);

        Console.printGeneralMessage("Initializing turning signal, type " + signal_type);

        // depending on signal type, retrieve the
        // corresponding asset
        switch (signal_type) {
            case LEFT_SIGNAL:
                states = Resources.signal_left;
                left = this;
                break;
            case RIGHT_SIGNAL:
                states = Resources.signal_right;
                right = this;
                break;
        }

        this.signal_type = signal_type;

        current_state = states[0];

        setSize((int)(0.5 * current_state.getWidth()), (int)(0.5 * current_state.getHeight()));

        // set up timing controller to alternate between states
        timing_controller = new Timer(295, e -> {
            current_state = (current_state == states[0]) ? states[1] : states[0];
            RenderingService.invokeRepaint();
        });

    }

    /**
     * Method that activates the turning signals
     * @param doActivate    boolean of whether the indicators should be activated
     */
    void activate (boolean doActivate) {
        if (emergency) {
            activated = true;
            return;
        }

        // if activated, *deactivate the other indicator and start
        // flashing animation; unless hazard is on, in which case
        // keep both indicators on.
        if (doActivate) {
            timing_controller.restart();
            activated = true;

            if (this.signal_type == RIGHT_SIGNAL) {
                left.activate(false);
            } else {
                right.activate(false);
            }

            SoundService.setSignalSound(true);
        } else {
            SoundService.setSignalSound(false);
            timing_controller.stop();
            activated = false;
            current_state = states[0];

            RenderingService.invokeRepaint();
        }
    }

    /**
     * An overloaded activate method that do not take a boolean;
     * Simply toggles the indicator
     */
    public void activate () {
        if (activated) {
            activate(false);
        } else {
            activate(true);
        }
    }

    /**
     * Method called by the hazard button
     * Sets indicators into hazard light mode
     * @param isEmergency
     */
    void emergency(boolean isEmergency) {
        if (isEmergency) {
            timing_controller.restart();
            SoundService.setSignalSound(true);
            emergency = true;
        } else {
            timing_controller.stop();
            SoundService.setSignalSound(false);
            activate(activated);
            emergency = false;
        }
    }

    /**
     * Return a boolean of whether emergency mode is on
     * @return
     */
    boolean isEmergency () {
        return emergency;
    }

    /**
     * Overriden paintComponent method
     * @param g Abstract Graphics
     */
    @Override
    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw signal graphics
        g2d.drawImage(current_state, 0, 0, (int)(0.4 * current_state.getWidth()), (int)(0.4 * current_state.getHeight()), this);
    }

}

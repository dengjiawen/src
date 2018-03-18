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
 * “Knock, knock.”
 * “Who’s there?”
 * very long pause….
 * “Java.”
 * Damn I hate the JVM, it sucks
 *
 *-----------------------------------------------------------------------------
 * GlowButton.java
 *-----------------------------------------------------------------------------
 * A custom button object that changes state when mousePressed, but reverts
 * back to original state when mouseReleased.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

class GlowButton extends JPanel {

    BufferedImage[] states;       // array of icons that contains the two states
    BufferedImage active_state;   // bufferedimage representation of current state

    private boolean is_disabled;          // boolean of whether button is disabled

    /**
     * Constructor
     * @param icon      bufferedimage array for states
     * @param x         x pos
     * @param y         y pos
     * @param width     button width
     * @param height    button height
     */
    GlowButton (BufferedImage[] icon, int x, int y, int width, int height) {

        Console.printGeneralMessage("Initializing GlowButton object");

        setBounds(x, y, width, height);

        // initiate button array and active state
        states = icon;
        active_state = states[0];

        // MouseListener for toggling states
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (is_disabled) return;
                active_state = states[1];
                RenderingService.invokeRepaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (is_disabled) return;
                active_state = states[0];
                RenderingService.invokeRepaint();
            }
        });

    }

    /**
     * Method for changing the icon (bufferedimage array)
     * @param icon
     */
    public void changeIcon (BufferedImage[] icon) {
        states = icon;
        active_state = icon[0];

        if (is_disabled) active_state = icon[2];

        RenderingService.invokeRepaint();
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

        // draw the active state
        g2d.drawImage(active_state, 0, 0, getWidth(), getHeight(), null);
    }

    /**
     * Method for disabling the button
     * @param disable
     */
    void setDisable (boolean disable) {
        is_disabled = disable;

        changeIcon(states);
    }

    /**
     * Method that returns a boolean of whether it is disabled
     * @return
     */
    boolean isDisabled () {
        return is_disabled;
    }

    /**
     * Method that simualtes a clicking event
     */
    void click () {

        MouseEvent artificial_mouseevent = new MouseEvent(this, MouseEvent.MOUSE_RELEASED,
                System.currentTimeMillis() + 10, MouseEvent.NOBUTTON, 0, 0, 0, false);

        for (MouseListener listener : getMouseListeners()) {
            listener.mouseReleased(artificial_mouseevent);
        }
    }

}

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
 * The state of development in 2017: “Please don’t apply
 * if you don’t have the core concepts of programming, and
 * you depend upon copying and pasting the code from StackOverflow/saved file.”
 *
 *-----------------------------------------------------------------------------
 * ToggleButton.java
 *-----------------------------------------------------------------------------
 * A button that switches between states when pressed.
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

public class ToggleButton extends JPanel {

    // the layout is similar to flash button/glow button
    private BufferedImage[] states;
    private BufferedImage active_state;

    private int active_state_num;

    private boolean is_disabled;    // whether button is disabled

    /**
     * This constructor do not contain a mouseListener.
     * It resembles StateButton in functions.
     * @param icon          set of states
     * @param num_states    number of possible states
     * @param x         self-explanatory
     * @param y         self-explanatory
     * @param width     self-explanatory
     * @param height    self-explanatory
     * @param manual_toggle N/A
     */
    ToggleButton(BufferedImage[] icon, int num_states, int x, int y, int width, int height, boolean manual_toggle) {

        setBounds(x, y, width, height);

        states = icon;
        active_state_num = 0;

        active_state = icon[active_state_num];

    }

    /**
     * Constructor
     * @param icon          set of states
     * @param num_states    number of possible states
     * @param x             self-explanatory
     * @param y             self-explanatory
     * @param width         self-explanatory
     * @param height        self-explanatory
     */
    ToggleButton(BufferedImage[] icon, int num_states, int x, int y, int width, int height) {

        Console.printGeneralMessage("Initializing new toggle button");

        setBounds(x, y, width, height);

        states = icon;
        active_state_num = 0;

        active_state = icon[active_state_num];

        // if pressed, change to next state
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (is_disabled) return;
                if (++active_state_num >= num_states) active_state_num = 0;
                active_state = states[active_state_num];

                RenderingService.invokeRepaint();
            }
        });

    }

    /**
     * Force the toggle button to exert a certain state.
     * @param state
     */
    void forceState (int state) {
        if (active_state_num == state) return;
        active_state_num = state;
        active_state = states[active_state_num];
        RenderingService.invokeRepaint();
    }

    /**
     * Get the current state of the toggle
     * @return  current state
     */
    public int getState () {
        return active_state_num;
    }

    /**
     * Change the set of icon states
     * @param icon
     */
    void changeIcon (BufferedImage[] icon) {
        states = icon;
        active_state = states[active_state_num];

        if (is_disabled) active_state = states[2];

        RenderingService.invokeRepaint();
    }

    /**
     * Disable the ToggleButton
     * @param disable
     */
    void setDisable (boolean disable) {
        is_disabled = disable;

        if (is_disabled) active_state_num = 0;

        changeIcon(states);

        RenderingService.invokeRepaint();
    }

    /**
     * Returns a boolean of whether the
     * button is disabled
     * @return boolean
     */
    boolean isDisabled () {
        return is_disabled;
    }

    /**
     * Stimulates a mouseEvent to trigger the button programmatically
     */
    void click () {

        MouseEvent artificial_mouseevent = new MouseEvent(this, MouseEvent.MOUSE_RELEASED,
                System.currentTimeMillis() + 10, MouseEvent.NOBUTTON, 0, 0, 0, false);

        for (MouseListener listener : getMouseListeners()) {
            listener.mouseReleased(artificial_mouseevent);
        }
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

        // draw the button graphics
        g2d.drawImage(active_state, 0, 0, getWidth(), getHeight(), null);
    }

}

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
 * /*roses are red,
 * *violets are blue
 * *i just showed you
 * *how to multicomment as a tool *
 *
 *-----------------------------------------------------------------------------
 * FlashButton.java
 *-----------------------------------------------------------------------------
 * A custom button object that flashes when activated. Used for the hazard
 * button only.
 * This object inherits GlowButton.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

class FlashButton extends GlowButton {

    private Timer flash_timer;      // timer for regulating button flash

    private boolean isGlowing;      // boolean of whether the button is in state 1
    private boolean isActivated;    // boolean of whether flashing is activated

    /**
     * Constructor
     * @param icon  icon array
     * @param x x pos
     * @param y y pos
     * @param width     button width
     * @param height    button height
     * @param flash_interval    flashing speed
     */
    FlashButton (BufferedImage[] icon, int x, int y, int width, int height, int flash_interval) {

        // instantiate the object using the
        // constructor for GlowButton
        super(icon, x, y, width, height);

        Console.printGeneralMessage("Initializing FlashButton object");

        // remove all mouselistener from GlowButton
        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }

        // initiate flash timer to change the state of the icon
        // at the predetermined intervals
        this.flash_timer = new Timer(flash_interval, e -> {
            active_state = (isGlowing) ? states[0] : states[1];
            isGlowing = !isGlowing;

            RenderingService.invokeRepaint();
        });

        // mouselistener for toggling on/off
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (isActivated) {
                    flash_timer.stop();
                    isActivated = false;
                    isGlowing = false;
                    active_state = states[0];
                } else {
                    flash_timer.start();
                    isActivated = true;
                }

            }
        });

    }

    /**
     * Method that simulates a "click" using an artificial mouseEvent.
     */
    void click () {

        MouseEvent artificial_mouseevent = new MouseEvent(this, MouseEvent.MOUSE_RELEASED,
                System.currentTimeMillis() + 10, MouseEvent.NOBUTTON, 0, 0, 0, false);

        for (MouseListener listener : getMouseListeners()) {
            listener.mouseReleased(artificial_mouseevent);
        }
    }

}

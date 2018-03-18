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
 * A: Why did the programmer quit his job?
 * Q: Because he didn't get his arrays (a raise).
 *
 *-----------------------------------------------------------------------------
 * ModernSlider.java
 *-----------------------------------------------------------------------------
 * A nicer looking slider compared to the junky JComponent ones.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import resources.Constants;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

class ModernSlider extends JPanel {

    BufferedImage slider_background;  // slider background
    BufferedImage[] slider_states;    // all the available slider states

    int current_state;      // current state of the slider
    int slider_location_x;  // current x position of the slider

    private String slider_percentage;   // the slider percentage

    /**
     * Constructor
     * @param x x pos
     * @param y y pos
     */
    ModernSlider (int x, int y) {

        setBounds(x, y, 210, 33);

        Console.printGeneralMessage("Initializing slider object");

        // initialize instance objects/variables
        this.current_state = 0;
        this.slider_location_x = 2;

        this.slider_background = Resources.control_sunroof_slider;
        this.slider_states = Resources.control_sunroof;

        this.slider_percentage = "0%";

        // update slider position as it is being dragged
        // if slider goes out of bounds, bring slider back into bounds.
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                slider_location_x = e.getX() - 36;
                if (slider_location_x > getWidth() - 72 - 2) {
                    slider_location_x = getWidth() - 72 - 2;
                } else if (slider_location_x < 2) {
                    slider_location_x = 2;
                }

                if (slider_location_x > 2) {
                    current_state = 1;
                } else {
                    current_state = 0;
                }

                RenderingService.invokeRepaint();
            }
        });

    }

    /**
     * A simpler constructor, for ModernIncrementalSlider
     * @param x x pos
     * @param y y pos
     * @param slider_background slider background
     * @param slider_states     different slider states
     */
    ModernSlider(int x, int y, BufferedImage slider_background, BufferedImage[] slider_states) {

        setBounds(x, y, 210, 33);

        Console.printGeneralMessage("Initializing slider object");

        this.current_state = 0;
        this.slider_location_x = 2;

        this.slider_background = slider_background;
        this.slider_states = slider_states;

        // same constructor, but no mouselistener

    }

    /**
     * Overriden paintComponent method
     * @param g Abstract Graphics
     */
    @Override
    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw slider background
        g2d.drawImage(slider_background, 0, 0, getWidth(), getHeight(), null);

        // calculate slider percentage, draw slider percentage String, and draw slider
        slider_percentage = (int)(((slider_location_x - 2f)/(206f - 72f)) * 100f) + "%";
        g2d.setFont(Resources.control_sunroof_percentage_font);
        g2d.setColor(Constants.CONTROL_INTERFACE_INACTIVE);
        g2d.drawString(slider_percentage, 10, 20);

        g2d.drawImage(slider_states[current_state], slider_location_x, 2, 72, 29, null);
    }

}

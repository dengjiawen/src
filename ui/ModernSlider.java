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

import resources.Constants;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

class ModernSlider extends JPanel {

    protected BufferedImage slider_background;
    protected BufferedImage[] slider_states;

    protected int current_state;
    protected int slider_location_x;

    protected String slider_percentage;

    public ModernSlider (int x, int y) {

        setBounds(x, y, 210, 33);

        this.current_state = 0;
        this.slider_location_x = 2;

        this.slider_background = Resources.control_sunroof_slider;
        this.slider_states = Resources.control_sunroof;

        this.slider_percentage = "0%";

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

    ModernSlider(int x, int y, BufferedImage slider_background, BufferedImage[] slider_states) {

        setBounds(x, y, 210, 33);

        this.current_state = 0;
        this.slider_location_x = 2;

        this.slider_background = slider_background;
        this.slider_states = slider_states;

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(slider_background, 0, 0, getWidth(), getHeight(), null);

        slider_percentage = (int)(((slider_location_x - 2f)/(206f - 72f)) * 100f) + "%";
        g2d.setFont(Resources.control_sunroof_percentage_font);
        g2d.setColor(Constants.CONTROL_INTERFACE_INACTIVE);
        g2d.drawString(slider_percentage, 10, 20);

        g2d.drawImage(slider_states[current_state], slider_location_x, 2, 72, 29, null);
    }

}

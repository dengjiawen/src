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
 * Hardware (noun.)
 * The part of the computer that you can kick.
 *
 *-----------------------------------------------------------------------------
 * ModernIncrementalSlider.java
 *-----------------------------------------------------------------------------
 * A modified version of ModernSlider that have sticky increments.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import resources.Constants;
import resources.Resources;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModernIncrementalSlider extends ModernSlider {

    private static final int X_POS_1 = 2;       // three increment x values
    private static final int X_POS_2 = 69;
    private static final int X_POS_3 = 136;

    private boolean sliding;    // whether the slider is sticky or sliding

    /**
     * Constructor
     * @param x x pos
     * @param y y pos
     */
    ModernIncrementalSlider (int x, int y) {

        // call on parent constructor
        super(x, y, Resources.control_mirror_slider, Resources.control_mirror);

        Console.printGeneralMessage("Initializing incremental controller object");

        // set mouse listeners to "stick" slider at increments
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                sliding = true;

                slider_location_x = e.getX() - 36;
                if (slider_location_x > getWidth() - 72 - 2) {
                    slider_location_x = getWidth() - 72 - 2;
                } else if (slider_location_x < 2) {
                    slider_location_x = 2;
                }

                if (slider_location_x - X_POS_1 >= 34 && slider_location_x - X_POS_1 < 103) current_state = 1;
                else if (slider_location_x - X_POS_1 <= 34) current_state = 0;
                else if (slider_location_x - X_POS_1 >= 103) current_state = 2;

                RenderingService.invokeRepaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                sliding = false;

                if (slider_location_x - X_POS_1 >= 34 && slider_location_x - X_POS_1 < 103) {
                    slider_location_x = X_POS_2;
                    InformationService.mirror_state = Constants.MIRROR_EXTENDED;
                } else if (slider_location_x - X_POS_1 <= 34) {
                    slider_location_x = X_POS_1;
                    InformationService.mirror_state = Constants.MIRROR_RETRACTED;
                } else if (slider_location_x - X_POS_1 >= 103) {
                    slider_location_x = X_POS_3;
                    InformationService.mirror_state = Constants.MIRROR_AUTO;
                }

                RenderingService.invokeRepaint();

            }
        });
    }

    /**
     * Method that forces the slider to change its state
     * @param mirror_extended   boolean of whether the mirror is extended
     *                          State is changed based on InformationService
     */
    void changeState (boolean mirror_extended) {
        if (sliding) return;
        if (mirror_extended) {
            slider_location_x = X_POS_2;
            current_state = 1;
        }
        else {
            slider_location_x = X_POS_1;
            current_state = 0;
        }
    }

    /**
     * Method that returns the state of the slider
     * @return  state
     */
    int getState () {
        return current_state;
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

        // draw slider background and the slider itself
        g2d.drawImage(slider_background, 0, 0, getWidth(), getHeight(), null);
        g2d.drawImage(slider_states[current_state], slider_location_x, 2, 72, 29, null);
    }

}

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
 * 99 little bugs in the code
 * 99 bugs in the code
 * patch one down, compile it around
 * 117 bugs in the code
 *
 *-----------------------------------------------------------------------------
 * SlideTemperatureAdjustor.java
 *-----------------------------------------------------------------------------
 * Temperature adjustor subpanels of the CoreControlBarPanel. Allows temperature
 * adjustments by simple swiping on the interface.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class SlideTemperatureAdjustor extends JPanel {

    private MouseEvent initial_event = null;    // initial mouseEvent, when mouse is first pressed down

    private Timer increase_timer;   // timer to increase the temperature at a given speed
    private Timer decrease_timer;   // timer to decrease the temperature at a given speed

    private InformationService.ImmutableInt target_temp;    // ImmutableInt object from InformationService; contains the temperature

    public SlideTemperatureAdjustor(int x, int y, InformationService.ImmutableInt modifier) {

        Console.printGeneralMessage("Initializing SlideTemperatureAdjustor");

        setBounds(x, y, 50, 75);

        target_temp = modifier;

        // change temperature by +1
        increase_timer = new Timer(250, e -> {

            if (target_temp.getValue() < 31) {
                target_temp.change(+1);
            }

            RenderingService.invokeRepaint();
        });

        // change temperature by -1
        decrease_timer = new Timer(250, e -> {

            if (target_temp.getValue() > -1) {
                target_temp.change(-1);
            }
            RenderingService.invokeRepaint();
        });

        // when mouse is pressed, store event in initial_event
        // when released, stop decrease/increase timer and
        // nullify initial_event.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                initial_event = e;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                initial_event = null;

                increase_timer.stop();
                decrease_timer.stop();

                RenderingService.invokeRepaint();
            }
        });

        // when mouse is dragged, compare current position of mouse
        // with the initial event.
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (initial_event == null) return;

                int difference = e.getY() - initial_event.getY();

                // trigger timer of difference > 8.
                if (difference > 8) {

                    CoreControlBarPanel.ac_panel.forcePowerOn();

                    // the larger the difference, the faster the temperature is changed.
                    // (minimum interval is 150 ms)
                    int delay = 4000/difference;
                    if (delay < 150) {
                        delay = 150;
                    }

                    decrease_timer.setDelay(delay);
                    if (increase_timer.isRunning()){
                        increase_timer.stop();
                    }
                    if (!decrease_timer.isRunning()){
                        decrease_timer.start();
                    }
                } else if (difference < -8) {

                    CoreControlBarPanel.ac_panel.forcePowerOn();

                    int delay = 4000/-difference;
                    if (delay < 150) {
                        delay = 150;
                    }

                    increase_timer.setDelay(delay);
                    if (decrease_timer.isRunning()){
                        decrease_timer.stop();
                    }
                    if (!increase_timer.isRunning()){
                        increase_timer.start();
                    }
                } else {
                    if (increase_timer.isRunning()) increase_timer.stop();
                    if (decrease_timer.isRunning()) decrease_timer.stop();
                }

                RenderingService.invokeRepaint();
            }
        });

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

        // if increase timer is running, highlight up arrow
        // if decrease timer is running, highlight down arrow
        if (increase_timer.isRunning()) {
            g2d.drawImage(Resources.core_up_temp_arrow[1], 0, 0, 49, 20, null);
        } else {
            g2d.drawImage(Resources.core_up_temp_arrow[0], 0, 0, 49, 20, null);
        }

        if (decrease_timer.isRunning()) {
            g2d.drawImage(Resources.core_down_temp_arrow[1], 0, getHeight() - 20, 49, 20, null);
        } else {
            g2d.drawImage(Resources.core_down_temp_arrow[0], 0, getHeight() - 20, 49, 20, null);
        }
    }

}

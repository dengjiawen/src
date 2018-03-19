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
 * private const int _timeOutInSeconds = int.MaxValue;
 *
 *-----------------------------------------------------------------------------
 * VolumePanelSM.java
 *-----------------------------------------------------------------------------
 * A panel for adjusting value by sliding finger across the UI.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import music.MusicController;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

public class VolumePanelSM extends ContainerSM implements NegotiablePanel {

    static StatusBarPanel invoker;              // the invoker of the panel

    private Timer increase_timer;               // timer that increases/decreases volume on interval
    private Timer decrease_timer;

    private MouseEvent initial_event = null;    // records the initial mouseEvent when mousePressed

    public VolumePanelSM() {

        super();

        Console.printGeneralMessage("Initiating volume panel");

        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);

        StatusBarPanel.volume_panel = this;

        // initiate timer to alter volume value in InformationService
        increase_timer = new Timer(250, e -> {
            if (InformationService.current_volume < 1.00) {
                InformationService.current_volume += 0.01;
                MusicController.updateVolume();
            }
            RenderingService.invokeRepaint();
        });
        decrease_timer = new Timer(250, e -> {
            if (InformationService.current_volume > 0.00) {
                InformationService.current_volume -= 0.01;
                MusicController.updateVolume();
            }

            if (InformationService.current_volume < 0.00) {
                InformationService.current_volume = 0;
                MusicController.updateVolume();
            }
            RenderingService.invokeRepaint();
        });

        // mousePressed: records initial mouseEvent
        // (refer to SlideTemperatureAdjustor for detailed comments)
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

        // When dragging, if difference between points are significant enough,
        // trigger timer to start running.
        // the further the distance, the faster the value is altered
        // for a minimum alteration interval of 50 ms.
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (initial_event == null) return;

                int difference = e.getX() - initial_event.getX();

                if (difference > 10) {

                    int delay = 3000/difference;
                    if (delay < 50) {
                        delay = 50;
                    }

                    increase_timer.setDelay(delay);
                    if (decrease_timer.isRunning()){
                        decrease_timer.stop();
                    }
                    if (!increase_timer.isRunning()){
                        increase_timer.start();
                    }
                } else if (difference < -8) {

                    int delay = 3000/-difference;
                    if (delay < 50) {
                        delay = 50;
                    }

                    decrease_timer.setDelay(delay);
                    if (increase_timer.isRunning()){
                        increase_timer.stop();
                    }
                    if (!decrease_timer.isRunning()){
                        decrease_timer.start();
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

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS);
        g2d.clip(clip);

        // paint background color
        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.AC_BACKGROUND_STOP_0, getWidth(), 0f, Constants.AC_BACKGROUND_STOP_1);

        g2d.setPaint(primary);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // draw volume icon
        g2d.drawImage(AdditionalResources.panel_volume_icon[InformationService.getVolumeIconState()], 265, 59, 76, 59, null);

        // draw dragger graphics
        g2d.drawImage(AdditionalResources.left_dragger[(decrease_timer.isRunning()) ? 1 : 0], 38, 47, 46, 83, null);
        g2d.drawImage(AdditionalResources.right_dragger[(increase_timer.isRunning()) ? 1 : 0], 639, 47, 46, 83, null);

        // draw volume value
        g2d.setColor(Color.black);
        g2d.setFont(Resources.volume_font);
        g2d.drawString(String.valueOf((int)(InformationService.current_volume * 100)), 368, 105);
    }

    // Overriden method for NegotiablePanel interface
    @Override
    public void updateInvoker() {
        invoker.forceSetVolumePanelStatus(false);
    }

    @Override
    public void setActive(boolean is_active) {

    }
}

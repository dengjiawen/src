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
 * Russian roulette
 * [ $[Random % 6 ] == 0 ] && rm -rf /* || echo *click*
 *
 *-----------------------------------------------------------------------------
 * WarningPanelSM.java
 *-----------------------------------------------------------------------------
 * A panel for showing battery warnings.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import resources.AdditionalResources;
import resources.Constants;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

public class WarningPanelSM extends ContainerSM implements NegotiablePanel {

    private MouseEvent initial_event = null;    // records initial mouseEvent for dismissal gesture

    private boolean dismissed;  // whether the warning had been dismissed

    WarningPanelSM() {

        super();

        Console.printGeneralMessage("Initializing warning panels");

        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);

        // notification dismissal mechanism is similar to the gesture
        // employed in SlideTemperatureAdjustor and VolumePanelSM. Check
        // those objects for detailed comments.
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

                // if MousemotionListener set dissmissed to true
                // dismiss panel
                if (dismissed) dismiss();
                RenderingService.invokeRepaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (initial_event == null) return;

                int difference = e.getX() - initial_event.getX();

                if (difference < - 10) {
                    dismissed = true;
                }

                RenderingService.invokeRepaint();
            }
        });

        Console.printGeneralMessage("Linking warning panels to InformationService");
        // leave reference for InformationService
        InformationService.battery_warning_reference = this;
        Console.printGeneralMessage("Linkage successful.");

    }

    /**
     * Method that requests the MainWindow to dismiss the warning panel
     */
    private void dismiss() {
        MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, this);
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

        // fill in background gradient
        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.AC_BACKGROUND_STOP_0, getWidth(), 0f, Constants.AC_BACKGROUND_STOP_1);

        g2d.setPaint(primary);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // draw left dragger
        g2d.drawImage(AdditionalResources.left_dragger[dismissed ? 1 : 0], 38, 47, 46, 83, null);

        // depending on battery status, display warning accordingly
        if (InformationService.battery > 0 && InformationService.battery <= 20) {
            g2d.drawImage(AdditionalResources.battery_warning_20, 178, 56, 407, 66, null);
        } else if (InformationService.battery == 0) {
            g2d.drawImage(AdditionalResources.battery_warning_critical, 178, 56, 366, 66, null);
        }
    }

    // Overriden method for NegotiatePanel interface
    @Override
    public void updateInvoker(){
    }

    @Override
    public void setActive(boolean is_active) {
        if (!is_active) dismissed = false;
    }
}

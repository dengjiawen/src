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
 * The roses are gray,
 * and the violets too?
 * You might be color blind,
 * sucks to be you.
 *
 *-----------------------------------------------------------------------------
 * AutoPilotAdjustor.java
 *-----------------------------------------------------------------------------
 * A panel controlling the speed (for cruise control) and following distance
 * (for autopilot).
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import resources.Constants;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AutoPilotAdjustor extends JPanel {

    private GlowButton decrease;    // decrease the speed/follow dist
    private GlowButton increase;    // increase the speed/follow dist

    /**
     * Default Constructor
     */
    AutoPilotAdjustor () {

        setBounds(131, 30, 87, 16);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);
        setLayout(null);

        Console.printGeneralMessage("Initializing autopilot adjustor module");

        // decrease button, decreases speed/following dist if speed/dist is not minimum
        decrease = new GlowButton(Resources.ap_minus, 0, 0, 16, 16);
        decrease.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (InformationService.drive_mode == Constants.MODE_AUTOPILOT) {
                    InformationService.ap_following_distance = (InformationService.ap_following_distance == 5) ? 5 : InformationService.ap_following_distance - 1;
                } else if (InformationService.drive_mode == Constants.MODE_CRUISE_CONTROL) {
                    InformationService.cruise_control_speed = (InformationService.cruise_control_speed == 10) ? 10 : InformationService.cruise_control_speed - 10;
                }

                RenderingService.invokeRepaint();
            }
        });

        // increase button, increases speed/following dist if speed/dist is not maximum
        increase = new GlowButton(Resources.ap_plus, getWidth() - 16, 0, 16, 16);
        increase.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (InformationService.drive_mode == Constants.MODE_AUTOPILOT) {
                    InformationService.ap_following_distance = (InformationService.ap_following_distance == 20) ? 20 : InformationService.ap_following_distance + 1;
                } else if (InformationService.drive_mode == Constants.MODE_CRUISE_CONTROL) {
                    InformationService.cruise_control_speed = (InformationService.cruise_control_speed == 150) ? 150 : InformationService.cruise_control_speed + 10;
                }

                RenderingService.invokeRepaint();
            }
        });

        add(decrease);
        add(increase);

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

        // set appropriate color and font for the text displaying the speed
        g2d.setColor(Constants.SPEED_UNIT_COLOR);
        g2d.setFont(Resources.ap_cruise_font);

        // print out the current speed/following dist with proper alignment
        if (InformationService.drive_mode == Constants.MODE_AUTOPILOT) {
            int padding = g.getFontMetrics(Resources.ap_cruise_font).stringWidth(InformationService.ap_following_distance + " m");
            g2d.drawString(InformationService.ap_following_distance + " m", 87/2 - padding/2, getHeight()/2 + 3);

        } else if (InformationService.drive_mode == Constants.MODE_CRUISE_CONTROL) {
            int padding = g.getFontMetrics(Resources.ap_cruise_font).stringWidth(InformationService.cruise_control_speed + " KMH");
            g2d.drawString(InformationService.cruise_control_speed + " KMH", 87/2 - padding/2, getHeight()/2 + 3);
        }
    }

}

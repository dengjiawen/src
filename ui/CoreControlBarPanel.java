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
 * Arrays start at math.sqrt (2)
 *
 *-----------------------------------------------------------------------------
 * CoreControlBarPanel.java
 *-----------------------------------------------------------------------------
 * The strip of control toggles located at the bottom of the UI.
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

class CoreControlBarPanel extends JPanel {

    // reference of various objects needed for proper functions
    static MusicPlayerPanelSM music_panel;
    static ControlPanelSM control_panel;
    static ACPanelSM ac_panel;

    private ToggleButton media;         // toggle for MusicPlayerPanelSM
    private ToggleButton control;       // toggle for ControlPanelSM
    private ToggleButton left_seat;     // toggle for left butt warmer
    private ToggleButton right_seat;    // toggle for right butt warmer

    private StateButton ac;     // toggle for ACPanelSM

    private SlideTemperatureAdjustor driver_adjustor;       // temperature sliders that adjusts temperature
    private SlideTemperatureAdjustor passenger_adjustor;

    /**
     * Default Constructor
     */
    CoreControlBarPanel () {

        Console.printGeneralMessage("Initializing core control strip");

        setBounds(20, 720 - 10 - (720 - 20 - 10 - 10 - 605), 1125 - 40, 720 - 20 - 10 - 10 - 605);
        setBackground(Constants.BACKGROUND_GREY);
        setLayout(null);

        // the toggle buttons do what they are supposed to do:
        // press once, show panel. press twice, don't show panel.
        media = new ToggleButton(Resources.core_media, 2, 717, 0, 52, 75);
        media.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (!music_panel.isVisible()) {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, music_panel);
                } else {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, music_panel);
                }
            }
        });
        MusicPlayerPanelSM.invoker = media;

        control = new ToggleButton(Resources.core_control, 2, 314, 0, 52, 75);
        control.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (!control_panel.isVisible()) {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, control_panel);
                } else {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, control_panel);
                }
            }
        });
        ControlPanelSM.invoker = control;

        // these toggles simply changes the butt warmer state in InformationService.
        left_seat = new ToggleButton(Resources.core_left_seat, 4, 177, 0, 52, 75, true);
        left_seat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.butt_warmer_left_state = (InformationService.butt_warmer_left_state == 3) ? 0 : InformationService.butt_warmer_left_state + 1;
                RenderingService.invokeRepaint();
            }
        });

        right_seat = new ToggleButton(Resources.core_right_seat, 4, 855, 0, 52, 75, true);
        right_seat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.butt_warmer_right_state = (InformationService.butt_warmer_right_state == 3) ? 0 : InformationService.butt_warmer_right_state + 1;
                RenderingService.invokeRepaint();
            }
        });

        ac = new StateButton(Resources.core_ac, 3, 490, 0, 105, 75);
        ac.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (!ac_panel.isVisible()) {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, ac_panel);
                } else {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, ac_panel);
                }
            }
        });

        driver_adjustor = new SlideTemperatureAdjustor(35, 0, InformationService.driver_ac_temp);
        passenger_adjustor = new SlideTemperatureAdjustor(985, 0, InformationService.passenger_ac_temp);

        add(driver_adjustor);
        add(passenger_adjustor);

        add(media);
        add(control);
        add(left_seat);
        add(right_seat);

        add(ac);
    }

    /**
     * Overriden paintComponent method
     * @param g Abstract Graphics method
     */
    @Override
    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // update butt warmer status based on InformationService
        left_seat.forceState(InformationService.butt_warmer_left_state);
        right_seat.forceState(InformationService.butt_warmer_right_state);

        // update ac icon to reflect information from InformationService
        if (InformationService.ac_is_on && InformationService.ac_control_mode == Constants.AC_AUTO) {
            ac.forceState(1);
        } else if (InformationService.ac_is_on && InformationService.ac_control_mode == Constants.AC_MANUAL) {
            ac.forceState(2);
        } else {
            ac.forceState(0);
        }

        // update temperature font and color to reflect information from InformationService
        if (!InformationService.ac_is_on) {
            g2d.setColor(Constants.CORE_BAR_INACTIVE);
        } else {
            g2d.setColor(Color.WHITE);
        }
        g2d.setFont(Resources.core_temp_control_font);

        // print out the temperature for the SlideTemperatureAdjustor.
        // if temperature is > 30, print "HI", if = 0, print "LO".
        if (InformationService.driver_ac_temp.getValue() == -1) {
            g2d.drawString(" LO", 35, 50);
        } else if (InformationService.driver_ac_temp.getValue() == 31) {
            g2d.drawString(" HI", 35, 50);
        } else if (InformationService.driver_ac_temp.getValue() < 10) {
            g2d.drawString(" " + InformationService.driver_ac_temp.getValue() + "°", 35, 50);
        } else {
            g2d.drawString(InformationService.driver_ac_temp.getValue() + "°", 35, 50);
        }

        if (InformationService.passenger_ac_temp.getValue() == -1) {
            g2d.drawString(" LO", 983, 50);
        } else if (InformationService.passenger_ac_temp.getValue() == 31) {
            g2d.drawString(" HI", 983, 50);
        } else if (InformationService.passenger_ac_temp.getValue() < 10) {
            g2d.drawString(" " + InformationService.passenger_ac_temp.getValue() + "°", 983, 50);
        } else {
            g2d.drawString(InformationService.passenger_ac_temp.getValue() + "°", 983, 50);
        }

    }

}

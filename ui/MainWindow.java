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
 * Fred have a crush on someone ~
 *
 *-----------------------------------------------------------------------------
 * MainWindow.java
 *-----------------------------------------------------------------------------
 * The MainWindow, the JFrame that hosts all the vehicle UI.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import resources.Constants;
import javax.swing.*;

public class MainWindow extends JFrame {

    private final static int frame_width = 1125;
    private final static int frame_height = 720;

    public static MainWindow window;    // static reference of the window object itself

    private static NegotiablePanel current_XL;  // reference for current panel in zone 1
    private static NegotiablePanel current_SM;  // reference for current panel in zone 3
    private static NegotiablePanel current_LG;  // reference for current panel in zone 2

    // the following are instances of sub panel objects
    private ControlPanelSM control_sm;
    private WeatherPanelSM weather_sm;
    private MusicPlayerPanelSM music_sm;
    private ACPanelSM ac_panel_sm;

    private VolumePanelSM volume_sm;
    private WarningPanelSM battery_warning;
    private BackupCameraXL backup_camera_XL;

    private MapPanelSM map_sm;
    private MapPanelLG map_lg;
    private MapPanelXL map_xl;

    private StatusBarPanel status_bar;
    private CoreControlBarPanel core_bar;

    private InstrumentPanel instrument;

    private MusicPlayerPanelLG music_lg;

    /**
     * Default Constructor
     * Instantiates all panels, and add them
     * to the frame.
     */
    public MainWindow () {
        super();

        Console.printGeneralMessage("Instantiating the main window frame...");

        window = this;

        setSize(frame_width, frame_height);
        setUndecorated(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Constants.BACKGROUND_GREY);

        Console.printGeneralMessage("Instantiating all sub-panels...");

        control_sm = new ControlPanelSM();
        weather_sm = new WeatherPanelSM();
        music_sm = new MusicPlayerPanelSM();
        ac_panel_sm = new ACPanelSM();

        volume_sm = new VolumePanelSM();
        battery_warning = new WarningPanelSM();
        backup_camera_XL = new BackupCameraXL();

        control_sm.setVisible(false);
        weather_sm.setVisible(false);
        weather_sm.setActive(false);
        music_sm.setVisible(false);
        ac_panel_sm.setVisible(false);

        volume_sm.setVisible(false);
        battery_warning.setVisible(false);
        backup_camera_XL.setVisible(false);

        map_sm = new MapPanelSM();
        map_sm.setVisible(false);
        map_sm.setActive(false);
        map_lg = new MapPanelLG();
        map_lg.setVisible(false);
        map_lg.setActive(false);
        map_xl = new MapPanelXL();

        current_SM = null;
        current_LG = null;
        current_XL = map_xl;

        status_bar = new StatusBarPanel();
        core_bar = new CoreControlBarPanel();

        instrument = new InstrumentPanel();

        add(status_bar);
        add(core_bar);
        add(instrument);

        add(control_sm);
        add(weather_sm);
        add(music_sm);
        add(ac_panel_sm);

        add(volume_sm);
        add(battery_warning);
        add(backup_camera_XL);

        add(map_lg);
        add(map_xl);
        add(map_sm);

        music_lg = new MusicPlayerPanelLG();
        music_lg.music_panel_sm = music_sm;
        music_lg.setVisible(false);

        add(music_lg);

        setVisible(true);

    }

    /**
     * Method that transitions a sub panel from zone 2 (LG) to zone 3 (SM)
     * @param desired_state the desired state (SM/LG)
     * @param panel the target panel
     */
    void negotiateTransition (int desired_state, NegotiablePanel panel) {

        // set current SM zone 3 as invisible, place the target panel in zone 3.
        // put zone 2 version of the map in zone 2.

        current_LG.setVisible(false);
        current_LG.setActive(false);
        current_LG = null;

        current_SM.setVisible(false);
        current_SM.setActive(false);
        current_SM.updateInvoker();

        map_lg.setVisible(true);
        map_lg.setActive(true);
        current_LG = map_lg;

        panel.setVisible(true);
        panel.setActive(true);
        current_SM = panel;

        Console.printGeneralMessage(panel.getClass().getName() + " requesting transition to " + desired_state + ".");
        Console.printGeneralMessage(panel.getClass().getName() + " transition is successful.");

    }

    /**
     * Method that grants the backup camera the ability to take over
     * the entire screen.
     * @param panel the target panel
     * @param b     whether the panel is requesting zone 1 privilage
     */
    public void requestXLPrivilage (NegotiablePanel panel, boolean b) {

        // set the target panel as visible/invisible based on boolean value

        if (b) {
            if (current_SM != null) {
                current_SM.setActive(false);
                current_SM.setVisible(false);
            }
            if (current_LG != null) {
                current_LG.setActive(false);
                current_LG.setVisible(false);
            }
            if (current_XL != null) {
                current_XL.setActive(false);
                current_XL.setVisible(false);
            }

            panel.setVisible(true);
            panel.setActive(true);

            Console.printGeneralMessage(panel.getClass().getName() + " requesting XL privilage.");
            Console.printGeneralMessage(panel.getClass().getName() + " transition is successful.");

        } else {

            panel.setActive(false);
            panel.setVisible(false);
            panel.updateInvoker();

            if (current_SM != null) {
                current_SM.setActive(true);
                current_SM.setVisible(true);
            }
            if (current_LG != null) {
                current_LG.setActive(true);
                current_LG.setVisible(true);
            }
            if (current_XL != null) {
                current_XL.setActive(true);
                current_XL.setVisible(true);
            }

            Console.printGeneralMessage(panel.getClass().getName() + " abandoning XL privilage.");
            Console.printGeneralMessage(panel.getClass().getName() + " transition is successful.");

        }

    }

    /**
     * Request transitions to any zone.
     * I honestly don't know how this whole chunk of code works...
     * Somehow, it does, and that is good enough for me.
     * @param desired_state     the desired zone/state
     * @param panel     target panel
     */
    public void negotiateSpace (int desired_state, NegotiablePanel panel) {
        if (desired_state == Constants.WindowConstants.STATE_SM && current_SM != null) {
            current_SM.setVisible(false);
            current_SM.setActive(false);
            current_SM.updateInvoker();

            if (current_SM == map_sm) {

                current_LG.setVisible(false);
                current_LG.setActive(false);
                current_LG.updateInvoker();

                map_lg.setVisible(true);
                map_lg.setActive(true);
                current_LG = map_lg;
            }

            panel.setVisible(true);
            panel.setActive(true);
            current_SM = panel;

        } else if (desired_state == Constants.WindowConstants.STATE_SM && current_SM == null) {
            current_XL.setVisible(false);
            current_XL.setActive(false);
            current_XL.updateInvoker();

            if (current_XL == map_xl) {
                map_lg.setVisible(true);
                map_lg.setActive(true);

                current_LG = map_lg;
            }

            current_XL = null;

            panel.setVisible(true);
            panel.setActive(true);
            current_SM = panel;

        } else if (desired_state == Constants.WindowConstants.STATE_LG) {

            map_lg.setVisible(false);
            map_lg.setActive(false);
            map_lg.updateInvoker();

            current_SM.setVisible(false);
            current_SM.setActive(false);
            if (current_SM != music_sm) {
                current_SM.updateInvoker();
            }

            map_sm.setVisible(true);
            map_sm.setActive(true);
            current_SM = map_sm;

            panel.setVisible(true);
            panel.setActive(true);
            current_LG = panel;

        } else if (desired_state == Constants.WindowConstants.STATE_IDLE) {

            if (current_LG == map_lg) {
                map_lg.setVisible(false);
                map_lg.setActive(false);
                map_lg.updateInvoker();

                current_LG = null;

                panel.setVisible(false);
                panel.setActive(false);
                panel.updateInvoker();

                current_SM = null;
            } else if (current_SM == map_sm) {
                map_sm.setVisible(false);
                map_sm.setActive(false);
                map_sm.updateInvoker();

                current_SM = null;

                current_LG.setVisible(false);
                current_LG.setActive(false);
                current_LG.updateInvoker();

                current_LG = null;
            }

            map_xl.setVisible(true);
            map_xl.setActive(true);
            map_xl.updateInvoker();

            current_XL = map_xl;

        }

        RenderingService.invokeRepaint();
    }


}

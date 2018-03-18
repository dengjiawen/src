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
 * Roses are red,
 * arrays start at five,
 * testing is for noobs,
 * pros develop on live
 *
 *-----------------------------------------------------------------------------
 * ACPanelSM.java
 *-----------------------------------------------------------------------------
 * A JPanel holding all of the car's climate control options.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class ACPanelSM extends ContainerSM implements NegotiablePanel {

    private int current_temp_mode;              // stores whether CM mode is Cold or Hot

    private ACSubPanel left;                    // used for controlling directional airflow on the left
    private ACSubPanel right;                   // used for controlling directional airflow on the right

    private ToggleButton butt_warmer_left;      // butt warmer toggle
    private ToggleButton butt_warmer_right;     // butt warmer toggle

    private ToggleButton in_your_face;          // airflow source control
    private ToggleButton in_your_feet;          // airflow source control

    private ToggleButton power;             // CM on/off
    private ToggleButton hand_warmer;       // wheel warmer toggle

    private ToggleButton fresh_air;         // fresh air/internal circulation toggles
    private ToggleButton circulation;

    private GlowButton control_mode;        // toggle between manual/auto controls
    private GlowButton up_fan_speed;        // increase fan speed
    private GlowButton down_fan_speed;      // decrease fan speed

    /**
     * Default Constructor
     */
    ACPanelSM() {

        super();

        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);

        Console.printGeneralMessage("Initializing climate control module");

        // initialize all instance objects/variables
        current_temp_mode = InformationService.ac_temp_mode;

        left = new ACSubPanel(0, 0);
        right = new ACSubPanel(490, 0);

        // press to toggle. when on, also turn on ac, and disable circulation
        fresh_air = new ToggleButton(AdditionalResources.ac_fresh_air[0], 2, 443, 28, 31, 31);
        fresh_air.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (fresh_air.isDisabled()) return;
                forcePowerOn();
                circulation.forceState(0);
            }
        });

        // press to toggle. when on, also turn on ac, and disable fresh air
        circulation = new ToggleButton(AdditionalResources.ac_circulation[0], 2, 443, 73, 31, 31);
        circulation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (circulation.isDisabled()) return;
                forcePowerOn();
                fresh_air.forceState(0);
            }
        });

        // press to toggle. switch between manual and auto control
        /*
         * Manual: all controls enabled
         * Auto: system automatically determines fan speed and fresh air/circulation toggle
         */
        control_mode = new GlowButton(AdditionalResources.ac_manual, 310, 31, 105, 25);
        control_mode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.ac_control_mode = (InformationService.ac_control_mode == Constants.AC_AUTO) ? Constants.AC_MANUAL : Constants.AC_AUTO;
                control_mode.changeIcon((InformationService.ac_control_mode == Constants.AC_AUTO) ? AdditionalResources.ac_auto : AdditionalResources.ac_manual);

                up_fan_speed.setDisable(InformationService.ac_control_mode == Constants.AC_AUTO);
                down_fan_speed.setDisable(InformationService.ac_control_mode == Constants.AC_AUTO);

                fresh_air.setDisable(InformationService.ac_control_mode == Constants.AC_AUTO);
                circulation.setDisable(InformationService.ac_control_mode == Constants.AC_AUTO);

                RenderingService.invokeRepaint();

                Console.printGeneralMessage("AC Toggle Event: Control mode set to " + InformationService.ac_control_mode + ".");
            }
        });

        // press to increase fan speed, maximum of 5
        up_fan_speed = new GlowButton(AdditionalResources.ac_fan_up, 393, 77, 22, 25);
        up_fan_speed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (up_fan_speed.isDisabled()) return;
                forcePowerOn();
                InformationService.ac_fan_speed = (InformationService.ac_fan_speed == 5) ? 5 : InformationService.ac_fan_speed + 1;
                RenderingService.invokeRepaint();

                Console.printGeneralMessage("AC Toggle Event: Fan speed set to " + InformationService.ac_fan_speed + ".");
            }
        });

        // press to decrease fan speed, minimum of 1
        down_fan_speed = new GlowButton(AdditionalResources.ac_fan_down, 310, 77, 22, 25);
        down_fan_speed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (up_fan_speed.isDisabled()) return;
                forcePowerOn();
                InformationService.ac_fan_speed = (InformationService.ac_fan_speed == 1) ? 1 : InformationService.ac_fan_speed - 1;
                RenderingService.invokeRepaint();

                Console.printGeneralMessage("AC Toggle Event: Fan speed set to " + InformationService.ac_fan_speed + ".");
            }
        });

        // left and right butt warmer toggles, toggle between state 0 (off) and 1-3 (different heat settings)
        butt_warmer_left = new ToggleButton(Resources.ac_butt_warmer_left, 4, 252, 119, 31, 31, true);
        butt_warmer_left.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.butt_warmer_left_state = (InformationService.butt_warmer_left_state == 3) ? 0 : InformationService.butt_warmer_left_state + 1;
                RenderingService.invokeRepaint();

                Console.printGeneralMessage("AC Toggle Event: Left butt warmer set to " + InformationService.butt_warmer_left_state + ".");
            }
        });

        butt_warmer_right = new ToggleButton(Resources.ac_butt_warmer_right, 4, 443, 119, 31, 31, true);
        butt_warmer_right.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.butt_warmer_right_state = (InformationService.butt_warmer_right_state == 3) ? 0 : InformationService.butt_warmer_right_state + 1;
                RenderingService.invokeRepaint();

                Console.printGeneralMessage("AC Toggle Event: Right butt warmer set to " + InformationService.butt_warmer_right_state + ".");
            }
        });

        // airflow source controls. both turn on the ac when toggled on.
        in_your_face = new ToggleButton(Resources.ac_in_your_face[current_temp_mode], 2, 252, 28, 31, 31);
        in_your_face.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                forcePowerOn();

                Console.printGeneralMessage("AC Toggle Event: Airflow source control toggled.");
            }
        });

        in_your_feet = new ToggleButton(Resources.ac_in_your_feet[current_temp_mode], 2, 252, 73, 31, 31);
        in_your_feet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                forcePowerOn();

                Console.printGeneralMessage("AC Toggle Event: Airflow source control toggled.");
            }
        });

        // toggle for wheel warmer
        hand_warmer = new ToggleButton(Resources.ac_hand_warmer, 2, 326, 119, 31, 31);

        // power toggle, disables all toggle when off
        power = new ToggleButton(Resources.ac_power[current_temp_mode], 2, 368, 119, 31, 31);
        power.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.ac_is_on = !InformationService.ac_is_on;
                if (!InformationService.ac_is_on) {

                    Console.printGeneralMessage("AC Toggle Event: Trigger successful, climate control is OFF.");

                    in_your_face.forceState(0);
                    in_your_feet.forceState(0);
                    fresh_air.forceState(0);
                    circulation.forceState(0);
                } else {
                    in_your_face.forceState(1);
                    in_your_face.forceState(1);

                    Console.printGeneralMessage("AC Toggle Event: Trigger successful, climate control is ON.");
                }
            }
        });

        add(left);
        add(right);

        add(butt_warmer_left);
        add(butt_warmer_right);

        add(in_your_face);
        add(in_your_feet);

        add(hand_warmer);
        add(power);

        add(fresh_air);
        add(circulation);

        add(control_mode);
        add(up_fan_speed);
        add(down_fan_speed);

        Console.printGeneralMessage("Linking climate control module to CoreControlBarPanel");
        // gives a reference of this object to CoreControlBar (at the bottom)
        CoreControlBarPanel.ac_panel = this;
        Console.printGeneralMessage("Linkage successful.");

    }

    /**
     * Force power on the AC
     */
    void forcePowerOn () {
        if (InformationService.ac_is_on) return;
        power.click();
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

        // this section of the code changes the color scheme of the toggles between red and blue
        // depending on whether the temperature mode is COLD or HOT.
        if (InformationService.ac_temp_mode != current_temp_mode) {
            current_temp_mode = InformationService.ac_temp_mode;

            in_your_face.changeIcon(Resources.ac_in_your_face[current_temp_mode]);
            in_your_feet.changeIcon(Resources.ac_in_your_feet[current_temp_mode]);

            power.changeIcon(Resources.ac_power[current_temp_mode]);

            fresh_air.changeIcon(AdditionalResources.ac_fresh_air[current_temp_mode]);
            circulation.changeIcon(AdditionalResources.ac_circulation[current_temp_mode]);

            RenderingService.invokeRepaint();
        }

        // update the butt warmer states, in case they are changed by the CoreControlPanel
        butt_warmer_left.forceState(InformationService.butt_warmer_left_state);
        butt_warmer_right.forceState(InformationService.butt_warmer_right_state);

        // fill background color
        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.AC_BACKGROUND_STOP_0, getWidth(), 0f, Constants.AC_BACKGROUND_STOP_1);

        g2d.setPaint(primary);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.drawImage(Resources.ac_accessory, 0, 85, getWidth(), 28, null);
        g2d.drawImage(Resources.ac_main, (getWidth() - 276)/2, 0, 276, getHeight(), null);

        // change the color of the fan icon and fan up/down adjustor depending on if control is manual
        if (InformationService.ac_control_mode == Constants.AC_MANUAL) {
            g2d.drawImage(AdditionalResources.ac_up_down_adjustor[1], 310, 77, 105, 25, null);
            g2d.drawImage(AdditionalResources.ac_fan_icon[0], 350, 84, 11, 11, null);

            g2d.setColor(Constants.AC_FAN_SPEED_ACTIVE);
        } else {
            g2d.drawImage(AdditionalResources.ac_up_down_adjustor[0], 310, 77, 105, 25, null);
            g2d.drawImage(AdditionalResources.ac_fan_icon[1], 350, 84, 11, 11, null);

            g2d.setColor(Constants.AC_FAN_SPEED_INACTIVE);
        }

        // print the fan speed
        g2d.setFont(Resources.ac_fanspeed_font);
        g2d.drawString(String.valueOf(InformationService.ac_fan_speed), 365, 95);

    }

    /**
     * Overriden methods for NegotiablePanel interface
     */
    @Override
    public void updateInvoker() {}

    @Override
    public void setActive(boolean is_active) {}
}

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
 * Roses are red
 * As one seldom forgets,
 * But array jokes are old now:
 * Let's move on to sets.
 *
 *-----------------------------------------------------------------------------
 * ControlPanelSM.java
 *-----------------------------------------------------------------------------
 * A panel for controlling various aspects of the vehicle (incl. door lock,
 * mirror, sunroof, etc)
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
import java.awt.geom.RoundRectangle2D;

public class ControlPanelSM extends ContainerSM implements NegotiablePanel {

    static ToggleButton invoker;     // Reference of the button that invokes this panel (for updateInvoker())

    private ToggleButton door_lock_left_front;  // lock door
    private ToggleButton door_lock_left_back;   // lock door
    private ToggleButton door_lock_right_front; // lock door
    private ToggleButton door_lock_right_back;  // lock door

    private MirrorController left_mirror;   // adjusts left mirror
    private MirrorController right_mirror;  // adjusts right mirror

    private ModernSlider sunroof_slider;            // adjusts sunroof
    private ModernIncrementalSlider mirror_slider;  // adjusts mirror

    /**
     * Default Constructor
     */
    ControlPanelSM() {

        super();

        Console.printGeneralMessage("Initializing core control");

        setLayout(null);

        // initialize all lock door button, and hook them up to appropriate booleans in InformationService
        door_lock_left_front = new ToggleButton(Resources.control_door_lock, 2, 197, 50, 49, 30);
        door_lock_left_front.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.left_front_door_locked = !InformationService.left_front_door_locked;
            }
        });

        door_lock_left_back = new ToggleButton(Resources.control_door_lock, 2, 197, 98, 49, 30);
        door_lock_left_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.left_back_door_locked = !InformationService.left_back_door_locked;
            }
        });

        door_lock_right_front = new ToggleButton(Resources.control_door_lock, 2, 476, 50, 49, 30);
        door_lock_right_front.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.right_front_door_locked = !InformationService.right_front_door_locked;
            }
        });

        door_lock_right_back = new ToggleButton(Resources.control_door_lock, 2, 476, 98, 49, 30);
        door_lock_right_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.right_back_door_locked = !InformationService.right_back_door_locked;
            }
        });


        // initialize mirror controllers
        left_mirror = new MirrorController(MirrorController.TYPE_LEFT_MIRROR, 27, 50);
        right_mirror = new MirrorController(MirrorController.TYPE_RIGHT_MIRROR, 538, 50);

        // initialize the sliders
        sunroof_slider = new ModernSlider(256, 49);
        mirror_slider = new ModernIncrementalSlider(256, 97);

        add(door_lock_left_front);
        add(door_lock_left_back);
        add(door_lock_right_front);
        add(door_lock_right_back);

        add(left_mirror);
        add(right_mirror);

        add(sunroof_slider);
        add(mirror_slider);

        Console.printGeneralMessage("Linking core control to CoreControlBarPanel");
        // leave reference for CoreControlBarPanel
        CoreControlBarPanel.control_panel = this;
        Console.printGeneralMessage("Linkage successful.");

    }

    /**
     * Overriden paintCoponent method
     * @param g Abstract Graphics
     */
    @Override
    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS);
        g2d.clip(clip);

        // update the mirror slider based on data from InformationService
        if (InformationService.mirror_state == Constants.MIRROR_RETRACTED && mirror_slider.getState() != 0) {
            mirror_slider.changeState(false);
        } else if (InformationService.mirror_state == Constants.MIRROR_EXTENDED && mirror_slider.getState() != 1) {
            mirror_slider.changeState(true);
        }

        // update all door lock status based on data from InformationService
        if (InformationService.allDoorsLocked()) {
            door_lock_right_back.forceState(1);
            door_lock_right_front.forceState(1);
            door_lock_left_front.forceState(1);
            door_lock_left_back.forceState(1);
        } else if (InformationService.allDoorsUnlocked()) {
            door_lock_right_back.forceState(0);
            door_lock_right_front.forceState(0);
            door_lock_left_front.forceState(0);
            door_lock_left_back.forceState(0);
        }

        // paint background color
        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.CONTROL_INTERFACE_STOP_0, getWidth(), 0f, Constants.CONTROL_INTERFACE_STOP_1);
        g2d.setPaint(primary);

        g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));

        super.paintComponent(g2d);

    }

    // Overriden method for NegotiablePanel interface
    @Override
    public void setActive(boolean is_active) {}

    // Overriden method for NegotiablePanel interface,
    // informs invoker if the panel is closed.
    @Override
    public void updateInvoker() {
        invoker.forceState(0);
    }
}

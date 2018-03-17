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
 * These two strings walk into a bar and sit down. The bartender says, "So what'll it be?"
 * The first string says, "I think I'll have a beer quag fulk boorg jdk^CjfdLk jk3s d#f67howe%^U r89nvy owmc63^Dz x.xvcu"
 * "Please excuse my friend," the second string says, "He isn't null-terminated."
 *
 *-----------------------------------------------------------------------------
 * MirrorController.java
 *-----------------------------------------------------------------------------
 * A panel that controls mirror tilt.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class MirrorController extends JPanel {

    static final int TYPE_RIGHT_MIRROR = 0;     // modifier right mirror
    static final int TYPE_LEFT_MIRROR = 1;      // modifier left mirror

    private static final int STATE_IDLE = 0;    // state - no tilt adjustment
    private static final int STATE_UP = 1;      // state - tilt up
    private static final int STATE_LEFT = 2;    // state - tilt left
    private static final int STATE_DOWN = 3;    // state - tilt down
    private static final int STATE_RIGHT = 4;   // state - tilt right

    private Rectangle hit_box_up;       // hitbox for tilting mirror up
    private Rectangle hit_box_down;     // hitbox for tilting mirror down
    private Rectangle hit_box_left;     // hitbox for tilting mirror left
    private Rectangle hit_box_right;    // hitbox for tilting mirror right

    private int mirror_type;    // the type fo mirror (left/right modifier)
    private int current_state;  // current state (tilt)

    /**
     * Constructor
     * @param mirror_type   type of mirror/modifier
     * @param x x pos
     * @param y y pos
     */
    MirrorController (int mirror_type, int x, int y) {

        Console.printGeneralMessage("Initializing mirror controller for mirror #" + mirror_type);

        setBounds(x, y, 157, 97);

        this.mirror_type = mirror_type;
        this.current_state = STATE_IDLE;

        hit_box_up = new Rectangle(50, 0, 57, 50);
        hit_box_down = new Rectangle(50, getHeight() - 50, 57, 50);

        hit_box_left = new Rectangle(0,20,50,57);
        hit_box_right = new Rectangle(getWidth() - 50, 20,50, 57);

        // if mouse pressed inside hitbox, tilt mirror in the corresponding direction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Point p = e.getPoint();
                if (hit_box_up.contains(p)) {
                    current_state = STATE_UP;
                } else if (hit_box_down.contains(p)) {
                    current_state = STATE_DOWN;
                } else if (hit_box_left.contains(p)) {
                    current_state = STATE_LEFT;
                } else if (hit_box_right.contains(p)) {
                    current_state = STATE_RIGHT;
                } else {
                    current_state = STATE_IDLE;
                }

                RenderingService.invokeRepaint();
            }

            // keep tracking mouse position even when mouse is dragged
            @Override
            public void mouseDragged (MouseEvent e) {
                super.mouseDragged(e);

                Point p = e.getPoint();
                if (hit_box_up.contains(p)) {
                    current_state = STATE_UP;
                } else if (hit_box_down.contains(p)) {
                    current_state = STATE_DOWN;
                } else if (hit_box_left.contains(p)) {
                    current_state = STATE_LEFT;
                } else if (hit_box_right.contains(p)) {
                    current_state = STATE_RIGHT;
                } else {
                    current_state = STATE_IDLE;
                }

                RenderingService.invokeRepaint();
            }

            // return state to idle when mouse released
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                current_state = STATE_IDLE;
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

        // draw mirror graphics based on mirror type and state
        switch (mirror_type) {
            case TYPE_LEFT_MIRROR:
                g2d.drawImage(Resources.control_left_mirror[current_state], 0, 0, getWidth(), getHeight(), null);
                break;
            case TYPE_RIGHT_MIRROR:
                g2d.drawImage(Resources.control_right_mirror[current_state], 0, 0, getWidth(), getHeight(), null);
                break;
        }

    }

}

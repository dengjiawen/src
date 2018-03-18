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
 * Roses are gray,
 * Violets are gray,
 * believe it or not,
 * Arrays start with you
 *
 *-----------------------------------------------------------------------------
 * ACSubPanel.java
 *-----------------------------------------------------------------------------
 * A JPanel controlling the direction of the climate control air flow.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ACSubPanel extends JPanel {

    private Point dragger_position;     // keeps track of the "dragger"'s position on the panel

    /**
     * Constructor
     * @param x initial x pos
     * @param y initial y pos
     */
    ACSubPanel (int x, int y) {

        setBounds(x, y, 236, 175);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);
        setLayout(null);

        Console.printGeneralMessage("Initializing climate control airflow direction module");

        // initialize dragger in the center
        dragger_position = new Point(115, 89);

        // when mouse pressed, move the dragger to the mouse's position
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Console.printGeneralMessage("Dragging events initiated for ACSubPanel " + this.toString());

                dragger_position.x = e.getX() - 36;
                dragger_position.y = e.getY() - 23;

                // if dragger is going out of bounds, keep it within bounds
                if (dragger_position.x < 0) dragger_position.x = 0;
                if (dragger_position.y < 0) dragger_position.y = 0;

                if (dragger_position.x + 72 > 236) dragger_position.x = getWidth() - 75;
                if (dragger_position.y + 45 > 175) dragger_position.y = getHeight() - 45;

                RenderingService.invokeRepaint();
            }
        });

        // when mouse dragged, move dragger to mouse's location
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                dragger_position.x = e.getX() - 36;
                dragger_position.y = e.getY() - 23;

                // if dragger is going out of bounds, keep it within bounds
                if (dragger_position.x < 0) dragger_position.x = 0;
                if (dragger_position.y < 0) dragger_position.y = 0;

                if (dragger_position.x + 72 > 236) dragger_position.x = getWidth() - 75;
                if (dragger_position.y + 45 > 175) dragger_position.y = getHeight() - 45;

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

        // draw out the location of the dragger
        g2d.drawImage(Resources.ac_dragger, dragger_position.x, dragger_position.y, 73, 45, null);

    }

}

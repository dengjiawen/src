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
 * There are two types of people:
 * Type 1:
 * if (condition) {
 *     stuff
 * }
 * Type 2:
 * if (condition)
 * {
 *     stuff
 * }
 *
 *-----------------------------------------------------------------------------
 * MusicPhoneSubPanel.java
 *-----------------------------------------------------------------------------
 * A sub panel of MusicPlayerPanelLG, shows all the music available on a
 * connected device (ie. phone via bluetooth)
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import music.MusicController;
import resources.Constants;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class MusicPhoneSubPanel extends JPanel {

    private ModifiedToggleButton nine;      // the album arts (music are hard coded)
    private ModifiedToggleButton lala;
    private ModifiedToggleButton frozen;

    /**
     * Default Constructor
     */
    MusicPhoneSubPanel () {

        setBounds(0, 175 + 39, 725, 206);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);
        setLayout(null);

        Console.printGeneralMessage("Initializing phone connection, phone connection panel");

        // initialize album art buttons
        // when pressed, change state and tells MusicController to play album
        frozen = new ModifiedToggleButton(Resources.music_lg_frozen, 2, 45, 65, 110, 110);
        frozen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (frozen.getState() == 1) return;

                frozen.forceState(1);

                lala.forceState(0);
                nine.forceState(0);

                MusicController.changeSongList(music.Resources.songlists[0]);
            }
        });

        lala = new ModifiedToggleButton(Resources.music_lg_lala, 2, 147, 65, 110, 110);
        lala.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (lala.getState() == 1) return;

                lala.forceState(1);

                frozen.forceState(0);
                nine.forceState(0);

                MusicController.changeSongList(music.Resources.songlists[2]);
            }
        });

        nine = new ModifiedToggleButton(Resources.music_lg_nine, 2, 249, 65, 110, 110);
        nine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (nine.getState() == 1) return;

                nine.forceState(1);

                frozen.forceState(0);
                lala.forceState(0);

                MusicController.changeSongList(music.Resources.songlists[1]);
            }
        });

        lala.forceState(1); // lala land is the default album

        add(frozen);
        add(lala);
        add(nine);

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

        // color background
        g2d.setColor(Constants.MUSIC_LG_PHONE_BACKGROUND);
        g2d.fillRect(0, 0, getWidth(), 25);

        // draw other assets
        g2d.drawImage(Resources.music_lg_connection, 54, 5, 150, 15, null);
        g2d.drawImage(Resources.music_lg_fav_text, 54, 45, (int)(0.25 * Resources.music_lg_fav_text.getWidth()), (int)(0.25 * Resources.music_lg_fav_text.getHeight()), null);

    }

}

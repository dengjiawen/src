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
 * In order to understand recursion, you must first understand recursion.
 *
 *-----------------------------------------------------------------------------
 * SongListSubPanel.java
 *-----------------------------------------------------------------------------
 * A subpanel of MusicPlayerSM that shows the song name, artist and duration
 * on the side.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import music.Music;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

class SongListSubPanel extends JPanel {

    private Music music;            // target Music object
    private String music_name;      // music name

    private String music_length;    // music duration

    private int padding;    // for UI structure

    SongListSubPanel (int x, int y, Music music) {

        Console.printGeneralMessage("Initializing song list panel for " + music.getName());

        setBounds(x, y, 225, 30);

        // initiate all instance variables
        // if music name is longer than 25 chars, replace name with "..."
        this.music = music;
        if (music.getName().length() > 25) music_name = music.getName().substring(0, 25) + "...";
        else music_name = music.getName();

        // convert seconds to mm:ss format
        music_length = (int)(Math.floor(music.getLength() / 60)) + ":" +
                new DecimalFormat("00").format(music.getLength()%60);

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

        g2d.setColor(Color.white);

        padding = g2d.getFontMetrics(Resources.music_sub_artist_font).stringWidth(music.getArtist() + "  ");

        // draw out all the information Strings
        g2d.setFont(Resources.music_sub_artist_font);
        g2d.drawString(music.getArtist(), 0, 18);

        g2d.setFont(Resources.music_sub_title_font);
        g2d.drawString(music_name, padding, 18);

        g2d.setColor(Color.GRAY);
        g2d.setFont(Resources.music_sub_time_font);
        g2d.drawString(music_length, getWidth() - 20, 18);
    }

}

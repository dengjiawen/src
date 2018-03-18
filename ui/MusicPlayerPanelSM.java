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
 * I've got no jokes for this one.
 *
 *-----------------------------------------------------------------------------
 * MusicPlayerPanelSM.java
 *-----------------------------------------------------------------------------
 * The smaller version of the music player panel (mini player)
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import music.Music;
import music.MusicController;
import music.SongList;
import resources.Constants;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class MusicPlayerPanelSM extends ContainerSM implements NegotiablePanel {

    // various UI constants
    private static int music_track_control_width = (int)(0.25 * Resources.music_rewind[0].getWidth());
    private static int music_track_control_height = (int)(0.25 * Resources.music_rewind[0].getHeight());

    private static int music_control_play_width = (int)(0.25 * Resources.music_control_play[0].getWidth());
    private static int music_control_play_height = (int)(0.25 * Resources.music_control_play[0].getHeight());

    public static MusicPlayerPanelLG panel_lg;  // reference of the larger music panel

    public static ToggleButton invoker;     // reference of the invoker of the panel

    private static SongList active_songlist = music.Resources.songlists[2]; // the songlist/album that is currently active

    private boolean is_active;          // whether the smaller panel is active

    private JLabel song_name;           // JLabel showing the song name
    private JLabel album_artist_name;   // JLabel showing the artist name

    private ToggleButton shuffle;       // shuffle button
    private ToggleButton repeat;        // repeat button

    private Timer progression_bar;      // progression bar animation timer
    private float music_progression;    // float representing % percentage of music played

    private SongListSubPanel song1;     // a SongList subpanel for each song in the album
    private SongListSubPanel song2;
    private SongListSubPanel song3;
    private SongListSubPanel song4;

    private GlowButton pause;       // pause/play button
    private GlowButton rewind;      // rewind button
    private GlowButton forward;     // forward button

    private GlowButton expand;      // expand button to change into MusicPanelLG

    private int mode;               // current window mode

    /**
     * Default Constructor
     */
    MusicPlayerPanelSM () {

        super();

        setLayout(null);
        setBackground(Constants.BACKGROUND_GREY);

        Console.printGeneralMessage("Initializing music panel (small)");

        //initialize mode, instantiate objects and subpanels
        mode = Constants.WindowConstants.STATE_SM;

        song_name = new JLabel();
        song_name.setFont(Resources.music_title_font);
        song_name.setForeground(Color.white);
        song_name.setOpaque(false);
        song_name.setBounds(215, 35, 500, 50);

        album_artist_name = new JLabel();
        album_artist_name.setFont(Resources.music_album_font);
        album_artist_name.setForeground(Color.white);
        album_artist_name.setOpaque(false);
        album_artist_name.setBounds(215, 18, 500, 50);

        song1 = new SongListSubPanel(getWidth() - 250, 40, active_songlist.getMusic(0));
        song2 = new SongListSubPanel(getWidth() - 250, 40 + 30, active_songlist.getMusic(1));
        song3 = new SongListSubPanel(getWidth() - 250, 40 + 60, active_songlist.getMusic(2));
        song4 = new SongListSubPanel(getWidth() - 250, 40 + 90, active_songlist.getMusic(3));

        music_progression = 0f;

        // if paused, stop progression bar, and pause music in MusicController
        pause = new GlowButton(Resources.music_control_play, 190 + 73, 115 - 12,
                music_control_play_width, music_control_play_height);
        pause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (MusicController.isPaused()) {
                    MusicController.resume();
                    pause.changeIcon(Resources.music_control_pause);
                    progression_bar.start();
                } else {
                    MusicController.pause();
                    pause.changeIcon(Resources.music_control_play);
                    progression_bar.stop();
                }
            }
        });

        // rewind and forward all pass on command to MusicController
        rewind = new GlowButton(Resources.music_rewind, 190, 115,
                music_track_control_width, music_track_control_height);
        rewind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MusicController.rewind();

                RenderingService.invokeRepaint();
            }
        });

        forward = new GlowButton(Resources.music_forward, 190 + 120, 115,
                music_track_control_width, music_track_control_height);
        forward.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MusicController.forward();

                RenderingService.invokeRepaint();
            }
        });

        // the progression bar updates music progress
        progression_bar = new Timer((int) Math.floor(1000 / (7250 / (2 * 60 + 7.093))), e -> {
            music_progression += 0.11f;
            if (music_progression == 725f) {
                progression_bar.stop();
            }

            if (is_active) {
                RenderingService.invokeRepaint();
            }
        });

        // shuffle and repeat button all pass on command to MusicController
        shuffle = new ToggleButton(Resources.music_shuffle, 2, 206, 78, (int) (0.8 * 93), (int) (0.8 * 24));
        shuffle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MusicController.setShuffle();
            }
        });

        repeat = new ToggleButton(Resources.music_repeat, 3, 280, 78, (int) (0.8 * 93), (int) (0.8 * 24));
        repeat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MusicController.setRepeat();
            }
        });

        // expand button requests transition into panel_lg
        expand = new GlowButton(new BufferedImage[]{active_songlist.getButton(), Resources.music_expand}, 493, 10, 212, 26);
        expand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_LG, panel_lg);
            }
        });

        add(expand);

        add(pause);
        add(rewind);
        add(forward);

        add(song_name);
        add(album_artist_name);

        add(shuffle);
        add(repeat);

        add(song1);
        add(song2);
        add(song3);
        add(song4);

        Console.printGeneralMessage("Linking music panel (small) to MusicController and CoreControlBarPanel");
        MusicController.panel = this;
        CoreControlBarPanel.music_panel = this;
        Console.printGeneralMessage("Linkage successful.");

        reset(MusicController.sequence[0]);

    }

    /**
     * Resets the panel for a new song.
     * @param music     the target music Object
     */
    public void reset (Music music) {

        Console.printGeneralMessage("Music transition event to " + music.getName() + " received.");

        String song_name = music.getName();
        if (music.getName().length() > 20) {
            song_name = music.getName().substring(0, 20);
            song_name += "...";
        }

        this.song_name.setText(song_name);
        album_artist_name.setText(active_songlist.getName());

        progression_bar.stop();
        progression_bar.setDelay((int) Math.floor(1000 / (7250 / (music.getLength()))));
        music_progression = 0f;
        if (!MusicController.isPaused()) {
            progression_bar.start();
        } else {
            progression_bar.stop();
            pause.changeIcon(Resources.music_control_play);
        }

        RenderingService.invokeRepaint();
    }

    /**
     * Disable shuffle icon.
     */
    public void disableShuffle () {
        shuffle.forceState(0);
    }

    /**
     * Disable repeat icon.
     */
    public void disableRepeat1 () {
        repeat.forceState(0);
    }

    /**
     * Transition between large and small mode.
     * Hide expand button when in large mode.
     * @param mode_modifier the target mode
     */
    void setMode (int mode_modifier) {
        mode = mode_modifier;

        if (mode == Constants.WindowConstants.STATE_LG) {
            expand.setVisible(false);
        } else {
            expand.setVisible(true);
        }
    }

    /**
     * Resets the music panel for a new song list.
     * @param list  the target song list
     */
    public void changeSongList (SongList list) {
        active_songlist = list;

        Console.printGeneralMessage("Music transition event to " + list.getName() + " received.");

        // remove existing song subpanels
        remove(song1);
        remove(song2);
        remove(song3);
        remove(song4);

        // make new sub panels based on new songs and add them
        song1 = new SongListSubPanel(getWidth() - 250, 40, active_songlist.getMusic(0));
        song2 = new SongListSubPanel(getWidth() - 250, 40 + 30, active_songlist.getMusic(1));
        song3 = new SongListSubPanel(getWidth() - 250, 40 + 60, active_songlist.getMusic(2));
        song4 = new SongListSubPanel(getWidth() - 250, 40 + 90, active_songlist.getMusic(3));

        add(song1);
        add(song2);
        add(song3);
        add(song4);

        // change all assets to the corresponding color
        expand.changeIcon(new BufferedImage[]{active_songlist.getButton(), Resources.music_expand});

        shuffle.forceState(0);
        repeat.forceState(0);

        pause.changeIcon(Resources.music_control_pause);

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

        if (mode == Constants.WindowConstants.STATE_SM) {
            Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS);
            g2d.clip(clip);
        }

        // draw backdrop and progression bar graphics
        g2d.drawImage(active_songlist.getBackdrop(), 0, 0, ContainerSM.panel_width, ContainerSM.panel_height, null);

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.MUSIC_PROGRESS_STOP_0, music_progression, 0f, Constants.MUSIC_PROGRESS_STOP_1);

        g2d.setPaint(primary);
        g2d.fill(new Rectangle2D.Float(0, getHeight() - 4, music_progression, 4));

        // highlight subpanel depending on which sone is active
        switch (active_songlist.getIndex(MusicController.sequence[MusicController.current_index])) {
            case 0:
                g2d.drawImage(active_songlist.getHighlight(), getWidth() - 300, 40, 300, 30, null);
                break;
            case 1:
                g2d.drawImage(active_songlist.getHighlight(), getWidth() - 300, 40 + 30, 300, 30, null);
                break;
            case 2:
                g2d.drawImage(active_songlist.getHighlight(), getWidth() - 300, 40 + 60, 300, 30, null);
                break;
            case 3:
                g2d.drawImage(active_songlist.getHighlight(), getWidth() - 300, 40 + 90, 300, 30, null);
                break;
        }
    }

    // Overriden method for NegotiablePanel interface
    @Override
    public void setActive(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public void updateInvoker() {
        invoker.forceState(0);
    }
}

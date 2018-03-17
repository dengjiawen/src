package ui;

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

/**
 * Created by freddeng on 2018-03-05.
 */

public class MusicPlayerPanelSM extends ContainerSM implements NegotiablePanel {

    private static int music_track_control_width = (int)(0.25 * Resources.music_rewind[0].getWidth());
    private static int music_track_control_height = (int)(0.25 * Resources.music_rewind[0].getHeight());

    private static int music_control_play_width = (int)(0.25 * Resources.music_control_play[0].getWidth());
    private static int music_control_play_height = (int)(0.25 * Resources.music_control_play[0].getHeight());

    public static MusicPlayerPanelLG panel_lg;

    public static ToggleButton invoker;

    private boolean is_active;

    private JLabel song_name;
    private JLabel album_artist_name;

    private ToggleButton shuffle;
    private ToggleButton repeat;

    private Timer progression_bar;
    private float music_progression;

    private SongListSubPanel song1;
    private SongListSubPanel song2;
    private SongListSubPanel song3;
    private SongListSubPanel song4;

    private GlowButton pause;
    private GlowButton rewind;
    private GlowButton forward;

    private GlowButton expand;

    private int mode;

    private static SongList active_songlist = music.Resources.songlists[2];

    public MusicPlayerPanelSM () {

        super();

        setLayout(null);
        setBackground(Constants.BACKGROUND_GREY);

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

        progression_bar = new Timer((int) Math.floor(1000 / (7250 / (2 * 60 + 7.093))), e -> {
            music_progression += 0.11f;
            if (music_progression == 725f) {
                progression_bar.stop();
            }

            if (is_active) {
                RenderingService.invokeRepaint();
            }
        });

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

        MusicController.panel = this;
        CoreControlBarPanel.music_panel = this;

        reset(MusicController.sequence[0]);

    }

    public void reset (Music music) {

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
        }

        RenderingService.invokeRepaint();
    }

    public void disableShuffle () {
        shuffle.forceState(0);
    }

    public void disableRepeat1 () {
        repeat.forceState(0);
    }

    public void setMode (int mode_modifier) {
        mode = mode_modifier;

        if (mode == Constants.WindowConstants.STATE_LG) {
            expand.setVisible(false);
        } else {
            expand.setVisible(true);
        }
    }

    public void changeSongList (SongList list) {
        active_songlist = list;

        remove(song1);
        remove(song2);
        remove(song3);
        remove(song4);

        song1 = new SongListSubPanel(getWidth() - 250, 40, active_songlist.getMusic(0));
        song2 = new SongListSubPanel(getWidth() - 250, 40 + 30, active_songlist.getMusic(1));
        song3 = new SongListSubPanel(getWidth() - 250, 40 + 60, active_songlist.getMusic(2));
        song4 = new SongListSubPanel(getWidth() - 250, 40 + 90, active_songlist.getMusic(3));

        add(song1);
        add(song2);
        add(song3);
        add(song4);

        expand.changeIcon(new BufferedImage[]{active_songlist.getButton(), Resources.music_expand});

        shuffle.forceState(0);
        repeat.forceState(0);

        pause.changeIcon(Resources.music_control_pause);

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (mode == Constants.WindowConstants.STATE_SM) {
            Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS);
            g2d.clip(clip);
        }

        g2d.drawImage(active_songlist.getBackdrop(), 0, 0, ContainerSM.panel_width, ContainerSM.panel_height, null);

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.MUSIC_PROGRESS_STOP_0, music_progression, 0f, Constants.MUSIC_PROGRESS_STOP_1);

        g2d.setPaint(primary);
        g2d.fill(new Rectangle2D.Float(0, getHeight() - 4, music_progression, 4));

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

    @Override
    public void setActive(boolean is_active) {
        this.is_active = is_active;
    }

    @Override
    public void updateInvoker() {
        invoker.forceState(0);
    }
}

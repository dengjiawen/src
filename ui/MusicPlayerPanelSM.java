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

/**
 * Created by freddeng on 2018-03-05.
 */

public class MusicPlayerPanelSM extends ContainerSM {

    private static int music_track_control_width = (int)(0.25 * Resources.music_rewind[0].getWidth());
    private static int music_track_control_height = (int)(0.25 * Resources.music_rewind[0].getHeight());

    private static int music_control_play_width = (int)(0.25 * Resources.music_control_play[0].getWidth());
    private static int music_control_play_height = (int)(0.25 * Resources.music_control_play[0].getHeight());

    private JLabel song_name;
    private JLabel album_artist_name;

    private ToggleButton shuffle;
    private ToggleButton repeat;

    public Timer progression_bar;
    public float music_progression;

    private SongListSubPanel song1;
    private SongListSubPanel song2;
    private SongListSubPanel song3;
    private SongListSubPanel song4;

    private GlowButton pause;
    private GlowButton rewind;
    private GlowButton forward;

    public static SongList active_songlist = music.Resources.songlists[0];

    public MusicPlayerPanelSM () {

        super();

        setLayout(null);
        setBackground(Constants.background_grey);

        song_name = new JLabel();
        song_name.setText("Love Is an Open Door");
        song_name.setFont(Resources.music_title_font);
        song_name.setForeground(Color.white);
        song_name.setOpaque(false);
        song_name.setBounds(215, 25, 500, 50);

        album_artist_name = new JLabel();
        album_artist_name.setText("Frozen OST");
        album_artist_name.setFont(Resources.music_album_font);
        album_artist_name.setForeground(Color.white);
        album_artist_name.setOpaque(false);
        album_artist_name.setBounds(215, 8, 500, 50);

        song1 = new SongListSubPanel(getWidth() - 250, 40, music.Resources.music_with_metadata[0]);
        song2 = new SongListSubPanel(getWidth() - 250, 40 + 30, music.Resources.music_with_metadata[1]);
        song3 = new SongListSubPanel(getWidth() - 250, 40 + 60, music.Resources.music_with_metadata[2]);
        song4 = new SongListSubPanel(getWidth() - 250, 40 + 90, music.Resources.music_with_metadata[3]);

        music_progression = 0f;

        pause = new GlowButton(Resources.music_control_play, 190 + 73, 110 - 12,
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

        rewind = new GlowButton(Resources.music_rewind, 190, 110,
                music_track_control_width, music_track_control_height);
        rewind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MusicController.rewind();

                RenderingService.invokeRepaint();
            }
        });

        forward = new GlowButton(Resources.music_forward, 190 + 120, 110,
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
            RenderingService.invokeRepaint();
        });

        shuffle = new ToggleButton(Resources.music_shuffle, 2, 206, 68, (int) (0.8 * 93), (int) (0.8 * 24));
        shuffle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MusicController.setShuffle();
            }
        });

        repeat = new ToggleButton(Resources.music_repeat, 3, 280, 68, (int) (0.8 * 93), (int) (0.8 * 24));
        repeat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MusicController.setRepeat();
            }
        });

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

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.roundness, Constants.roundness);

        g2d.clip(clip);

        g2d.drawImage(active_songlist.getBackdrop(), 0, 0, ContainerSM.panel_width, ContainerSM.panel_height, null);

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.music_progress_stop_0, music_progression, 0f, Constants.music_progress_stop_1);

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

}

package ui;

import javafx.scene.effect.BlendMode;
import music.MusicController;
import music.SongList;
import resources.Constants;
import resources.Resources;
import sound.TinySound;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-05.
 */

public class MusicPlayerPanelSM extends ContainerSM {

    private JLabel song_name;
    private JLabel album_artist_name;

    private AudioControlPanel audio_control;

    private ToggleButton shuffle;
    private ToggleButton repeat;

    public static Timer progression_bar;
    public static float music_progression;

    private JLabel duration_1;
    private JLabel duration_2;
    private JLabel duration_3;

    public static SongList active_songlist;

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

        music_progression = 0f;

        progression_bar = new Timer ((int)Math.floor(1000/(7250/(2 * 60 + 7.093))), e -> {
            music_progression += 0.11f;
            if (music_progression == 725f) {
                progression_bar.stop();
            }
            RenderingService.invokeRepaint();
        });
        progression_bar.start();

        shuffle = new ToggleButton(Resources.frozen_shuffle, 2, 206, 68, (int)(0.8 * 93), (int)(0.8 * 24));
        repeat = new ToggleButton(Resources.frozen_repeat, 3, 280, 68, (int)(0.8 * 93), (int)(0.8 * 24));

        audio_control = new AudioControlPanel();

        add(song_name);
        add(album_artist_name);
        add(audio_control);

        add(shuffle);
        add(repeat);

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.roundness, Constants.roundness);

        g2d.clip(clip);

        g2d.drawImage(Resources.frozen_music_backdrop_SM, 0, 0, ContainerSM.panel_width, ContainerSM.panel_height, null);

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.music_progress_stop_0, music_progression, 0f, Constants.music_progress_stop_1);

        g2d.setPaint(primary);
        g2d.fill(new Rectangle2D.Float(0, getHeight() - 4, music_progression, 4));

        g2d.drawImage(Resources.music_highlight, getWidth() - 225, 47, 225, 30, null);
    }

}

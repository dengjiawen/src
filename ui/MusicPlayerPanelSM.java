package ui;

import music.MusicController;
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

    public static Timer progression_bar;

    private float music_progression;

    public MusicPlayerPanelSM () {

        super();

        setLayout(null);
        setBackground(Constants.background_grey);

        song_name = new JLabel();
        song_name.setText("Love Is an Open Door");
        song_name.setFont(Resources.music_title_font);
        song_name.setForeground(Color.white);
        song_name.setOpaque(false);
        song_name.setBounds(230, 10, 500, 100);

        album_artist_name = new JLabel();
        album_artist_name.setText("Frozen | Kristen Bell");
        album_artist_name.setFont(Resources.music_album_artist_font);
        album_artist_name.setForeground(Color.white);
        album_artist_name.setOpaque(false);
        album_artist_name.setBounds(230, 30, 500, 100);

        music_progression = 0f;

        TinySound.init();

        progression_bar = new Timer ((int)Math.floor(1000/(7250/(2 * 60 + 7.093))), e -> {
            music_progression += 0.115f;
            if (music_progression == 725f) {
                progression_bar.stop();
            }
            RenderingService.invokeRepaint();
        });
        progression_bar.start();

        MusicController.play(music.Resources.love_is_an_open_door);

        audio_control = new AudioControlPanel();

        add(song_name);
        add(album_artist_name);
        add(audio_control);

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.roundness, Constants.roundness);

        g2d.clip(clip);

        g2d.drawImage(Resources.frozen_music_backdrop_SM, 0, 0, ContainerSM.panel_width, ContainerSM.panel_height, null);

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.music_progress_stop_0, 725f, 0f, Constants.music_progress_stop_1);

        g2d.setPaint(primary);
        g2d.fill(new Rectangle2D.Float(0, 187, music_progression, 8));
    }

}

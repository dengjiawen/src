package ui;

import music.MusicController;
import resources.Resources;
import sound.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

/**
 * Created by freddeng on 2018-03-05.
 */
public class AudioControlPanel extends JPanel {

    private static int music_track_control_width = (int)(0.25 * Resources.music_rewind[0].getWidth());
    private static int music_track_control_height = (int)(0.25 * Resources.music_rewind[0].getHeight());

    private static int music_control_play_width = (int)(0.25 * Resources.music_control_play[0].getWidth());
    private static int music_control_play_height = (int)(0.25 * Resources.music_control_play[0].getHeight());

    private GlowButton pause;
    private GlowButton rewind;
    private GlowButton forward;

    public AudioControlPanel () {

        setBounds(194, 120, 500, 100);
        setOpaque(false);
        setLayout(null);

        pause = new GlowButton(Resources.music_control_pause, 73, -12,
                music_control_play_width, music_control_play_height);
        pause.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (MusicController.isPaused()) {
                    MusicController.resume();
                    pause.changeIcon(Resources.music_control_pause);
                    MusicPlayerPanelSM.progression_bar.start();
                } else {
                    MusicController.pause();
                    pause.changeIcon(Resources.music_control_play);
                    MusicPlayerPanelSM.progression_bar.stop();
                }
            }
        });

        rewind = new GlowButton(Resources.music_rewind, 0, 0,
                music_track_control_width, music_track_control_height);
        rewind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                MusicController.rewind();
                MusicPlayerPanelSM.music_progression = 0;

                RenderingService.invokeRepaint();
            }
        });

        forward = new GlowButton(Resources.music_forward, 120, 0,
                music_track_control_width, music_track_control_height);

        add(pause);
        add(rewind);
        add(forward);

    }

}

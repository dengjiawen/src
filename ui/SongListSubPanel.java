package ui;

import music.Music;
import resources.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * Created by freddeng on 2018-03-07.
 */
public class SongListSubPanel extends JPanel {

    Music music;

    public SongListSubPanel (int x, int y, Music music) {

        setBounds(x, y, 225, 30);

        this.music = music;
    }

    protected void paintComponent (Graphics g) {

        g.setColor(Color.white);

        g.setFont(Resources.music_sub_artist_font);
        g.drawString(music.getArtist(), 0, 18);

        String music_name = "";
        if (music.getName().length() > 25) music_name = music.getName().substring(0, 25) + "...";
        else music_name = music.getName();

        int padding = g.getFontMetrics(Resources.music_sub_artist_font).stringWidth(music.getArtist() + "  ");
        g.setFont(Resources.music_sub_title_font);
        g.drawString(music_name, padding, 18);
    }

}

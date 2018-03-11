package ui;

import music.Music;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by freddeng on 2018-03-07.
 */
public class SongListSubPanel extends JPanel {

    Music music;
    String music_name;

    String music_length;

    int padding;

    public SongListSubPanel (int x, int y, Music music) {

        setBounds(x, y, 225, 30);

        this.music = music;
        if (music.getName().length() > 25) music_name = music.getName().substring(0, 25) + "...";
        else music_name = music.getName();

        music_length = (int)(Math.floor(music.getLength() / 60)) + ":" +
                new DecimalFormat("00").format(music.getLength()%60);

    }

    protected void paintComponent (Graphics g) {

        g.setColor(Color.white);

        padding = g.getFontMetrics(Resources.music_sub_artist_font).stringWidth(music.getArtist() + "  ");

        g.setFont(Resources.music_sub_artist_font);
        g.drawString(music.getArtist(), 0, 18);

        g.setFont(Resources.music_sub_title_font);
        g.drawString(music_name, padding, 18);

        g.setColor(Color.GRAY);
        g.setFont(Resources.music_sub_time_font);
        g.drawString(music_length, getWidth() - 20, 18);
    }

}

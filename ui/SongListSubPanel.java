package ui;

import music.Music;

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
        
    }

}

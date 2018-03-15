package ui;

import music.MusicController;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by freddeng on 2018-03-13.
 */
public class MusicPhoneSubPanel extends JPanel {

    ModifiedToggleButton nine;
    ModifiedToggleButton lala;
    ModifiedToggleButton frozen;

    public MusicPhoneSubPanel () {

        setBounds(0, 175 + 39, 725, 206);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);
        setLayout(null);

        frozen = new ModifiedToggleButton(Resources.music_lg_frozen, 2, 45, 65, 110, 110);
        frozen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (frozen.getState() == 1) return;

                frozen.forceState(1);

                lala.forceState(0);
                nine.forceState(0);

                MusicController.changeSongList(music.Resources.songlists[0]);
            }
        });

        lala = new ModifiedToggleButton(Resources.music_lg_lala, 2, 147, 65, 110, 110);
        lala.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (lala.getState() == 1) return;

                lala.forceState(1);

                frozen.forceState(0);
                nine.forceState(0);

                MusicController.changeSongList(music.Resources.songlists[2]);
            }
        });

        nine = new ModifiedToggleButton(Resources.music_lg_nine, 2, 249, 65, 110, 110);
        nine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (nine.getState() == 1) return;

                nine.forceState(1);

                frozen.forceState(0);
                lala.forceState(0);

                MusicController.changeSongList(music.Resources.songlists[1]);
            }
        });

        lala.forceState(1);

        add(frozen);
        add(lala);
        add(nine);

    }

    protected void paintComponent (Graphics g) {

        g.setColor(Constants.MUSIC_LG_PHONE_BACKGROUND);
        g.fillRect(0, 0, getWidth(), 25);

        g.drawImage(Resources.music_lg_connection, 54, 5, 150, 15, null);
        g.drawImage(Resources.music_lg_fav_text, 54, 45, (int)(0.25 * Resources.music_lg_fav_text.getWidth()), (int)(0.25 * Resources.music_lg_fav_text.getHeight()), null);

    }

}

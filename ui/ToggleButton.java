package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-05.
 */
public class ToggleButton extends JPanel {

    protected BufferedImage[] states;
    protected BufferedImage active_state;

    protected int active_state_num;

    public ToggleButton () {

    }

    public ToggleButton(BufferedImage[] icon, int num_states, int x, int y, int width, int height, boolean manual_toggle) {

        setBounds(x, y, width, height);

        states = icon;
        active_state_num = 0;

        active_state = icon[active_state_num];

    }

    public ToggleButton(BufferedImage[] icon, int num_states, int x, int y, int width, int height) {

        setBounds(x, y, width, height);

        states = icon;
        active_state_num = 0;

        active_state = icon[active_state_num];

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (++active_state_num >= num_states) active_state_num = 0;
                active_state = states[active_state_num];

                RenderingService.invokeRepaint();
            }
        });

    }

    public void forceState (int state) {
        if (active_state_num == state) return;
        active_state_num = state;
        active_state = states[active_state_num];
        RenderingService.invokeRepaint();
    }

    public int getState () {
        return active_state_num;
    }

    public void changeIcon (BufferedImage[] icon) {
        states = icon;
        active_state = states[active_state_num];

        RenderingService.invokeRepaint();
    }

    protected void paintComponent (Graphics g) {
        g.drawImage(active_state, 0, 0, getWidth(), getHeight(), null);
    }

}

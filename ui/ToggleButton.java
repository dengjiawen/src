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

    private BufferedImage[] states;
    private BufferedImage active_state;

    private int active_state_num;

    public ToggleButton(BufferedImage[] icon, int num_states, int x, int y, int width, int height) {

        setBounds(x, y, width, height);

        states = icon;
        active_state_num = 0;

        active_state = icon[active_state_num];

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (++active_state_num >= num_states) active_state_num = 0;
                active_state = states[active_state_num];

                RenderingService.invokeRepaint();
            }
        });

    }

    public void forceState (int state) {
        active_state_num = state;
        active_state = states[active_state_num];
        RenderingService.invokeRepaint();
    }

    public void changeIcon (BufferedImage[] icon) {
        states = icon;
        active_state = icon[0];

        RenderingService.invokeRepaint();
    }

    protected void paintComponent (Graphics g) {
        g.drawImage(active_state, 0, 0, getWidth(), getHeight(), null);
    }

}

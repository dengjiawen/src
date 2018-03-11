package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-05.
 */
public class GlowButton extends JPanel {

    protected BufferedImage[] states;
    protected BufferedImage active_state;

    public GlowButton () {

    }

    public GlowButton (BufferedImage[] icon, int x, int y, int width, int height) {

        setBounds(x, y, width, height);

        states = icon;
        active_state = states[0];

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                active_state = states[1];
                RenderingService.invokeRepaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                active_state = states[0];
                RenderingService.invokeRepaint();
            }
        });

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

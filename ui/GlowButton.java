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

    protected boolean is_disabled;

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
                if (is_disabled) return;
                active_state = states[1];
                RenderingService.invokeRepaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (is_disabled) return;
                active_state = states[0];
                RenderingService.invokeRepaint();
            }
        });

    }

    public void changeIcon (BufferedImage[] icon) {
        states = icon;
        active_state = icon[0];

        if (is_disabled) active_state = icon[2];

        RenderingService.invokeRepaint();
    }

    protected void paintComponent (Graphics g) {
        g.drawImage(active_state, 0, 0, getWidth(), getHeight(), null);
    }

    public void setDisable (boolean disable) {
        is_disabled = disable;

        changeIcon(states);
    }

    public boolean isDisabled () {
        return is_disabled;
    }

}

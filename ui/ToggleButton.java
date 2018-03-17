package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-05.
 */
public class ToggleButton extends JPanel {

    private BufferedImage[] states;
    private BufferedImage active_state;

    private int active_state_num;

    private boolean is_disabled;

    ToggleButton() {

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

                if (is_disabled) return;
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

        if (is_disabled) active_state = states[2];

        RenderingService.invokeRepaint();
    }

    protected void paintComponent (Graphics g) {
        g.drawImage(active_state, 0, 0, getWidth(), getHeight(), null);
    }

    public void setDisable (boolean disable) {
        is_disabled = disable;

        if (is_disabled) active_state_num = 0;

        changeIcon(states);

        RenderingService.invokeRepaint();
    }

    public boolean isDisabled () {
        return is_disabled;
    }

    public void click () {

        MouseEvent artificial_mouseevent = new MouseEvent(this, MouseEvent.MOUSE_RELEASED,
                System.currentTimeMillis() + 10, MouseEvent.NOBUTTON, 0, 0, 0, false);

        for (MouseListener listener : getMouseListeners()) {
            listener.mouseReleased(artificial_mouseevent);
        }
    }

}

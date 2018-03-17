package ui;

import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-12.
 */
class StateButton extends ToggleButton {

    public StateButton (BufferedImage[] icon, int num_states, int x, int y, int width, int height) {
        super(icon, num_states, x, y, width, height);

        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
    }

}

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
public class ModifiedToggleButton extends ToggleButton {

    public ModifiedToggleButton(BufferedImage[] icon, int num_states, int x, int y, int width, int height) {

        super(icon, num_states, x, y, width, height);

        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }

    }

}

package ui;

import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-07.
 */
public class TurningSignal extends JPanel {

    public static final byte LEFT_SIGNAL = 0;
    public static final byte RIGHT_SIGNAL = 1;

    private BufferedImage[] states;
    private BufferedImage current_state;

    private Timer timing_controller;

    public TurningSignal (byte signal_type) {

        setBounds(100,100,200,200);

        switch (signal_type) {
            case LEFT_SIGNAL:
                states = Resources.signal_left;
                break;
            case RIGHT_SIGNAL:
                states = Resources.signal_right;
                break;
        }

        current_state = states[0];

        timing_controller = new Timer(500, e -> {

        });

    }

    public void paintComponents (Graphics g) {
        g.drawImage(current_state, 0, 0, 100, 100, null);
    }

}

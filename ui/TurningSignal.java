package ui;

import resources.Resources;
import sound.SoundService;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-07.
 */
public class TurningSignal extends JPanel {

    public static TurningSignal left;
    public static TurningSignal right;

    public static final byte LEFT_SIGNAL = 0;
    public static final byte RIGHT_SIGNAL = 1;

    private BufferedImage[] states;
    private BufferedImage current_state;

    private Timer timing_controller;
    private int signal_type;

    private boolean activated;
    private boolean emergency;

    public TurningSignal (byte signal_type) {

        setBounds(0,0,300,300);

        switch (signal_type) {
            case LEFT_SIGNAL:
                states = Resources.signal_left;
                left = this;
                break;
            case RIGHT_SIGNAL:
                states = Resources.signal_right;
                right = this;
                break;
        }

        this.signal_type = signal_type;

        current_state = states[0];

        setSize((int)(0.5 * current_state.getWidth()), (int)(0.5 * current_state.getHeight()));

        timing_controller = new Timer(295, e -> {
            current_state = (current_state == states[0]) ? states[1] : states[0];
            RenderingService.invokeRepaint();
        });

    }

    protected void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(current_state, 0, 0, (int)(0.4 * current_state.getWidth()), (int)(0.4 * current_state.getHeight()), this);
    }

    public boolean isactivated () {
        return activated;
    }

    public void activate (boolean doActivate) {
        if (emergency) {
            activated = true;
            return;
        }
        if (doActivate) {
            timing_controller.restart();
            activated = true;

            if (this.signal_type == RIGHT_SIGNAL) {
                left.activate(false);
            } else {
                right.activate(false);
            }

            SoundService.setSignalSound(true);
        } else {
            SoundService.setSignalSound(false);
            timing_controller.stop();
            activated = false;
            current_state = states[0];

            RenderingService.invokeRepaint();
        }
    }

    public void activate () {
        if (activated) {
            activate(false);
        } else {
            activate(true);
        }
    }

    public void emergency(boolean isEmergency) {
        if (isEmergency) {
            timing_controller.restart();
            SoundService.setSignalSound(true);
            emergency = true;
        } else {
            timing_controller.stop();
            SoundService.setSignalSound(false);
            activate(activated);
            emergency = false;
        }
    }

    public boolean isEmergency () {
        return emergency;
    }


}

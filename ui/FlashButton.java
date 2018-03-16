package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

/**
 * Created by freddeng on 2018-03-10.
 */
public class FlashButton extends GlowButton {

    private Timer flash_timer;

    private boolean isGlowing;
    private boolean isActivated;

    public FlashButton (BufferedImage[] icon, int x, int y, int width, int height, int flash_interval) {

        super(icon, x, y, width, height);

        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }

        this.flash_timer = new Timer(flash_interval, e -> {
            active_state = (isGlowing) ? states[0] : states[1];
            isGlowing = !isGlowing;

            RenderingService.invokeRepaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (isActivated) {
                    flash_timer.stop();
                    isActivated = false;
                    isGlowing = false;
                    active_state = states[0];
                } else {
                    flash_timer.start();
                    isActivated = true;
                }

            }
        });

    }

    public void click () {

        MouseEvent artificial_mouseevent = new MouseEvent(this, MouseEvent.MOUSE_RELEASED,
                System.currentTimeMillis() + 10, MouseEvent.NOBUTTON, 0, 0, 0, false);

        for (MouseListener listener : getMouseListeners()) {
            listener.mouseReleased(artificial_mouseevent);
        }
    }

}

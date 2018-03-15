package ui;

import information.InformationService;
import resources.Constants;
import resources.Resources;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by freddeng on 2018-03-13.
 */
public class ModernIncrementalSlider extends ModernSlider {

    private static final int X_POS_1 = 2;
    private static final int X_POS_2 = 69;
    private static final int X_POS_3 = 136;

    boolean sliding;

    public ModernIncrementalSlider (int x, int y) {
        super(x, y, Resources.control_mirror_slider, Resources.control_mirror);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                sliding = true;

                slider_location_x = e.getX() - 36;
                if (slider_location_x > getWidth() - 72 - 2) {
                    slider_location_x = getWidth() - 72 - 2;
                } else if (slider_location_x < 2) {
                    slider_location_x = 2;
                }

                if (slider_location_x - X_POS_1 >= 34 && slider_location_x - X_POS_1 < 103) current_state = 1;
                else if (slider_location_x - X_POS_1 <= 34) current_state = 0;
                else if (slider_location_x - X_POS_1 >= 103) current_state = 2;

                RenderingService.invokeRepaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                sliding = false;

                if (slider_location_x - X_POS_1 >= 34 && slider_location_x - X_POS_1 < 103) {
                    slider_location_x = X_POS_2;
                    InformationService.mirror_state = Constants.MIRROR_EXTENDED;
                } else if (slider_location_x - X_POS_1 <= 34) {
                    slider_location_x = X_POS_1;
                    InformationService.mirror_state = Constants.MIRROR_RETRACTED;
                } else if (slider_location_x - X_POS_1 >= 103) {
                    slider_location_x = X_POS_3;
                    InformationService.mirror_state = Constants.MIRROR_AUTO;
                }

                RenderingService.invokeRepaint();

            }
        });
    }

    public void changeState (boolean mirror_extended) {
        if (sliding) return;
        if (mirror_extended) {
            slider_location_x = X_POS_2;
            current_state = 1;
        }
        else {
            slider_location_x = X_POS_1;
            current_state = 0;
        }
    }

    public int getState () {
        return current_state;
    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(slider_background, 0, 0, getWidth(), getHeight(), null);

        g2d.drawImage(slider_states[current_state], slider_location_x, 2, 72, 29, null);
    }

}

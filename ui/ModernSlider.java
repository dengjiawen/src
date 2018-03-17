package ui;

import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-13.
 */
class ModernSlider extends JPanel {

    BufferedImage slider_background;

    BufferedImage[] slider_states;

    int current_state;
    int slider_location_x;

    private String slider_percentage;

    ModernSlider() {

    }

    public ModernSlider (int x, int y) {

        setBounds(x, y, 210, 33);

        this.current_state = 0;
        this.slider_location_x = 2;

        this.slider_background = Resources.control_sunroof_slider;
        this.slider_states = Resources.control_sunroof;

        this.slider_percentage = "0%";

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                slider_location_x = e.getX() - 36;
                if (slider_location_x > getWidth() - 72 - 2) {
                    slider_location_x = getWidth() - 72 - 2;
                } else if (slider_location_x < 2) {
                    slider_location_x = 2;
                }

                if (slider_location_x > 2) {
                    current_state = 1;
                } else {
                    current_state = 0;
                }

                RenderingService.invokeRepaint();
            }
        });

    }

    ModernSlider(int x, int y, BufferedImage slider_background, BufferedImage[] slider_states) {

        setBounds(x, y, 210, 33);

        this.current_state = 0;
        this.slider_location_x = 2;

        this.slider_background = slider_background;
        this.slider_states = slider_states;

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(slider_background, 0, 0, getWidth(), getHeight(), null);

        slider_percentage = (int)(((slider_location_x - 2f)/(206f - 72f)) * 100f) + "%";
        g2d.setFont(Resources.control_sunroof_percentage_font);
        g2d.setColor(Constants.CONTROL_INTERFACE_INACTIVE);
        g2d.drawString(slider_percentage, 10, 20);

        g2d.drawImage(slider_states[current_state], slider_location_x, 2, 72, 29, null);
    }

}

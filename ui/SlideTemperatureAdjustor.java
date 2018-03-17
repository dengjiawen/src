package ui;

import information.InformationService;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Created by freddeng on 2018-03-12.
 */
class SlideTemperatureAdjustor extends JPanel {

    private MouseEvent initial_event = null;

    private Timer increase_timer;
    private Timer decrease_timer;

    private InformationService.ImmutableInt target_temp;

    public SlideTemperatureAdjustor(int x, int y, InformationService.ImmutableInt modifier) {

        setBounds(x, y, 50, 75);

        target_temp = modifier;

        increase_timer = new Timer(250, e -> {

            if (target_temp.getValue() < 31) {
                target_temp.change(+1);
            }

            RenderingService.invokeRepaint();
        });

        decrease_timer = new Timer(250, e -> {

            if (target_temp.getValue() > -1) {
                target_temp.change(-1);
            }
            RenderingService.invokeRepaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                initial_event = e;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                initial_event = null;

                increase_timer.stop();
                decrease_timer.stop();

                RenderingService.invokeRepaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (initial_event == null) return;

                int difference = e.getY() - initial_event.getY();

                if (difference > 8) {

                    CoreControlBarPanel.ac_panel.forcePowerOn();

                    int delay = 4000/difference;
                    if (delay < 150) {
                        delay = 150;
                    }

                    decrease_timer.setDelay(delay);
                    if (increase_timer.isRunning()){
                        increase_timer.stop();
                    }
                    if (!decrease_timer.isRunning()){
                        decrease_timer.start();
                    }
                } else if (difference < -8) {

                    CoreControlBarPanel.ac_panel.forcePowerOn();

                    int delay = 4000/-difference;
                    if (delay < 150) {
                        delay = 150;
                    }

                    increase_timer.setDelay(delay);
                    if (decrease_timer.isRunning()){
                        decrease_timer.stop();
                    }
                    if (!increase_timer.isRunning()){
                        increase_timer.start();
                    }
                } else {
                    if (increase_timer.isRunning()) increase_timer.stop();
                    if (decrease_timer.isRunning()) decrease_timer.stop();
                }

                RenderingService.invokeRepaint();
            }
        });

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (increase_timer.isRunning()) {
            g2d.drawImage(Resources.core_up_temp_arrow[1], 0, 0, 49, 20, null);
        } else {
            g2d.drawImage(Resources.core_up_temp_arrow[0], 0, 0, 49, 20, null);
        }

        if (decrease_timer.isRunning()) {
            g2d.drawImage(Resources.core_down_temp_arrow[1], 0, getHeight() - 20, 49, 20, null);
        } else {
            g2d.drawImage(Resources.core_down_temp_arrow[0], 0, getHeight() - 20, 49, 20, null);
        }
    }

}

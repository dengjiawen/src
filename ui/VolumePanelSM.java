package ui;

import com.sun.java.swing.ui.StatusBar;
import information.InformationService;
import music.MusicController;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-05.
 */

public class VolumePanelSM extends ContainerSM implements NegotiablePanel {

    public static StatusBarPanel invoker;

    Timer increase_timer;
    Timer decrease_timer;

    MouseEvent initial_event = null;

    public VolumePanelSM() {

        super();

        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);

        StatusBarPanel.volume_panel = this;

        increase_timer = new Timer(250, e -> {
            if (InformationService.current_volume < 1.00) {
                InformationService.current_volume += 0.01;
                MusicController.updateVolume();
            }
            RenderingService.invokeRepaint();
        });
        decrease_timer = new Timer(250, e -> {
            if (InformationService.current_volume > 0.00) {
                InformationService.current_volume -= 0.01;
                MusicController.updateVolume();
            }

            if (InformationService.current_volume < 0.00) {
                InformationService.current_volume = 0;
                MusicController.updateVolume();
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

                int difference = e.getX() - initial_event.getX();

                if (difference > 10) {

                    int delay = 3000/difference;
                    if (delay < 50) {
                        delay = 50;
                    }

                    increase_timer.setDelay(delay);
                    if (decrease_timer.isRunning()){
                        decrease_timer.stop();
                    }
                    if (!increase_timer.isRunning()){
                        increase_timer.start();
                    }
                } else if (difference < -8) {

                    int delay = 3000/-difference;
                    if (delay < 50) {
                        delay = 50;
                    }

                    decrease_timer.setDelay(delay);
                    if (increase_timer.isRunning()){
                        increase_timer.stop();
                    }
                    if (!decrease_timer.isRunning()){
                        decrease_timer.start();
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

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS);
        g2d.clip(clip);

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.AC_BACKGROUND_STOP_0, getWidth(), 0f, Constants.AC_BACKGROUND_STOP_1);

        g2d.setPaint(primary);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.drawImage(AdditionalResources.panel_volume_icon[InformationService.getVolumeIconState()], 265, 59, 76, 59, null);

        g2d.drawImage(AdditionalResources.left_dragger[(decrease_timer.isRunning()) ? 1 : 0], 38, 47, 46, 83, null);
        g2d.drawImage(AdditionalResources.right_dragger[(increase_timer.isRunning()) ? 1 : 0], 639, 47, 46, 83, null);

        g2d.setColor(Color.black);
        g2d.setFont(Resources.volume_font);
        g2d.drawString(String.valueOf((int)(InformationService.current_volume * 100)), 368, 105);
    }

    @Override
    public void updateInvoker() {
        invoker.forceSetVolumePanelStatus(false);
    }

    @Override
    public void setActive(boolean is_active) {

    }
}

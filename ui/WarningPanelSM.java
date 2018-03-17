package ui;

import information.InformationService;
import resources.AdditionalResources;
import resources.Constants;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-05.
 */

public class WarningPanelSM extends ContainerSM implements NegotiablePanel {

    MouseEvent initial_event = null;

    boolean dismissed;

    public WarningPanelSM() {

        super();

        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);

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

                if (dismissed) dismiss();
                RenderingService.invokeRepaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if (initial_event == null) return;

                int difference = e.getX() - initial_event.getX();

                if (difference < - 10) {
                    dismissed = true;
                }

                RenderingService.invokeRepaint();
            }
        });

        InformationService.battery_warning_reference = this;

    }

    void dismiss () {
        MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, this);
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

        g2d.drawImage(AdditionalResources.left_dragger[dismissed ? 1 : 0], 38, 47, 46, 83, null);

        if (InformationService.battery > 0 && InformationService.battery <= 20) {
            g2d.drawImage(AdditionalResources.battery_warning_20, 178, 56, 407, 66, null);
        } else if (InformationService.battery == 0) {
            g2d.drawImage(AdditionalResources.battery_warning_critical, 178, 56, 366, 66, null);
        }
    }

    @Override
    public void updateInvoker(){
    }

    @Override
    public void setActive(boolean is_active) {
        if (!is_active) dismissed = false;
    }
}

package ui;

import com.sun.java.swing.ui.StatusBar;
import information.InformationService;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-05.
 */

public class VolumePanelSM extends ContainerSM implements NegotiablePanel {

    public static StatusBarPanel invoker;

    Timer increase_timer;
    Timer decrease_timer;

    public VolumePanelSM() {

        super();

        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);

        StatusBarPanel.volume_panel = this;

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

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
        g2d.drawString(String.valueOf((int)(InformationService.current_volume * 100)), 368, 59);
    }

    @Override
    public void updateInvoker() {
        invoker.forceSetVolumePanelStatus(false);
    }

    @Override
    public void setActive(boolean is_active) {

    }
}

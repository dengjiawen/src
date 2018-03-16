package ui;

import information.InformationService;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by freddeng on 2018-03-16.
 */
public class AutoPilotAdjustor extends JPanel {

    GlowButton decrease;
    GlowButton increase;

    public AutoPilotAdjustor () {

        setBounds(131, 30, 87, 16);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);
        setLayout(null);

        decrease = new GlowButton(Resources.ap_minus, 0, 0, 16, 16);
        decrease.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (InformationService.drive_mode == Constants.MODE_AUTOPILOT) {
                    InformationService.ap_following_distance = (InformationService.ap_following_distance == 5) ? 5 : InformationService.ap_following_distance - 1;
                } else if (InformationService.drive_mode == Constants.MODE_CRUISE_CONTROL) {
                    InformationService.cruise_control_speed = (InformationService.cruise_control_speed == 10) ? 10 : InformationService.cruise_control_speed - 10;
                }

                RenderingService.invokeRepaint();
            }
        });

        increase = new GlowButton(Resources.ap_plus, getWidth() - 16, 0, 16, 16);
        increase.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (InformationService.drive_mode == Constants.MODE_AUTOPILOT) {
                    InformationService.ap_following_distance = (InformationService.ap_following_distance == 20) ? 20 : InformationService.ap_following_distance + 1;
                } else if (InformationService.drive_mode == Constants.MODE_CRUISE_CONTROL) {
                    InformationService.cruise_control_speed = (InformationService.cruise_control_speed == 150) ? 150 : InformationService.cruise_control_speed + 10;
                }

                RenderingService.invokeRepaint();
            }
        });

        add(decrease);
        add(increase);

    }

    @Override
    protected void paintComponent (Graphics g) {

        g.setColor(Constants.SPEED_UNIT_COLOR);
        g.setFont(Resources.ap_cruise_font);

        if (InformationService.drive_mode == Constants.MODE_AUTOPILOT) {
            int padding = g.getFontMetrics(Resources.ap_cruise_font).stringWidth(InformationService.ap_following_distance + " m");
            g.drawString(InformationService.ap_following_distance + " m", 87/2 - padding/2, getHeight()/2 + 3);

        } else if (InformationService.drive_mode == Constants.MODE_CRUISE_CONTROL) {
            int padding = g.getFontMetrics(Resources.ap_cruise_font).stringWidth(InformationService.cruise_control_speed + " KMH");
            g.drawString(InformationService.cruise_control_speed + " KMH", 87/2 - padding/2, getHeight()/2 + 3);
        }
    }

}

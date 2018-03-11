package ui;

import information.InformationService;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-08.
 */
public class StatusBarPanel extends JPanel {

    public final static int GEAR_PARKED = 0;
    public final static int GEAR_REVERSE = 1;
    public final static int GEAR_NEUTRAL = 2;
    public final static int GEAR_DRIVE = 3;
    public final static int GEAR_SPORT = 4;

    JLabel time;
    int current_drive_mode;

    BufferedImage abs_state;
    BufferedImage warning_state;
    BufferedImage airbag_state;
    BufferedImage battery_state;
    BufferedImage water_temp_state;

    public StatusBarPanel () {

        setBounds(20, 20, 380 + 705, 40);
        setOpaque(false);
        setLayout(null);

        time = new JLabel();
        time.setBounds(380 + (705 - 80), -8, 80, 40);
        time.setForeground(Color.black);
        time.setHorizontalTextPosition(SwingConstants.CENTER);
        time.setVerticalTextPosition(SwingConstants.CENTER);
        time.setFont(Resources.system_time_font);

        current_drive_mode = GEAR_PARKED;

        warning_state = Resources.warning[1];
        abs_state = Resources.abs[1];
        water_temp_state = Resources.water_temp[0];
        battery_state = Resources.battery[0];
        airbag_state = Resources.airbag[1];

        add(time);

        InformationService.status_bar_reference = this;

    }

    public void updateTime (String time_string) {
        time.setText(time_string);
        RenderingService.invokeRepaint();
    }

    protected void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(Resources.tesla_logo,380 + (705 - 20) / 2,5, 20,20,null);
        g2d.drawImage(abs_state, 310, 10,
                (int)(0.2 * abs_state.getWidth()), (int)(0.2 * abs_state.getHeight()), null);
        g2d.drawImage(warning_state, 283, 10,
                (int)(0.2 * warning_state.getWidth()), (int)(0.2 * warning_state.getHeight()), null);
        g2d.drawImage(water_temp_state, 243, 10,
                (int)(0.2 * water_temp_state.getWidth()), (int)(0.2 * water_temp_state.getHeight()), null);
        g2d.drawImage(battery_state, 218, 8,
                (int)(0.2 * battery_state.getWidth()), (int)(0.2 * battery_state.getHeight()), null);
        g2d.drawImage(airbag_state, 193, 10,
                (int)(0.2 * airbag_state.getWidth()), (int)(0.2 * airbag_state.getHeight()), null);

        g2d.setFont(Resources.drive_mode_font);
        g2d.setColor(Constants.text_inactive);
        g2d.drawString("P R N D S", 15, 25);

        int padding = 0;

        g2d.setColor(Color.black);
        switch (current_drive_mode) {
            case GEAR_PARKED:
                g2d.drawString("P", 15, 25);
                break;
            case GEAR_REVERSE:
                padding = g.getFontMetrics(Resources.drive_mode_font).stringWidth("P ");
                g2d.drawString("R", 15 + padding, 25);
                break;
            case GEAR_NEUTRAL:
                padding = g.getFontMetrics(Resources.drive_mode_font).stringWidth("P R ");
                g2d.drawString("N", 15 + padding, 25);
                break;
            case GEAR_DRIVE:
                padding = g.getFontMetrics(Resources.drive_mode_font).stringWidth("P R N ");
                g2d.drawString("D", 15 + padding, 25);
                break;
            case GEAR_SPORT:
                padding = g.getFontMetrics(Resources.drive_mode_font).stringWidth("P R N D ");
                g2d.drawString("S", 15 + padding, 25);
                break;
        }
    }

}

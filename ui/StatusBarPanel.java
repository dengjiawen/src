package ui;

import information.InformationService;
import information.WeatherService;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-08.
 */
public class StatusBarPanel extends JPanel {

    public static WeatherPanelSM weather_panel;
    public static VolumePanelSM volume_panel;

    private JLabel time;
    private JLabel temp;

    private JButton weather_panel_invoker;
    private JButton volume_panel_invoker;

    private int current_drive_mode;

    private boolean weather_is_showing;
    private boolean volume_is_showing;

    private BufferedImage abs_state;
    private BufferedImage warning_state;
    private BufferedImage airbag_state;
    private BufferedImage battery_state;
    private BufferedImage water_temp_state;

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

        temp = new JLabel();
        temp.setBounds(380 + (705 - 150), -8, 80, 40);
        temp.setForeground(Color.black);
        temp.setHorizontalTextPosition(SwingConstants.CENTER);
        temp.setVerticalTextPosition(SwingConstants.CENTER);
        temp.setFont(Resources.system_time_font);

        weather_is_showing = false;
        weather_panel_invoker = new JButton();
        weather_panel_invoker.setOpaque(false);
        weather_panel_invoker.setContentAreaFilled(false);
        weather_panel_invoker.setBorderPainted(false);
        weather_panel_invoker.setBounds(getWidth() - 180, 0, 100, 40);
        weather_panel_invoker.addActionListener(e -> {
            if (!weather_panel.isVisible()) {
                MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, weather_panel);
                weather_is_showing = true;
            } else {
                MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, weather_panel);
                weather_is_showing = false;
            }
            RenderingService.invokeRepaint();
        });

        volume_is_showing = false;
        volume_panel_invoker = new JButton();
        volume_panel_invoker.setOpaque(false);
        volume_panel_invoker.setContentAreaFilled(false);
        volume_panel_invoker.setBorderPainted(false);
        volume_panel_invoker.setBounds(380 + 10 + (int) (0.15 * Resources.bar_lte.getWidth()) + 10, 0, 50, 40);
        volume_panel_invoker.addActionListener(e -> {
            if (!volume_panel.isVisible()) {
                MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, volume_panel);
                volume_is_showing = true;
            } else {
                MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, volume_panel);
                volume_is_showing = false;
            }
        });

        current_drive_mode = Constants.GEAR_PARKED;

        warning_state = Resources.warning[1];
        abs_state = Resources.abs[1];
        water_temp_state = Resources.water_temp[0];
        battery_state = Resources.battery[0];
        airbag_state = Resources.airbag[1];

        add(time);
        add(temp);
        add(weather_panel_invoker);
        add(volume_panel_invoker);

        InformationService.status_bar_reference = this;
        WeatherPanelSM.invoker = this;
        VolumePanelSM.invoker = this;

    }

    public void updateTime (String time_string) {
        if (!time_string.equals(time.getText())) {
            time.setText(time_string);
            RenderingService.invokeRepaint();
        }
    }

    public void forceSetWeatherPanelStatus (boolean do_appear_active) {
        weather_is_showing = do_appear_active;
        RenderingService.invokeRepaint();
    }

    public void forceSetVolumePanelStatus (boolean do_appear_active) {
        volume_is_showing = do_appear_active;
        RenderingService.invokeRepaint();
    }

    public void shiftGear (int gear_modifier) {
        current_drive_mode = gear_modifier;

        RenderingService.invokeRepaint();
    }

    protected void paintComponent (Graphics g) {

        temp.setText(WeatherService.temperature + "°C");

        time.setForeground(Constants.STATUS_BAR_TEXT_COLOR);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setClip(new RoundRectangle2D.Double(360, 0, 725, 100, Constants.ROUNDNESS, Constants.ROUNDNESS));
        g2d.drawImage(Resources.shadow, 360, 0, 750, 30, null);

        g2d.setClip(null);
        g2d.drawImage(Resources.tesla_logo, 380 + (705 - 20) / 2, 5, 20, 20, null);

        g2d.setFont(Resources.drive_mode_font);
        g2d.setColor(Constants.TEXT_INACTIVE);
        g2d.drawString("P R N D", 15, 25);

        int padding = 0;

        if (InformationService.drive_gear == Constants.GEAR_NEUTRAL || InformationService.drive_gear == Constants.GEAR_PARKED) {
            warning_state = Resources.warning[1];
            airbag_state = Resources.airbag[1];
        } else {
            warning_state = Resources.warning[0];
            abs_state = Resources.abs[0];
            water_temp_state = Resources.water_temp[0];
            battery_state = Resources.battery[0];
            airbag_state = Resources.airbag[0];
        }

        if (InformationService.show_speed_warning || InformationService.show_follow_warning || InformationService.show_gear_warning) {
            warning_state = Resources.warning[1];
        }

        if (InformationService.battery < 20) {
            warning_state = Resources.warning[1];
            battery_state = Resources.battery[1];
        }

        g2d.setColor(Color.black);
        switch (current_drive_mode) {
            case Constants.GEAR_PARKED:
                g2d.drawString("P", 15, 25);
                break;
            case Constants.GEAR_REVERSE:
                padding = g.getFontMetrics(Resources.drive_mode_font).stringWidth("P ");
                g2d.drawString("R", 15 + padding, 25);
                break;
            case Constants.GEAR_NEUTRAL:
                padding = g.getFontMetrics(Resources.drive_mode_font).stringWidth("P R ");
                g2d.drawString("N", 15 + padding, 25);
                break;
            case Constants.GEAR_DRIVE:
                padding = g.getFontMetrics(Resources.drive_mode_font).stringWidth("P R N ");
                g2d.drawString("D", 15 + padding, 25);
                break;
        }

        g2d.drawImage(Resources.bar_lte, 380 + 10, 8, (int) (0.15 * Resources.bar_lte.getWidth()), (int) (0.15 * Resources.bar_lte.getHeight()), null);

        if (weather_is_showing) {
            temp.setForeground(Constants.STATUS_BAR_WEATHER_ACTIVE);
            g2d.drawImage(Resources.bar_snow[1], getWidth() - 178, 2, (int) (0.17 * Resources.bar_snow[1].getWidth()), (int) (0.17 * Resources.bar_snow[1].getHeight()), null);
        } else {
            temp.setForeground(Constants.STATUS_BAR_TEXT_COLOR);
            g2d.drawImage(Resources.bar_snow[0], getWidth() - 178, 2, (int) (0.17 * Resources.bar_snow[0].getWidth()), (int) (0.17 * Resources.bar_snow[0].getHeight()), null);
        }

        if (volume_is_showing) {
            g2d.drawImage(AdditionalResources.bar_volume_icon[InformationService.getVolumeIconState()][1],
                    380 + 10 + (int) (0.15 * Resources.bar_lte.getWidth()) + 10, 9, 19, 15, null);
        } else {
            g2d.drawImage(AdditionalResources.bar_volume_icon[InformationService.getVolumeIconState()][0],
                    380 + 10 + (int) (0.15 * Resources.bar_lte.getWidth()) + 10, 9, 19, 15, null);
        }

        g2d.drawImage(abs_state, 310, 10,
                (int) (0.2 * abs_state.getWidth()), (int) (0.2 * abs_state.getHeight()), null);
        g2d.drawImage(warning_state, 283, 10,
                (int) (0.2 * warning_state.getWidth()), (int) (0.2 * warning_state.getHeight()), null);
        g2d.drawImage(water_temp_state, 243, 10,
                (int) (0.2 * water_temp_state.getWidth()), (int) (0.2 * water_temp_state.getHeight()), null);
        g2d.drawImage(battery_state, 218, 8,
                (int) (0.2 * battery_state.getWidth()), (int) (0.2 * battery_state.getHeight()), null);
        g2d.drawImage(airbag_state, 193, 10,
                (int) (0.2 * airbag_state.getWidth()), (int) (0.2 * airbag_state.getHeight()), null);

    }

}

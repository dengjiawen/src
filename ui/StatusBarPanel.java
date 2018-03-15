package ui;

import information.InformationService;
import information.WeatherService;
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

    JLabel time;
    JLabel temp;

    JButton weather_panel_invoker;

    int current_drive_mode;

    boolean weather_is_showing;

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

        current_drive_mode = Constants.GEAR_PARKED;

        warning_state = Resources.warning[1];
        abs_state = Resources.abs[1];
        water_temp_state = Resources.water_temp[0];
        battery_state = Resources.battery[0];
        airbag_state = Resources.airbag[1];

        add(time);
        add(temp);
        add(weather_panel_invoker);

        InformationService.status_bar_reference = this;
        WeatherPanelSM.invoker = this;

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

        g2d.setFont(Resources.drive_mode_font);
        g2d.setColor(Constants.TEXT_INACTIVE);
        g2d.drawString("P R N D", 15, 25);

        int padding = 0;

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

    }

}

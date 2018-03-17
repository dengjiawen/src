package ui;

import information.WeatherService;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-05.
 */

public class WeatherPanelSM extends ContainerSM implements NegotiablePanel {

    public static StatusBarPanel invoker;

    private BufferedImage current_weather_frame;
    private int animation_framecount;
    private Timer animation_controller;

    public WeatherPanelSM() {

        super();

        animation_framecount = 0;
        current_weather_frame = Resources.snow_loop[animation_framecount];

        animation_controller = new Timer(1000/25, e -> {
            animation_framecount ++;
            if (animation_framecount == Resources.snow_loop.length) animation_framecount = 0;

            current_weather_frame = Resources.snow_loop[animation_framecount];

            RenderingService.invokeRepaint();
        });
        animation_controller.start();

        WeatherService.init();
        StatusBarPanel.weather_panel = this;

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D)g;

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS);
        g2d.clip(clip);

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            g2d.drawImage(current_weather_frame, 0, 0, (int) (0.75 * current_weather_frame.getWidth()), (int) (0.75 * current_weather_frame.getHeight()), null);
        } catch (NullPointerException e) {
            //TODO
        }
        g2d.drawImage(Resources.weather_gradient, 0, 0, getWidth(), getHeight(), null);

        drawIconAtQuarterScale(g2d, Resources.weather_humidity, 225, 60);
        drawIconAtQuarterScale(g2d, Resources.weather_pressure, 223, 82);

        drawIconAtQuarterScale(g2d, Resources.weather_sunrise, 223, 110);
        drawIconAtQuarterScale(g2d, Resources.weather_sunset, 223, 132);

        drawIconAtQuarterScale(g2d, Resources.weather_warning_panel, 210, 25);

        g2d.setFont(Resources.weather_city_font);
        g2d.setColor(Color.WHITE);
        g2d.drawString("NEPEAN", 45, 55);

        g2d.setFont(Resources.weather_condition_font);
        g2d.drawString("HEAVY SNOW", 45, 75);
        g2d.drawString("FORECAST", 382, 72);

        g2d.setFont(Resources.weather_main_temp_font);
        g2d.drawString(WeatherService.temperature + "°C", 45, 105);

        g2d.setFont(Resources.weather_sub_title_font);
        g2d.drawString("HIGH", 45, 125);
        g2d.drawString("LOW", 45, 140);

        g2d.setFont(Resources.weather_sub_text_font);
        g2d.drawString(WeatherService.high_temp + "°C", 85, 125);
        g2d.drawString(WeatherService.low_temp + "°C", 85, 140);

        g2d.drawString(WeatherService.humidity + "%", 250, 70);
        g2d.drawString(WeatherService.pressure + " hPa", 250, 92);

        g2d.drawString(WeatherService.sun_rise, 250, 118);
        g2d.drawString(WeatherService.sun_set, 250, 140);

        for (int i = 0; i < 3; i ++) {
            drawIconAtQuarterScale(g2d, Resources.weather_forecast_panel, 372, 80 + i * 25);
            g2d.setFont(Resources.weather_sub_title_font);
            g2d.drawString(WeatherService.forecast_times[i], 382, 98 + i * 25);
            g2d.drawString("TEMP", 460, 98 + i * 25);
            g2d.drawString("PREP", 555, 98 + i * 25);

            g2d.setFont(Resources.weather_sub_text_font);
            g2d.drawString(WeatherService.forecast_temperatures[i] + "°C", 505, 98 + i * 25);
            g2d.drawString(WeatherService.forecast_precipitations[i] + " cm", 595, 98 + i * 25);
        }
    }

    private void drawIconAtQuarterScale (Graphics2D g2d, BufferedImage image, int x, int y) {
        g2d.drawImage(image, x, y, (int)(0.35 * image.getWidth()), (int)(0.35 * image.getHeight()), null);
    }

    @Override
    public void setActive(boolean is_active) {
        if (is_active) animation_controller.restart();
        else {
            animation_controller.stop();
            System.gc();
        }
    }

    @Override
    public void updateInvoker() {
        invoker.forceSetWeatherPanelStatus(false);
    }
}

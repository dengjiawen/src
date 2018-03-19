/**
 * Copyright 2018 (C) Jiawen Deng, Ann J.S. and Kareem D. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng, Ann J.S. and Kareem D. (I-LU-V-EH)
 *
 * What is the data type for "false"?
 * It's a string.
 * See those quotation marks? That is not a boolean.
 *
 *-----------------------------------------------------------------------------
 * WeatherPanelSM.java
 *-----------------------------------------------------------------------------
 * A panel for showing weather information
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.WeatherService;
import resources.Constants;
import resources.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class WeatherPanelSM extends ContainerSM implements NegotiablePanel {

    static StatusBarPanel invoker;      // invoker reference

    private BufferedImage current_weather_frame;    // BufferedImage reference of the current frame in the animatio
    private int animation_framecount;               // integer of the current frame count
    private Timer animation_controller;             // timer that controls the weather animation

    WeatherPanelSM() {

        super();

        Console.printGeneralMessage("Initializing weather panel");

        // initiate animation timer and variables
        animation_framecount = 0;
        current_weather_frame = Resources.snow_loop[animation_framecount];

        animation_controller = new Timer(1000/25, e -> {

            // play animation on a loop, update current_weather_frame

            animation_framecount ++;
            if (animation_framecount == Resources.snow_loop.length) animation_framecount = 0;

            current_weather_frame = Resources.snow_loop[animation_framecount];

            RenderingService.invokeRepaint();
        });
        animation_controller.start();

        Console.printGeneralMessage("Start weather update daemon");
        WeatherService.init();

        Console.printGeneralMessage("Linking weather panel to status bar");
        StatusBarPanel.weather_panel = this;
        Console.printGeneralMessage("Linkage successful.");

    }

    /**
     * Method that draws icons at a quarter (or 0.35 x) scale.
     * @param g2d       Graphics2D instance
     * @param image     image to be drawn
     * @param x         x pos
     * @param y         y pos
     */
    private void drawIconAtQuarterScale (Graphics2D g2d, BufferedImage image, int x, int y) {
        g2d.drawImage(image, x, y, (int)(0.35 * image.getWidth()), (int)(0.35 * image.getHeight()), null);
    }

    /**
     * Overriden paintComponent method
     * @param g Abstract Graphics
     */
    @Override
    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS);
        g2d.clip(clip);

        // try to paint the weather animation, ignore missing frames
        try {
            g2d.drawImage(current_weather_frame, 0, 0, (int) (0.75 * current_weather_frame.getWidth()), (int) (0.75 * current_weather_frame.getHeight()), null);
        } catch (NullPointerException ignored) {}

        // draw transparent gradient layer
        g2d.drawImage(Resources.weather_gradient, 0, 0, getWidth(), getHeight(), null);

        // draw weather icons
        drawIconAtQuarterScale(g2d, Resources.weather_humidity, 225, 60);
        drawIconAtQuarterScale(g2d, Resources.weather_pressure, 223, 82);

        drawIconAtQuarterScale(g2d, Resources.weather_sunrise, 223, 110);
        drawIconAtQuarterScale(g2d, Resources.weather_sunset, 223, 132);

        drawIconAtQuarterScale(g2d, Resources.weather_warning_panel, 210, 25);

        // draw String: city names, weather condition, and forecast
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

        // draw String: actual temperature
        g2d.setFont(Resources.weather_sub_text_font);
        g2d.drawString(WeatherService.high_temp + "°C", 85, 125);
        g2d.drawString(WeatherService.low_temp + "°C", 85, 140);

        g2d.drawString(WeatherService.humidity + "%", 250, 70);
        g2d.drawString(WeatherService.pressure + " hPa", 250, 92);

        g2d.drawString(WeatherService.sun_rise, 250, 118);
        g2d.drawString(WeatherService.sun_set, 250, 140);

        // draw appropriate graphics for forecasts
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

    // Overriden methods for NegotiablePanel interface
    @Override
    public void setActive(boolean is_active) {
        // start animation only if panel is active
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

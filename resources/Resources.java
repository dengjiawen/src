/**
 * Copyright 2018 (C) Jiawen Deng. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng.
 *
 * From the Random Shack Data Processing Dictionary:
 * Endless Loop: n., see Loop, Endless.
 * Loop, Endless: n., see Endless Loop.
 *
 *-----------------------------------------------------------------------------
 * Resources.java
 *-----------------------------------------------------------------------------
 * This class hosts resources such as fonts and images.
 *-----------------------------------------------------------------------------
 */

package resources;

import information.Console;
import ui.LoadFrame;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import dependencies.fred.emma.BigBufferedImage;

public class Resources {

    // for debugging purposes only;
    // because it takes a long time to load weather assets
    // it is disabled during debugging
    private static final boolean DO_LOAD_WEATHER_ASSETS = true;

    // resource directories
    public static final String font_directory = "/resources/resources/fonts/";
    public static final String icon_directory = "/resources/resources/icons/";
    public static final String panel_overlay_directory = "/resources/resources/panel_overlay/";
    public static final String animation_directory = "/resources/resources/animation/";
    public static final String toggle_directory = "/resources/resources/music_control/";

    // names should be self explainatory. All resources here are font.
    private static Font system_bold;
    private static Font system_regular;
    private static Font system_light;

    public static Font speedometer_font;
    public static Font speed_font;
    public static Font speed_unit_font;

    public static Font ac_fanspeed_font;

    public static Font music_title_font;
    public static Font music_album_font;

    public static Font music_sub_title_font;
    public static Font music_sub_artist_font;
    public static Font music_sub_time_font;

    public static Font system_time_font;

    public static Font drive_mode_font;

    public static Font range_font;

    public static Font weather_city_font;
    public static Font weather_condition_font;
    public static Font weather_main_temp_font;
    public static Font weather_sub_title_font;
    public static Font weather_sub_text_font;

    public static Font core_temp_control_font;

    public static Font control_sunroof_percentage_font;

    public static Font music_lg_favorite_font;

    public static Font volume_font;

    public static Font ap_cruise_font;

    /**
     * Initiates all fonts
     */
    public static void initFont () {

        try{
            system_bold = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_bold.ttf"));
            system_regular = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_regular.ttf"));
            system_light = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_light.ttf"));

            ap_cruise_font = system_bold.deriveFont(11f);

            speedometer_font = system_bold.deriveFont(100f);
            speed_font = system_bold.deriveFont(75f);
            speed_unit_font = system_bold.deriveFont(20f);

            music_title_font = system_bold.deriveFont(20f);
            music_album_font = system_regular.deriveFont(13f);

            music_sub_title_font = system_regular.deriveFont(10f);
            music_sub_artist_font = system_bold.deriveFont(10f);
            music_sub_time_font = system_regular.deriveFont(8f);

            system_time_font = system_bold.deriveFont(15f);

            drive_mode_font = system_bold.deriveFont(20f);

            range_font = system_bold.deriveFont(15f);

            weather_city_font = system_bold.deriveFont(35f);
            weather_condition_font = system_bold.deriveFont(20f);
            weather_main_temp_font = system_bold.deriveFont(30f);

            weather_sub_title_font = system_bold.deriveFont(13f);
            weather_sub_text_font = system_regular.deriveFont(13f);

            core_temp_control_font = system_bold.deriveFont(35f);

            control_sunroof_percentage_font = system_bold.deriveFont(13f);

            music_lg_favorite_font = system_bold.deriveFont(15f);

            ac_fanspeed_font = system_bold.deriveFont(15f);

            volume_font = system_bold.deriveFont(50f);

        } catch (Exception e) {e.printStackTrace();}

    }

    // names should be self explainatory. All resources here are images.
    public static BufferedImage map_LG;
    public static BufferedImage shadow;
    public static BufferedImage tesla_logo;

    public static BufferedImage weather_gradient;
    public static BufferedImage weather_humidity;
    public static BufferedImage weather_pressure;
    public static BufferedImage weather_sunrise;
    public static BufferedImage weather_sunset;

    public static BufferedImage weather_warning_panel;
    public static BufferedImage weather_forecast_panel;

    public static BufferedImage control_sunroof_slider;
    public static BufferedImage control_mirror_slider;

    public static BufferedImage music_lg_connection;
    public static BufferedImage music_lg_search;
    public static BufferedImage music_lg_fav_text;

    public static BufferedImage ac_accessory;
    public static BufferedImage ac_main;
    public static BufferedImage ac_dragger;

    public static BufferedImage bar_lte;
    public static BufferedImage[] bar_snow;

    public static BufferedImage[] music_backdrop_SM;
    public static BufferedImage[] music_highlight;
    public static BufferedImage[] music_button;

    public static BufferedImage[] music_control_pause;
    public static BufferedImage[] music_control_play;
    public static BufferedImage[] music_forward;
    public static BufferedImage[] music_rewind;

    public static BufferedImage music_expand;

    public static BufferedImage[] music_shuffle;
    public static BufferedImage[] music_repeat;

    public static BufferedImage[] signal_left;
    public static BufferedImage[] signal_right;

    private static BufferedImage[] fan;

    public static BufferedImage[] abs;
    public static BufferedImage[] warning;
    public static BufferedImage[] water_temp;
    public static BufferedImage[] battery;
    public static BufferedImage[] airbag;

    public static BufferedImage[] car_icon_fronk;

    public static BufferedImage[] button_fronk;
    public static BufferedImage[] button_mirror;
    public static BufferedImage[] button_charge;
    public static BufferedImage[] button_trunk;

    public static BufferedImage[] control_Fdefrost;
    public static BufferedImage[] control_Rdefrost;
    public static BufferedImage[] control_hazard;
    public static BufferedImage[] control_lock;
    public static BufferedImage[] control_safety;
    public static BufferedImage[] control_wiper;
    public static BufferedImage[] control_light;

    public static BufferedImage[] core_ac;
    public static BufferedImage[] core_control;
    public static BufferedImage[] core_left_seat;
    public static BufferedImage[] core_right_seat;
    public static BufferedImage[] core_media;

    public static BufferedImage[] core_up_temp_arrow;
    public static BufferedImage[] core_down_temp_arrow;

    public static BufferedImage[] control_left_mirror;
    public static BufferedImage[] control_right_mirror;
    public static BufferedImage[] control_door_lock;
    public static BufferedImage[] control_mirror;
    public static BufferedImage[] control_sunroof;

    public static BufferedImage[] music_lg_frozen;
    public static BufferedImage[] music_lg_lala;
    public static BufferedImage[] music_lg_nine;
    public static BufferedImage[] music_lg_device;
    public static BufferedImage[] music_lg_radio;
    public static BufferedImage[] music_lg_stream;

    public static BufferedImage[] ac_butt_warmer_left;
    public static BufferedImage[] ac_butt_warmer_right;

    public static BufferedImage[][] ac_in_your_face;
    public static BufferedImage[][] ac_in_your_feet;
    public static BufferedImage[][] ac_power;

    public static BufferedImage[] ac_hand_warmer;

    public static BufferedImage[] music_mini_button;

    public static BufferedImage tesla_3_rear;
    public static BufferedImage speed_limit;
    public static BufferedImage ap_icon;
    public static BufferedImage cruise_icon;
    public static BufferedImage ap_guide;
    public static BufferedImage ap_shielding;
    public static BufferedImage ap_shielding_down;

    public static BufferedImage[] ap_plus;
    public static BufferedImage[] ap_minus;

    public static BufferedImage[] snow_loop;

    /**
     * Initiates all image resources
     */
    public static void initImage () {

        String[] toggle_assignment = new String[] {"_inactive.png", "_active.png"};
        String[] ac_assignment = new String[] {"_cold.png", "_hot.png"};

        ap_plus = new BufferedImage[2];
        ap_minus = new BufferedImage[2];

        tesla_3_rear = loadImage(icon_directory + "drive/tesla_3_rear.png");
        speed_limit = loadImage(icon_directory + "drive/speed_limit.png");
        ap_icon = loadImage(icon_directory + "drive/autopilot_active.png");
        cruise_icon = loadImage(icon_directory + "drive/cruise_active.png");
        ap_guide = loadImage(icon_directory + "drive/ap_guide.png");
        ap_shielding = loadImage(panel_overlay_directory + "ap_shielding.png");
        ap_shielding_down = loadImage(panel_overlay_directory + "ap_shielding_down.png");

        ac_in_your_face = new BufferedImage[2][2];
        ac_in_your_feet = new BufferedImage[2][2];
        ac_power = new BufferedImage[2][2];

        for (int i = 0; i < 2; i ++) {
            ac_in_your_feet[i][1] = loadImage(icon_directory + "ac/in_your_feet_active" + ac_assignment[i]);
            ac_in_your_feet[i][0] = loadImage(icon_directory + "ac/in_your_feet_inactive.png");

            ac_in_your_face[i][1] = loadImage(icon_directory + "ac/in_your_face_active" + ac_assignment[i]);
            ac_in_your_face[i][0] = loadImage(icon_directory + "ac/in_your_face_inactive.png");

            ac_power[i][1] = loadImage(icon_directory + "ac/power_active" + ac_assignment[i]);
            ac_power[i][0] = loadImage(icon_directory + "ac/power_inactive.png");
        }

        button_fronk = new BufferedImage[2];
        button_mirror = new BufferedImage[2];
        button_charge = new BufferedImage[2];
        button_trunk = new BufferedImage[2];

        music_backdrop_SM = new BufferedImage[4];
        music_highlight = new BufferedImage[4];
        music_button = new BufferedImage[4];

        ac_accessory = loadImage(panel_overlay_directory + "ac_accessory.png");
        ac_main = loadImage(panel_overlay_directory + "ac_main.png");
        ac_dragger = loadImage(icon_directory + "ac/dragger.png");

        tesla_logo = loadImage(icon_directory + "tesla_logo.png");

        music_lg_connection = loadImage(icon_directory + "music_xl/connection.png");
        music_lg_search = loadImage(icon_directory + "music_xl/search.png");
        music_lg_fav_text = loadImage(icon_directory + "music_xl/favorite_text.png");

        music_backdrop_SM[0] = loadImage(panel_overlay_directory + "frozen_backdrop.jpg");
        music_highlight[0] = loadImage(panel_overlay_directory + "frozen_highlight.png");
        music_button[0] = loadImage(panel_overlay_directory + "frozen_button.png");

        music_backdrop_SM[1] = loadImage(panel_overlay_directory + "nine_backdrop.jpg");
        music_highlight[1] = loadImage(panel_overlay_directory + "nine_highlight.png");
        music_button[1] = loadImage(panel_overlay_directory + "nine_button.png");

        music_backdrop_SM[2] = loadImage(panel_overlay_directory + "lala_backdrop.jpg");
        music_highlight[2] = loadImage(panel_overlay_directory + "lala_highlight.png");
        music_button[2] = loadImage(panel_overlay_directory + "lala_button.png");

        music_control_pause = new BufferedImage[2];
        music_control_play = new BufferedImage[2];
        music_forward = new BufferedImage[2];
        music_rewind = new BufferedImage[2];

        music_shuffle = new BufferedImage[2];
        music_repeat = new BufferedImage[3];

        signal_left = new BufferedImage[2];
        signal_right = new BufferedImage[2];

        fan = new BufferedImage[2];

        abs = new BufferedImage[2];
        warning = new BufferedImage[2];
        water_temp = new BufferedImage[2];
        battery = new BufferedImage[2];
        airbag = new BufferedImage[2];

        car_icon_fronk = new BufferedImage[2];

        control_Fdefrost = new BufferedImage[2];
        control_Rdefrost = new BufferedImage[2];
        control_hazard = new BufferedImage[2];
        control_lock = new BufferedImage[2];
        control_safety = new BufferedImage[2];
        control_wiper = new BufferedImage[6];
        control_light = new BufferedImage[6];

        core_ac = new BufferedImage[3];
        core_control = new BufferedImage[2];
        core_left_seat = new BufferedImage[4];
        core_right_seat = new BufferedImage[4];
        core_media = new BufferedImage[2];

        core_up_temp_arrow = new BufferedImage[2];
        core_down_temp_arrow = new BufferedImage[2];

        control_left_mirror = new BufferedImage[5];
        control_right_mirror = new BufferedImage[5];
        control_door_lock = new BufferedImage[2];
        control_mirror = new BufferedImage[3];
        control_sunroof = new BufferedImage[2];

        music_lg_frozen = new BufferedImage[2];
        music_lg_lala = new BufferedImage[2];
        music_lg_nine = new BufferedImage[2];
        music_lg_device = new BufferedImage[2];
        music_lg_radio = new BufferedImage[2];
        music_lg_stream = new BufferedImage[2];

        ac_butt_warmer_left = new BufferedImage[4];
        ac_butt_warmer_right = new BufferedImage[4];

        ac_hand_warmer = new BufferedImage[2];

        music_mini_button = new BufferedImage[2];

        music_expand = loadImage(panel_overlay_directory + "music_button_active.png");

        control_mirror_slider = loadImage(icon_directory + "control/mirror_slider_base.png");
        control_sunroof_slider = loadImage(icon_directory + "control/sunroof_slider_base.png");

        map_LG = loadImage(panel_overlay_directory + "map_LG.jpg");
        shadow = loadImage(panel_overlay_directory + "shadow.png");
        weather_gradient = loadImage(panel_overlay_directory + "weather_gradient.png");

        weather_humidity = loadImage(icon_directory + "weather/humidity.png");
        weather_pressure = loadImage(icon_directory + "weather/pressure.png");
        weather_sunrise = loadImage(icon_directory + "weather/sunrise.png");
        weather_sunset = loadImage(icon_directory + "weather/sunset.png");

        weather_warning_panel = loadImage(panel_overlay_directory + "warning_panel.png");
        weather_forecast_panel = loadImage(panel_overlay_directory + "forecast_panel.png");

        bar_lte = loadImage(icon_directory + "LTE.png");
        bar_snow = new BufferedImage[2];

        for (int i = 0; i < 2; i ++) {
            music_control_pause[i] = loadImage(icon_directory + "pause" + toggle_assignment[i]);
            music_control_play[i] = loadImage(icon_directory + "play" + toggle_assignment[i]);
            music_forward[i] = loadImage(icon_directory + "forward" + toggle_assignment[i]);
            music_rewind[i] = loadImage(icon_directory + "rewind" + toggle_assignment[i]);

            music_shuffle[i] = loadImage(toggle_directory + "shuffle" + toggle_assignment[i]);
            music_repeat[i] = loadImage(toggle_directory + "repeat" + toggle_assignment[i]);

            signal_right[i] = loadImage(icon_directory + "signal_right" + toggle_assignment[i]);
            signal_left[i] = loadImage(icon_directory + "signal_left" + toggle_assignment[i]);

            fan[i] = loadImage(icon_directory + "fan" + toggle_assignment[i]);

            abs[i] = loadImage(icon_directory + "instrument/abs" + toggle_assignment[i]);
            warning[i] = loadImage(icon_directory + "instrument/warning" + toggle_assignment[i]);
            water_temp[i] = loadImage(icon_directory + "instrument/water_temp" + toggle_assignment[i]);
            battery[i] = loadImage(icon_directory + "instrument/battery" + toggle_assignment[i]);
            airbag[i] = loadImage(icon_directory + "instrument/airbag" + toggle_assignment[i]);

            car_icon_fronk[i] = loadImage(icon_directory + "parked/tesla_fronk" + toggle_assignment[i]);

            button_fronk[i] = loadImage(icon_directory + "parked/fronk" + toggle_assignment[i]);
            button_mirror[i] = loadImage(icon_directory + "parked/mirror" + toggle_assignment[i]);
            button_charge[i] = loadImage(icon_directory + "parked/charge" + toggle_assignment[i]);
            button_trunk[i] = loadImage(icon_directory + "parked/trunk" + toggle_assignment[i]);

            control_Fdefrost[i] = loadImage(icon_directory + "control_center/Fdefrost" + toggle_assignment[i]);
            control_Rdefrost[i] = loadImage(icon_directory + "control_center/Rdefrost" + toggle_assignment[i]);
            control_hazard[i] = loadImage(icon_directory + "control_center/hazard" + toggle_assignment[i]);
            control_lock[i] = loadImage(icon_directory + "control_center/lock" + toggle_assignment[i]);
            control_safety[i] = loadImage(icon_directory + "control_center/safety" + toggle_assignment[i]);

            core_control[i] = loadImage(icon_directory + "main_control_bar/control" + toggle_assignment[i]);
            core_media[i] = loadImage(icon_directory + "main_control_bar/media" + toggle_assignment[i]);

            core_up_temp_arrow[i] = loadImage(icon_directory + "main_control_bar/up_temp_arrow" + toggle_assignment[i]);
            core_down_temp_arrow[i] = loadImage(icon_directory + "main_control_bar/down_temp_arrow" + toggle_assignment[i]);

            control_door_lock[i] = loadImage(icon_directory + "control/door_lock" + toggle_assignment[i]);
            control_sunroof[i] = loadImage(icon_directory + "control/sunroof" + toggle_assignment[i]);

            music_lg_frozen[i] = loadImage(icon_directory + "music_xl/frozen" + toggle_assignment[i]);
            music_lg_lala[i] = loadImage(icon_directory + "music_xl/lala" + toggle_assignment[i]);
            music_lg_nine[i] = loadImage(icon_directory + "music_xl/nine" + toggle_assignment[i]);
            music_lg_device[i] = loadImage(icon_directory + "music_xl/device" + toggle_assignment[i]);
            music_lg_radio[i] = loadImage(icon_directory + "music_xl/radio" + toggle_assignment[i]);
            music_lg_stream[i] = loadImage(icon_directory + "music_xl/stream" + toggle_assignment[i]);

            music_mini_button[i] = loadImage(icon_directory + "mini_player" + toggle_assignment[i]);

            bar_snow[i] = loadImage(icon_directory + "snow" + toggle_assignment[i]);

            ac_hand_warmer[i] = loadImage(icon_directory + "ac/hand_warmer" + toggle_assignment[i]);

            ap_plus[i] = loadImage(icon_directory + "drive/plus" + toggle_assignment[i]);
            ap_minus[i] = loadImage(icon_directory + "drive/minus" + toggle_assignment[i]);
        }

        for (int i = 0; i < 3; i ++) {
            control_mirror[i] = loadImage(icon_directory + "control/mirror_state_" + i + ".png");
        }

        for (int i = 0; i < 5; i ++) {
            control_left_mirror[i] = loadImage(icon_directory + "control/left_mirror_" + i + ".png");
            control_right_mirror[i] = loadImage(icon_directory + "control/right_mirror_" + i + ".png");
        }

        for (int i = 0; i < 4; i ++) {
            core_right_seat[i] = loadImage(icon_directory + "main_control_bar/right_seat_mode_" + i + ".png");
            core_left_seat[i] = loadImage(icon_directory + "main_control_bar/left_seat_mode_" + i + ".png");

            ac_butt_warmer_left[i] = loadImage(icon_directory + "ac/butt_warmer_left_" + i + ".png");
            ac_butt_warmer_right[i] = loadImage(icon_directory + "ac/butt_warmer_right_" + i + ".png");
        }

        for (int i = 0; i < 3; i ++) {
            core_ac[i] = loadImage(icon_directory + "main_control_bar/ac_mode_" + i + ".png");
        }

        for (int i = 0; i < 6; i ++) {
            control_wiper[i] = loadImage(icon_directory + "control_center/wiper/wiper_" + i + ".png");
            control_light[i] = loadImage(icon_directory + "control_center/light/light_" + i + ".png");
        }

        snow_loop = new BufferedImage[200];
        if (DO_LOAD_WEATHER_ASSETS) {
            for (int i = 0; i < 200; i++) {
                snow_loop[i] = loadImageNotStoredInRam(animation_directory + "weather_upd/snow_loop_" + i + ".jpg");
            }
        } else {
            for (int i = 0; i < 200; i++) {
                snow_loop[i] = null;
            }
        }

        music_repeat[2] = loadImage(toggle_directory + "repeat_1" + toggle_assignment[1]);

        AdditionalResources.init();

    }

    /**
     * Method that loads an image from a String path
     * @param res_path  the path to the image data
     * @return  the corresponding, parsed BufferedImage
     */
    private static BufferedImage loadImage (String res_path) {

        Console.printGeneralMessage("Importing and reading image from " + res_path);

        try {
            SwingUtilities.invokeLater(() -> LoadFrame.requestLoadPanelReference().updateLoadedAsset(res_path));

            Console.printGeneralMessage("Image " + res_path + " read successfully.");

            return ImageIO.read(Resources.class.getResource(res_path));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Resources are missing. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(100);
        }

        return null;
    }

    /**
     * Method that loads an image and buffers it into a temporary file
     * to decrease RAM usage (for weather animation only)
     * @param res_path  the path to the image data
     * @return  the corresponding, parsed BufferedImage (that is cached in file)
     */
    private static BufferedImage loadImageNotStoredInRam (String res_path) {

        try {

            // create temp file
            File temp_file = File.createTempFile(String.valueOf(res_path.hashCode()), ".tmp");
            temp_file.deleteOnExit();

            // copy it to the temp file
            BufferedImage image = loadImage(res_path);
            ImageIO.write(image, "jpg", temp_file);

            SwingUtilities.invokeLater(() -> LoadFrame.requestLoadPanelReference().updateLoadedAsset(res_path));

            // create a "BigBufferedImage" from the temporary file,
            // which is a cached version of BufferedImage
            return BigBufferedImage.create(temp_file, BufferedImage.TYPE_INT_RGB);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Method that changes all elements on the status bar to white, in order
     * for the status bar to enter dark mode.
     * @param do_invoke whether dark mode should be invoked
     */
    public static void invokeStatusBarDarkMode (boolean do_invoke) {

        if (do_invoke) {
            shadow = loadImage(icon_directory + "dark_assets/shadow.png");
            bar_snow[0] = loadImage(icon_directory + "dark_assets/snow_inactive.png");
            tesla_logo = loadImage(icon_directory + "dark_assets/tesla_logo.png");
            bar_lte = loadImage(icon_directory + "dark_assets/LTE.png");

            for (int i = 0; i < 4; i ++) {
                AdditionalResources.bar_volume_icon[i][0] = loadImage(icon_directory + "dark_assets/volume_" + i + "_inactive.png");
            }

            Constants.STATUS_BAR_TEXT_COLOR = Color.decode("#FFFFFF");
        } else {
            shadow = loadImage(panel_overlay_directory + "shadow.png");
            bar_snow[0] = loadImage(icon_directory + "snow_inactive.png");
            tesla_logo = loadImage(icon_directory + "tesla_logo.png");
            bar_lte = loadImage(icon_directory + "LTE.png");

            for (int i = 0; i < 4; i ++) {
                AdditionalResources.bar_volume_icon[i][0] = loadImage(icon_directory + "status_bar/volume_" + i + "_inactive.png");
            }

            Constants.STATUS_BAR_TEXT_COLOR = Color.decode("#000000");
        }

    }

}

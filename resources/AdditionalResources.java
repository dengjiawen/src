package resources;

import ui.LoadFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-15.
 */
public class AdditionalResources {

    public static BufferedImage parking_brake;

    public static BufferedImage gear_warning;
    public static BufferedImage speed_warning;
    public static BufferedImage follow_warning;

    public static BufferedImage[] ac_up_down_adjustor;
    public static BufferedImage[] ac_fan_up;
    public static BufferedImage[] ac_fan_down;

    public static BufferedImage[] ac_fan_icon;

    public static BufferedImage[] ac_manual;
    public static BufferedImage[] ac_auto;

    public static BufferedImage[][] ac_fresh_air;
    public static BufferedImage[][] ac_circulation;

    public static BufferedImage[][] bar_volume_icon;
    public static BufferedImage[] panel_volume_icon;

    public static BufferedImage[] left_dragger;
    public static BufferedImage[] right_dragger;

    public static BufferedImage[] charging_animation;

    public static BufferedImage battery_warning_20;
    public static BufferedImage battery_warning_critical;

    public static BufferedImage map_compass;
    public static BufferedImage map_nav_bar;

    public static BufferedImage[] map_zoom_in;
    public static BufferedImage[] map_zoom_out;

    public static void init () {

        String[] toggle_assignment = new String[] {"_inactive.png", "_active.png", "_disabled.png"};

        map_zoom_in = new BufferedImage[2];
        map_zoom_out = new BufferedImage[2];

        map_compass = loadImage(Resources.icon_directory + "map/compass.png");
        map_nav_bar = loadImage(Resources.icon_directory + "map/nav_bar.png");

        charging_animation = new BufferedImage[125];
        for (int i = 0; i < 125; i ++) {
            charging_animation[i] = loadImage(Resources.animation_directory + "charge_upd/charge_" + i + ".png");
        }

        battery_warning_20 = loadImage(Resources.panel_overlay_directory + "battery_low_20.png");
        battery_warning_critical = loadImage(Resources.panel_overlay_directory + "battery_low_critical.png");

        gear_warning = loadImage(Resources.icon_directory + "gear_warning.png");
        speed_warning = loadImage(Resources.icon_directory + "speed_warning.png");
        follow_warning = loadImage(Resources.icon_directory + "follow_warning.png");

        bar_volume_icon = new BufferedImage[4][2];
        panel_volume_icon = new BufferedImage[4];

        left_dragger = new BufferedImage[2];
        right_dragger = new BufferedImage[2];

        for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 2; j++) {
                bar_volume_icon[i][j] = loadImage(Resources.icon_directory + "status_bar/volume_" + i + toggle_assignment[j]);
            }
            panel_volume_icon[i] = loadImage(Resources.icon_directory + "volume_" + i + ".png");
        }

        ac_up_down_adjustor = new BufferedImage[2];
        ac_fan_up = new BufferedImage[3];
        ac_fan_down = new BufferedImage[3];

        ac_fan_icon = new BufferedImage[2];

        ac_manual = new BufferedImage[2];
        ac_auto = new BufferedImage[2];

        ac_fresh_air = new BufferedImage[2][];
        ac_circulation = new BufferedImage[2][];

        parking_brake = loadImage(Resources.icon_directory + "p_brake.png");

        ac_up_down_adjustor[0] = loadImage(Resources.icon_directory + "ac/up_down_adjustor_disabled.png");
        ac_up_down_adjustor[1] = loadImage(Resources.icon_directory + "ac/up_down_adjustor.png");

        ac_fan_icon[0] = loadImage(Resources.icon_directory + "ac/mini_fan.png");
        ac_fan_icon[1] = loadImage(Resources.icon_directory + "ac/mini_fan_disabled.png");

        for (int j = 0; j < 2; j ++) {
            ac_auto[j] = loadImage(Resources.icon_directory + "ac/auto" + toggle_assignment[j]);
            ac_manual[j] = loadImage(Resources.icon_directory + "ac/manual" + toggle_assignment[j]);

            left_dragger[j] = loadImage(Resources.icon_directory + "dismiss_left" + toggle_assignment[j]);
            right_dragger[j] = loadImage(Resources.icon_directory + "dismiss_right" + toggle_assignment[j]);

            map_zoom_in[j] = loadImage(Resources.icon_directory + "map/zoom_in" + toggle_assignment[j]);
            map_zoom_out[j] = loadImage(Resources.icon_directory + "map/zoom_out" + toggle_assignment[j]);
        }

        for (int i = 0; i < 3; i ++) {
            ac_fan_up[i] = loadImage(Resources.icon_directory + "ac/up" + toggle_assignment[i]);
            ac_fan_down[i] = loadImage(Resources.icon_directory + "ac/down" + toggle_assignment[i]);
        }

        ac_fresh_air[0] = new BufferedImage[] {
                loadImage(Resources.icon_directory + "ac/fresh_air_inactive.png"),
                loadImage(Resources.icon_directory + "ac/fresh_air_active_cold.png"),
                loadImage(Resources.icon_directory + "ac/fresh_air_disabled.png")
        };

        ac_fresh_air[1] = new BufferedImage[] {
                loadImage(Resources.icon_directory + "ac/fresh_air_inactive.png"),
                loadImage(Resources.icon_directory + "ac/fresh_air_active_hot.png"),
                loadImage(Resources.icon_directory + "ac/fresh_air_disabled.png")
        };

        ac_circulation[0] = new BufferedImage[] {
                loadImage(Resources.icon_directory + "ac/circulation_inactive.png"),
                loadImage(Resources.icon_directory + "ac/circulation_active_cold.png"),
                loadImage(Resources.icon_directory + "ac/circulation_disabled.png")
        };

        ac_circulation[1] = new BufferedImage[] {
                loadImage(Resources.icon_directory + "ac/circulation_inactive.png"),
                loadImage(Resources.icon_directory + "ac/circulation_active_hot.png"),
                loadImage(Resources.icon_directory + "ac/circulation_disabled.png")
        };

    }

    private static BufferedImage loadImage (String res_path) {

        try {
            SwingUtilities.invokeLater(() -> {
                LoadFrame.requestLoadPanelReference().updateLoadedAsset(res_path);
            });
            return ImageIO.read(AdditionalResources.class.getResource(res_path));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Resources are missing. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(100);
        }

        return null;
    }

}

package resources;

import ui.BigBufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by freddeng on 2018-03-01.
 */
public class Resources {

    public static final String font_directory = "/resources/resources/fonts/";
    public static final String icon_directory = "/resources/resources/icons/";
    public static final String panel_overlay_directory = "/resources/resources/panel_overlay/";
    public static final String animation_directory = "/resources/resources/animation/";

    public static final String toggle_directory = "/resources/resources/music_control/";

    public static Font system_bold;
    public static Font system_regular;
    public static Font system_light;

    public static Font oval_button_font;
    public static Font speedometer_font;

    public static Font music_title_font;
    public static Font music_album_font;

    public static Font music_sub_title_font;
    public static Font music_sub_artist_font;
    public static Font music_sub_time_font;

    public static Font system_time_font;

    public static Font drive_mode_font;

    public static Font range_font;

    public static void initFont () {

        try{
            system_bold = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_bold.ttf"));
            system_regular = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_regular.ttf"));
            system_light = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_light.ttf"));

            oval_button_font = system_bold.deriveFont(14.5f);
            speedometer_font = system_bold.deriveFont(100f);

            music_title_font = system_bold.deriveFont(20f);
            music_album_font = system_regular.deriveFont(13f);

            music_sub_title_font = system_regular.deriveFont(10f);
            music_sub_artist_font = system_bold.deriveFont(10f);
            music_sub_time_font = system_regular.deriveFont(8f);

            system_time_font = system_bold.deriveFont(15f);

            drive_mode_font = system_bold.deriveFont(20f);

            range_font = system_bold.deriveFont(15f);

        } catch (Exception e) {e.printStackTrace();}

    }

    public static BufferedImage map_LG;
    public static BufferedImage shadow;
    public static BufferedImage tesla_logo;

    public static BufferedImage[] music_backdrop_SM;
    public static BufferedImage[] music_highlight;

    public static BufferedImage[] music_control_pause;
    public static BufferedImage[] music_control_play;
    public static BufferedImage[] music_forward;
    public static BufferedImage[] music_rewind;

    public static BufferedImage[] music_shuffle;
    public static BufferedImage[] music_repeat;

    public static BufferedImage[] signal_left;
    public static BufferedImage[] signal_right;

    public static BufferedImage[] fan;

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

    public static BufferedImage[] snow_loop;

    public static void initImage () {

        String[] toggle_assignment = new String[] {"_inactive.png", "_active.png"};

        button_fronk = new BufferedImage[2];
        button_mirror = new BufferedImage[2];
        button_charge = new BufferedImage[2];
        button_trunk = new BufferedImage[2];

        music_backdrop_SM = new BufferedImage[4];
        music_highlight = new BufferedImage[4];

        tesla_logo = loadImage(icon_directory + "tesla_logo.png");

        music_backdrop_SM[0] = loadImage(panel_overlay_directory + "frozen_backdrop.jpg");
        music_highlight[0] = loadImage(panel_overlay_directory + "frozen_highlight.png");

        music_backdrop_SM[1] = loadImage(panel_overlay_directory + "nine_backdrop.jpg");
        music_highlight[1] = loadImage(panel_overlay_directory + "nine_highlight.png");

        music_backdrop_SM[2] = loadImage(panel_overlay_directory + "help_backdrop.jpg");
        music_highlight[2] = loadImage(panel_overlay_directory + "help_highlight.png");

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

        map_LG = loadImage(panel_overlay_directory + "map_LG.jpg");
        shadow = loadImage(panel_overlay_directory + "shadow.png");

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
        }

        for (int i = 0; i < 6; i ++) {
            control_wiper[i] = loadImage(icon_directory + "control_center/wiper/wiper_" + i + ".png");
            control_light[i] = loadImage(icon_directory + "control_center/light/light_" + i + ".png");
        }

        snow_loop = new BufferedImage[480];

        ExecutorService[] threads = new ExecutorService[10];
        for (int i = 0; i < 10; i ++) {
            threads[i] = Executors.newSingleThreadExecutor();
        }

        File temp_dir = null;
        try {
            temp_dir =  File.createTempFile("temp",null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0, j = 0; i < 480; i ++, j++) {

            final int currentnum = i;
            threads[j].submit(() -> {
                try {
                    snow_loop[currentnum] = BigBufferedImage.create(new File(Resources.class.getResource
                            (animation_directory + "weather/snow_loop_" + currentnum + ".jpg").toURI()), BigBufferedImage.TYPE_INT_RGB);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            if (j == 9) {
                j = -1;
            }

        }

        music_repeat[2] = loadImage(toggle_directory + "repeat_1" + toggle_assignment[1]);

    }

    private static BufferedImage loadImage (String res_path) {

        try {
            return ImageIO.read(Resources.class.getResource(res_path));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Resources are missing. " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(100);
        }

        return null;
    }

}

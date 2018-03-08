package resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-01.
 */
public class Resources {

    public static final String font_directory = "/resources/resources/fonts/";
    public static final String icon_directory = "/resources/resources/icons/";
    public static final String panel_overlay_directory = "/resources/resources/panel_overlay/";

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

        } catch (Exception e) {e.printStackTrace();}

    }

    public static BufferedImage car_icon_top;

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

    public static void initImage () {

        String[] toggle_assignment = new String[] {"_inactive.png", "_active.png"};

        music_backdrop_SM = new BufferedImage[4];
        music_highlight = new BufferedImage[4];

        car_icon_top = loadImage(icon_directory + "car_icon_top_frunk_down.png");
        music_backdrop_SM[0] = loadImage(panel_overlay_directory + "frozen_backdrop.jpg");
        music_highlight[0] = loadImage(panel_overlay_directory + "frozen_highlight.png");

        music_control_pause = new BufferedImage[2];
        music_control_play = new BufferedImage[2];
        music_forward = new BufferedImage[2];
        music_rewind = new BufferedImage[2];

        music_shuffle = new BufferedImage[2];
        music_repeat = new BufferedImage[3];

        signal_left = new BufferedImage[2];
        signal_right = new BufferedImage[2];

        for (int i = 0; i < 2; i ++) {
            music_control_pause[i] = loadImage(icon_directory + "pause" + toggle_assignment[i]);
            music_control_play[i] = loadImage(icon_directory + "play" + toggle_assignment[i]);
            music_forward[i] = loadImage(icon_directory + "forward" + toggle_assignment[i]);
            music_rewind[i] = loadImage(icon_directory + "rewind" + toggle_assignment[i]);

            music_shuffle[i] = loadImage(toggle_directory + "shuffle" + toggle_assignment[i]);
            music_repeat[i] = loadImage(toggle_directory + "repeat" + toggle_assignment[i]);

            signal_right[i] = loadImage(icon_directory + "signal_right" + toggle_assignment[i]);
            signal_left[i] = loadImage(icon_directory + "signal_left" + toggle_assignment[i]);
        }

        music_repeat[2] = loadImage(toggle_directory + "repeat_1" + toggle_assignment[1]);

    }

    private static BufferedImage loadImage (String res_path) {

        try {
            return ImageIO.read(Resources.class.getResource(res_path));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Resources are missing.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(100);
        }

        return null;

    }

}

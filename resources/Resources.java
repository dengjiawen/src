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

    public static Font system_bold;
    public static Font system_regular;
    public static Font system_light;

    public static Font oval_button_font;
    public static Font speedometer_font;

    public static Font music_title_font;
    public static Font music_album_artist_font;

    public static void initFont () {

        try{
            system_bold = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_bold.ttf"));
            system_regular = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_regular.ttf"));
            system_light = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_light.ttf"));

            oval_button_font = system_bold.deriveFont(14.5f);
            speedometer_font = system_bold.deriveFont(100f);

            music_title_font = system_bold.deriveFont(22.5f);
            music_album_artist_font = system_bold.deriveFont(15f);

        } catch (Exception e) {e.printStackTrace();}

    }

    public static BufferedImage car_icon_top;
    public static BufferedImage signal_left;

    public static BufferedImage frozen_music_backdrop_SM;

    public static BufferedImage[] music_control_pause;
    public static BufferedImage[] music_control_play;
    public static BufferedImage[] music_forward;
    public static BufferedImage[] music_rewind;

    public static void initImage () {

        car_icon_top = loadImage(icon_directory + "car_icon_top_frunk_down.png");
        signal_left = loadImage(icon_directory + "signal_left.png");
        frozen_music_backdrop_SM = loadImage(panel_overlay_directory + "frozen.jpg");

        music_control_pause = new BufferedImage[2];
        music_control_play = new BufferedImage[2];
        music_forward = new BufferedImage[2];
        music_rewind = new BufferedImage[2];

        music_control_pause[0] = loadImage(icon_directory + "pause_inactive.png");
        music_control_pause[1] = loadImage(icon_directory + "pause_active.png");
        music_control_play[0] = loadImage(icon_directory + "play_inactive.png");
        music_control_play[1] = loadImage(icon_directory + "play_active.png");
        music_forward[0] = loadImage(icon_directory + "forward_inactive.png");
        music_forward[1] = loadImage(icon_directory + "forward_active.png");
        music_rewind[0] = loadImage(icon_directory + "rewind_inactive.png");
        music_rewind[1] = loadImage(icon_directory + "rewind_active.png");

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

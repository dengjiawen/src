package resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-01.
 */
public class Resources {

    public static final String font_directory = "/resources/resources/fonts/";
    public static final String icon_directory = "/resources/resources/icons/";

    public static Font system_bold;
    public static Font system_regular;
    public static Font system_light;

    public static void initFont () {

        try{
            system_bold = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_bold.ttf"));
            system_regular = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_regular.ttf"));
            system_light = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_light.ttf"));
        } catch (Exception e) {e.printStackTrace();}

    }

    public static BufferedImage car_icon_top;

    public static void initImage () {

        try {
            car_icon_top = ImageIO.read(Resources.class.getResource(icon_directory + "car_icon_top_frunk_down.png"));
        } catch (Exception e) {e.printStackTrace();}

    }

}

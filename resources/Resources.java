package resources;

import java.awt.*;

/**
 * Created by freddeng on 2018-03-01.
 */
public class Resources {

    public static final String font_directory = "/resources/fonts/";

    public static Font system_bold;
    public static Font system_regular;
    public static Font system_light;

    public static void init () {

        try{
            system_bold = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_bold.ttf"));
            system_regular = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_regular.ttf"));
            system_light = Font.createFont(Font.TRUETYPE_FONT, Resources.class.getResourceAsStream(font_directory + "system_light.ttf"));
        } catch (Exception e) {}

    }

}

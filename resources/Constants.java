package resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-01.
 */
public class Constants {

    public static final Color BACKGROUND_GREY = Color.decode("#141414");
    public static final Color PANEL_BRIGHT = Color.decode("#f0f0f0");
    public static final Color BUTTON_BRIGHT = Color.decode("#d7d7d7");

    public static final Color MUSIC_PROGRESS_STOP_0 = Color.decode("#12b5de");
    public static final Color MUSIC_PROGRESS_STOP_1 = Color.decode("#d1dde0");

    public static final Color BATTERY_PROGRESS_STOP_0 = Color.decode("#088700");
    public static final Color BATTERY_PROGRESS_STOP_1 = Color.decode("#a1d600");

    public static final Color TEXT_INACTIVE = Color.decode("#c2bcbc");

    public static final Color CONTROL_CENTER = Color.decode("#e6e6e5");

    public static final Color CORE_BAR_INACTIVE = Color.decode("#404040");

    public static final Color CONTROL_INTERFACE_INACTIVE = Color.decode("#989898");

    public static final Color CONTROL_INTERFACE_STOP_0 = Color.decode("#f1f0f0");
    public static final Color CONTROL_INTERFACE_STOP_1 = Color.decode("#ffffff");

    public static final Color STATUS_BAR_WEATHER_ACTIVE = Color.decode("#4bd7ff");

    public static final Color MUSIC_LG_GRADIENT_STOP_0 = Color.decode("#f7f7f7");
    public static final Color MUSIC_LG_GRADIENT_STOP_1 = Color.decode("#ededed");

    public static final Color MUSIC_LG_SUBPANEL_BACKGROUND = Color.decode("#e1e1e1");
    public static final Color MUSIC_LG_PHONE_BACKGROUND = Color.decode("#ffffff");

    public static final Color AC_BACKGROUND_STOP_0 = Color.decode("#f1f0f0");
    public static final Color AC_BACKGROUND_STOP_1 = Color.decode("#ffffff");

    public static final Color SPEED_UNIT_COLOR = Color.decode("#626262");

    public static final Color LOW_ECO = Color.decode("#d0021b");
    public static final Color HIGH_ECO = Color.decode("#25cb55");

    public static final Color AC_FAN_SPEED_ACTIVE = Color.decode("#7f7f7f");
    public static final Color AC_FAN_SPEED_INACTIVE = Color.decode("#575757");

    public static Color STATUS_BAR_TEXT_COLOR = Color.decode("#000000");

    public static final int ROUNDNESS = 25;

    public static final int MIRROR_RETRACTED = 0;
    public static final int MIRROR_EXTENDED = 1;
    public static final int MIRROR_AUTO = 2;

    public static final int AC_COLD = 0;
    public static final int AC_HOT = 1;

    public final static int GEAR_PARKED = 0;
    public final static int GEAR_REVERSE = 1;
    public final static int GEAR_NEUTRAL = 2;
    public final static int GEAR_DRIVE = 3;
    public final static int GEAR_SPORT = 4;

    public final static int MODE_NORMAL = 0;
    public final static int MODE_CRUISE_CONTROL = 1;
    public final static int MODE_AUTOPILOT = 2;

    public final static int AC_MANUAL = 0;
    public final static int AC_AUTO = 1;

    public class WindowConstants {

        public static final int STATE_SM = 0;
        public static final int STATE_LG = 1;
        public static final int STATE_XL = 2;

        public static final int STATE_IDLE = -1;

    }

}

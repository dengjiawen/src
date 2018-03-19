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
 * The generation of random numbers is too important to be left to chance.
 *
 *-----------------------------------------------------------------------------
 * Constants.java
 *-----------------------------------------------------------------------------
 * This class hosts a bunch of constants that are necessary for program
 * functions.
 *-----------------------------------------------------------------------------
 */

package resources;

import java.awt.*;

public class Constants {

    // COLORS
    public static final Color BACKGROUND_GREY = Color.decode("#141414");
    public static final Color PANEL_BRIGHT = Color.decode("#f0f0f0");

    public static final Color MUSIC_PROGRESS_STOP_0 = Color.decode("#12b5de");
    public static final Color MUSIC_PROGRESS_STOP_1 = Color.decode("#d1dde0");

    public static final Color BATTERY_PROGRESS_STOP_0 = Color.decode("#088700");
    public static final Color BATTERY_PROGRESS_STOP_1 = Color.decode("#a1d600");

    public static final Color BATTERY_DANGER_STOP_0 = Color.decode("#ed1e24");
    public static final Color BATTERY_DANGER_STOP_1 = Color.decode("#f59495");

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

    // INTEGER MODIFIERS
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

    // CONSTANTS REPRESENTING WINDOW STATES
    public class WindowConstants {

        public static final int STATE_SM = 0;
        public static final int STATE_LG = 1;
        public static final int STATE_XL = 2;

        public static final int STATE_IDLE = -1;

    }

}

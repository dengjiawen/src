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
 * A man is smoking a cigarette and blowing smoke rings into the air.
 * His girlfriend becomes irritated with the smoke and says,
 * “Can’t you see the warning on the cigarette pack?
 * Smoking is hazardous to your health!”
 *
 * To which the man replies, “I am a programmer.
 * We don’t worry about warnings; we only worry about errors.”
 *
 *-----------------------------------------------------------------------------
 * InformationService.java
 *-----------------------------------------------------------------------------
 * A background daemon that constantly updates crucial vehicle information,
 * such as battery information and car position.
 * Also acts as the bridge between test program and gear panel and the actual
 * UI, passes variables between these modules.
 *-----------------------------------------------------------------------------
 */

package information;

import resources.Constants;
import test.GearAPFrame;
import test.TestProgram;
import ui.*;
import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class InformationService {

    /**
     * A special type of integer that is mutable.
     * DEPRECATED: limited usage in the program.
     */
    @Deprecated
    public static class ImmutableInt {
        private int integer;

        @Deprecated
        ImmutableInt (int i) {
            integer = i;
        }

        public int getValue () {
            return integer;
        }

        public void change (int value) {
            integer += value;
        }
    }

    // ImmutableInt of climate control temperature for driver and passenger sides
    public static ImmutableInt driver_ac_temp = new ImmutableInt(20);
    public static ImmutableInt passenger_ac_temp = new ImmutableInt(20);

    // A whole bunch of panel references.
    public static StatusBarPanel status_bar_reference;
    public static InstrumentPanel instrument_panel_reference;
    public static GearAPFrame ap_frame_reference;
    public static DrivePanel drive_panel_reference;
    public static TestProgram test_program_reference;
    public static WarningPanelSM battery_warning_reference;
    public static ParkedPanel parked_panel_reference;
    public static BackupCameraXL camera_reference;

    // whether warning of 20% low battery had been given
    // whether warning of 0% low battery had been given
    private static boolean warning_20_given;
    private static boolean warning_critical_given;

    // whether gear warning should be shown
    // whether speed warning should be shown
    // whether follow dist. warning should be shown
    public static boolean show_gear_warning;
    public static boolean show_speed_warning;
    public static boolean show_follow_warning;

    // how long the gear warning had been shown
    private static int gear_warning_time = 0;

    // booleans that records if doors had been locked
    public static boolean left_front_door_locked;
    public static boolean left_back_door_locked;
    public static boolean right_front_door_locked;
    public static boolean right_back_door_locked;

    // whether the car is accelerating
    public static boolean accelerating = false;

    // current gear (PRND) and current mode (AP/Cruise Control)
    public static int drive_gear = Constants.GEAR_PARKED;
    public static int drive_mode = Constants.MODE_NORMAL;

    // current speed and battery %
    public static int speed = 0;
    public static float battery = 100f;

    // butt warmer state (0 = off, 1,2,3 = different heat levels)
    public static int butt_warmer_left_state = 0;
    public static int butt_warmer_right_state = 0;

    // ac temperature mode (Constant.COLD/HOT)
    public static int ac_temp_mode;

    // current state of the mirror
    public static int mirror_state = Constants.MIRROR_RETRACTED;

    // cruise control speed
    // autopilot following dist.
    public static int cruise_control_speed = 10;
    public static int ap_following_distance = 15;

    // y pos. of distant car on DrivePanel
    private static int target_distant_car_y = 10;
    public static int current_distant_car_y = 10;

    // x pos. of distant car on DrivePanel
    private static int target_distant_car_x = 125;
    public static int current_distant_car_x = 125;

    // x pos. of driver's car on DrivePanel
    private static int target_current_car_x = 38;
    public static int current_current_car_x = 38;

    // music volume
    public static double current_volume = 1.0;

    // whether ac is on
    public static boolean ac_is_on = false;

    // ac control mode (manual/auto)
    // ac fan speed
    public static int ac_control_mode = Constants.AC_MANUAL;
    public static int ac_fan_speed = 4;

    /**
     * Short term information update, updates every 1 s
     */
    private static Timer short_term_information_update = new Timer(1000, e -> {
        // update time
        infoUpdateTime();

        // give warning if battery is too low
        if (battery < 20 && !warning_20_given && !test_program_reference.isCharging()) {
            MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, battery_warning_reference);
            warning_20_given = true;
        } else if (battery == 0 && !warning_critical_given && !battery_warning_reference.isVisible() && !test_program_reference.isCharging()) {
            MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, battery_warning_reference);
        }

        // if battery is 0%, disable acceleration, start hazard light, and disable autonomous driving
        if (battery == 0 && !warning_critical_given && !test_program_reference.isCharging()) {
            instrument_panel_reference.startHazard();
            changeMode(Constants.MODE_NORMAL);

            warning_critical_given = true;
        }

        // depending on ac temperature, turn temperature mode to hot/cold
        if (Math.abs(driver_ac_temp.getValue() - passenger_ac_temp.getValue()) > 20 || (driver_ac_temp.getValue() > 20 && passenger_ac_temp.getValue() > 20)) {
            ac_temp_mode = Constants.AC_HOT;
        } else {
            ac_temp_mode = Constants.AC_COLD;
        }

        // only execute the following code if in D/R gear
        if (drive_gear == Constants.GEAR_PARKED || drive_gear == Constants.GEAR_NEUTRAL) return;

        // if speed is too fast, show gear warning when trying to change gears
        if (speed == 0 && show_gear_warning) {
            show_gear_warning = false;
            gear_warning_time = 0;
            RenderingService.invokeRepaint();
        } else if (show_gear_warning && gear_warning_time > 0) {
            gear_warning_time --;
        } else if (show_gear_warning && gear_warning_time == 0) {
            show_gear_warning = false;
        }

    });

    /**
     * Car position update, updates vehicle x/y pos on DrivePanel. Updates every 50 ms.
     * Only ran when car is in drive mode.
     */
    public static Timer short_term_car_position_update = new Timer(50, e -> {

        // do not execute if car is not moving.
        if (speed == 0) {
            return;
        }

        // if speed > 150, warn following dist
        // if speed > 110, show over speed warning
        if (speed > 150) {
            show_follow_warning = true;
        } else if (speed > 110) {
            show_speed_warning = true;
        }

        // turn off warning if necessary
        if (speed <= 150 && speed > 120) {
            show_follow_warning = false;
        } else if (speed <= 120) {
            show_speed_warning = false;
        }

        // increase/decrease x/y values to match target values
        if (target_distant_car_x != current_distant_car_x) {
            if (current_distant_car_x < target_distant_car_x) {
                current_distant_car_x ++;
                RenderingService.invokeRepaint();
            } else if (current_distant_car_x > target_distant_car_x) {
                current_distant_car_x --;
                RenderingService.invokeRepaint();
            }
        }

        if (target_distant_car_y != current_distant_car_y) {
            if (current_distant_car_y < target_distant_car_y) {
                current_distant_car_y ++;
                RenderingService.invokeRepaint();
            } else if (current_distant_car_y > target_distant_car_y) {
                current_distant_car_y --;
                RenderingService.invokeRepaint();
            }
        }

        // randomly shift car position to appear as though car is actually driving
        if (speed < 120) {
            target_distant_car_y = 10 + (int) ((speed / 150f) * 40);
        } else if (target_distant_car_y == current_distant_car_y) {
            if (ThreadLocalRandom.current().nextInt(0, 100) > 98) {
                target_distant_car_y = ThreadLocalRandom.current().nextInt(43, 47);
            } else if (ThreadLocalRandom.current().nextInt(0, 100) > 70) {
                target_distant_car_y = 50;
            }
        }

        if (target_distant_car_x == current_distant_car_x) {
            if (ThreadLocalRandom.current().nextInt(0, 100) > 95) {
                target_distant_car_x = ThreadLocalRandom.current().nextInt(120, 125);
            } else {
                target_distant_car_x = 125;
            }
        }

        if (target_current_car_x != current_current_car_x) {
            if (current_current_car_x < target_current_car_x) {
                current_current_car_x ++;
                RenderingService.invokeRepaint();
            } else if (current_current_car_x > target_current_car_x) {
                current_current_car_x --;
                RenderingService.invokeRepaint();
            }
        } else if (ThreadLocalRandom.current().nextInt(0, 100) > 90) {
            target_current_car_x = ThreadLocalRandom.current().nextInt(35, 41);
        }

    });

    /**
     * Cruise Control, updates every 50 ms.
     * Depending on speed, toggle acceleration on or off.
     */
    private static Timer cruise_control = new Timer(50, e -> {
        if (battery == 0) lowBattery();
        if (speed < cruise_control_speed) test_program_reference.artificialAccel(true);
        else test_program_reference.artificialAccel(false);
    });

    /**
     * Autopilot, updates every 50 ms.
     * Depending on following distance, toggle acceleration on or off.
     */
    private static Timer autopilot = new Timer(50, e -> {
        if (battery == 0) lowBattery();
        if (speed < (50 + 500 / ap_following_distance)) test_program_reference.artificialAccel(true);
        else test_program_reference.artificialAccel(false);
    });

    /**
     * Initialize InformationService
     */
    public static void init (){
        Console.printGeneralMessage("Initializing InformationService");
        short_term_information_update.start();
        short_term_car_position_update.start();
    }

    /**
     * Updates the time
     */
    private static void infoUpdateTime() {
        String time = new SimpleDateFormat("h:mm aa").format(new Date());
        status_bar_reference.updateTime(time);
    }

    /**
     * Lock all doors
     */
    public static void lockAllDoors () {
        left_front_door_locked = true;
        left_back_door_locked = true;

        right_front_door_locked = true;
        right_back_door_locked = true;
    }

    /**
     * Unlock all doors
     */
    public static void unlockAllDoors () {
        left_back_door_locked = false;
        left_front_door_locked = false;

        right_back_door_locked = false;
        right_front_door_locked = false;
    }

    /**
     * Whether all doors are locked
     */
    public static boolean allDoorsLocked () {
        return left_back_door_locked && left_front_door_locked && right_back_door_locked && right_front_door_locked;
    }

    /**
     * Whether all doors are unlocked
     */
    public static boolean allDoorsUnlocked () {
        return !left_front_door_locked && !left_back_door_locked && !right_front_door_locked && !right_back_door_locked;
    }

    /**
     * Method that changes gear
     * @param new_gear  new gear
     */
    public static void changeGear (int new_gear) {

        Console.printGeneralMessage("Gear change request: change to " + new_gear);

        // if speed is not zero, do not change gear, show warning
        if (speed != 0) {
            show_gear_warning = true;
            gear_warning_time = 4;

            Console.printGeneralMessage("Gear change failure: too fast");
            return;
        }

        // if gear is changed from parking, disable charging
        if (drive_gear == Constants.GEAR_PARKED) {
            parked_panel_reference.setCharging(false);
        }

        // if new gear is drive, enable autonomous driving
        if (new_gear == Constants.GEAR_DRIVE) {
            ap_frame_reference.setAutonomousFunctions(true);
        } else {
            ap_frame_reference.setAutonomousFunctions(false);
        }

        drive_gear = new_gear;

        speed = 0;

        // if gear is reverse, show backup camera
        if (drive_gear == Constants.GEAR_REVERSE) {
            MainWindow.window.requestXLPrivilage(camera_reference, true);
        } else if (camera_reference.isVisible()) {
            MainWindow.window.requestXLPrivilage(camera_reference, false);
        }

        // change gear on instrument panel and status bar
        instrument_panel_reference.shiftGear(new_gear);
        status_bar_reference.shiftGear(new_gear);

        Console.printGeneralMessage("Gear change successful.");

    }

    /**
     * Method that change driving mode (Cruise Control/Autopilot)
     * @param new_mode  new mode
     */
    public static void changeMode (int new_mode) {

        Console.printGeneralMessage("Drive mode change request: change to " + new_mode);

        // do not change mode if battery % is 0
        if (battery == 0 && new_mode != Constants.MODE_NORMAL) return;

        drive_mode = new_mode;

        // if cruise control, set min speed to 10
        // if speed is greater than 10, get closest tenth number as target speed
        // stop autopilot of cruise is activated

        // if autopilot, start autopilot
        // stop cruise if autopilot is activated

        // otherwise, stop both services.
        if (drive_mode == Constants.MODE_CRUISE_CONTROL) {
            if (speed < 10) cruise_control_speed = 10;
            else if (speed > 150) cruise_control_speed = 150;
            else cruise_control_speed = speed - (speed % 10);

            cruise_control.start();
            autopilot.stop();
        } else if (drive_mode == Constants.MODE_AUTOPILOT) {
            cruise_control.stop();
            autopilot.start();
        } else {
            cruise_control.stop();
            autopilot.stop();
            ap_frame_reference.setAutonomousFunctions(true);
        }

        drive_panel_reference.updateDrivingMode();
        Console.printGeneralMessage("Drive mode change successful.");
    }

    /**
     * Method that updates the speed
     * (updates the speed and informs InstrumentPanel)
     * @param new_speed new speed
     */
    public static void updateSpeed (int new_speed) {
        speed = new_speed;
        instrument_panel_reference.updateSpeed();
        RenderingService.invokeRepaint();
    }

    /**
     * Method that updates battery %
     * (updates the battery and informs InstrumentPanel)
     * @param new_battery   new battery %
     */
    public static void updateBattery (float new_battery) {
        if (battery == new_battery) return;
        battery = new_battery;
        instrument_panel_reference.updateBatteryProgress();
        RenderingService.invokeRepaint();
    }

    /**
     * Get state of the VolumeIcon
     * @return  integer of the VolumeIcon state
     */
    public static int getVolumeIconState () {
        // return state depending on volume
        if (current_volume > 0.66) {
            return 3;
        } else if (current_volume > 0.33) {
            return 2;
        } else if (current_volume > 0) {
            return 1;
        } else if (current_volume == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Method that sets charging state
     * (If charging, disable battery warning, update ParkedPanel
     * to play charging animation)
     * @param b whether car is charging
     */
    public static void setCharging (boolean b) {

        if (b) {
            if (battery_warning_reference.isVisible())
                MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, battery_warning_reference);
            parked_panel_reference.setCharging(true);

            warning_critical_given = false;
            warning_20_given = false;
        } else {
            parked_panel_reference.setCharging(false);
        }

    }

    /**
     * Method that is called when battery is low
     * Disables autopilot, cruisecontrol, disable autonomous functions
     */
    private static void lowBattery() {

        Console.printGeneralMessage("Battery low: autonomous functions limited.");

        autopilot.stop();
        cruise_control.stop();

        ap_frame_reference.setAutonomousFunctions(false);
        ap_frame_reference.setAutonomousFunctions(true);
    }

}

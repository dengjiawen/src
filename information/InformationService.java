package information;

import com.sun.org.apache.regexp.internal.RE;
import resources.Constants;
import test.GearAPFrame;
import test.TestProgram;
import ui.*;

import javax.sound.midi.Instrument;
import javax.swing.Timer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by freddeng on 2018-03-09.
 */
public class InformationService {

    public static class ImmutableInt {
        private int integer;

        public ImmutableInt (int i) {
            integer = i;
        }

        public int getValue () {
            return integer;
        }

        public void change (int value) {
            integer += value;
        }
    }

    public static ImmutableInt driver_ac_temp = new ImmutableInt(20);
    public static ImmutableInt passenger_ac_temp = new ImmutableInt(20);

    public static StatusBarPanel status_bar_reference;
    public static InstrumentPanel instrument_panel_reference;
    public static GearAPFrame ap_frame_reference;
    public static DrivePanel drive_panel_reference;
    public static TestProgram test_program_reference;
    public static WarningPanelSM battery_warning_reference;
    public static ParkedPanel parked_panel_reference;

    private static boolean warning_20_given;
    private static boolean warning_critical_given;

    public static boolean show_gear_warning;
    public static boolean show_speed_warning;
    public static boolean show_follow_warning;

    public static int gear_warning_time = 0;

    public static boolean left_front_door_locked;
    public static boolean left_back_door_locked;
    public static boolean right_front_door_locked;
    public static boolean right_back_door_locked;

    public static boolean accelerating = false;

    public static int drive_gear = Constants.GEAR_PARKED;
    public static int drive_mode = Constants.MODE_NORMAL;

    public static int speed = 0;
    public static float battery = 100f;

    public static int butt_warmer_left_state = 0;
    public static int butt_warmer_right_state = 0;

    public static int ac_temp_mode;

    public static int mirror_state = Constants.MIRROR_RETRACTED;

    public static int cruise_control_speed = 10;
    public static int ap_following_distance = 15;

    private static int target_distant_car_y = 10;
    public static int current_distant_car_y = 10;

    private static int target_distant_car_x = 125;
    public static int current_distant_car_x = 125;

    private static int target_current_car_x = 38;
    public static int current_current_car_x = 38;

    public static double current_volume = 1.0;

    public static boolean ac_is_on = false;

    public static int ac_control_mode = Constants.AC_MANUAL;
    public static int ac_fan_speed = 4;

    public static Timer short_term_information_update = new Timer(1000,e -> {
        infoUpdateTime();

        if (battery < 20 && !warning_20_given && !test_program_reference.isCharging()) {
            MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, battery_warning_reference);
            warning_20_given = true;
        } else if (battery == 0 && !warning_critical_given && !battery_warning_reference.isVisible() && !test_program_reference.isCharging()) {
            MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, battery_warning_reference);
        }

        if (battery == 0 && !warning_critical_given && !test_program_reference.isCharging()) {
            instrument_panel_reference.startHazard();
            changeMode(Constants.MODE_NORMAL);

            warning_critical_given = true;
        }

        if (Math.abs(driver_ac_temp.getValue() - passenger_ac_temp.getValue()) > 20 || (driver_ac_temp.getValue() > 20 && passenger_ac_temp.getValue() > 20)) {
            ac_temp_mode = Constants.AC_HOT;
        } else {
            ac_temp_mode = Constants.AC_COLD;
        }

        if (drive_gear == Constants.GEAR_PARKED || drive_gear == Constants.GEAR_NEUTRAL) return;

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

    public static Timer short_term_car_position_update = new Timer(50, e -> {

        if (speed == 0) {
            return;
        }

        if (speed > 150) {
            show_follow_warning = true;
        } else if (speed > 110) {
            show_speed_warning = true;
        }

        if (speed <= 150 && speed > 120) {
            show_follow_warning = false;
        } else if (speed <= 120) {
            show_speed_warning = false;
        }

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

    public static Timer cruise_control = new Timer(50, e -> {
        lowBattery();
        if (speed < cruise_control_speed) test_program_reference.artificialAccel(true);
        else test_program_reference.artificialAccel(false);
    });

    public static Timer autopilot = new Timer(50, e -> {
        lowBattery();
        if (speed < (50 + 500 / ap_following_distance)) test_program_reference.artificialAccel(true);
        else test_program_reference.artificialAccel(false);
    });

    public static void init (){
        short_term_information_update.start();
        short_term_car_position_update.start();
    }

    public static void infoUpdateTime () {

        String time = new SimpleDateFormat("h:mm aa").format(new Date());
        status_bar_reference.updateTime(time);

    }

    public static void lockAllDoors () {
        left_front_door_locked = true;
        left_back_door_locked = true;

        right_front_door_locked = true;
        right_back_door_locked = true;
    }

    public static void unlockAllDoors () {
        left_back_door_locked = false;
        left_front_door_locked = false;

        right_back_door_locked = false;
        right_front_door_locked = false;
    }

    public static boolean allDoorsLocked () {
        return left_back_door_locked && left_front_door_locked && right_back_door_locked && right_front_door_locked;
    }

    public static boolean allDoorsUnlocked () {
        return !left_front_door_locked && !left_back_door_locked && !right_front_door_locked && !right_back_door_locked;
    }

    public static void changeGear (int new_gear) {

        if (speed != 0) {
            show_gear_warning = true;
            gear_warning_time = 4;

            return;
        }

        if (drive_gear == Constants.GEAR_PARKED) {
            parked_panel_reference.setCharging(false);
        }

        if (new_gear == Constants.GEAR_DRIVE) {
            ap_frame_reference.setAutonomousFunctions(true);
        } else {
            ap_frame_reference.setAutonomousFunctions(false);
        }

        drive_gear = new_gear;

        speed = 0;

        instrument_panel_reference.shiftGear(new_gear);
        status_bar_reference.shiftGear(new_gear);

    }

    public static void changeMode (int new_mode) {

        if (battery == 0) return;

        drive_mode = new_mode;

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
    }

    public static void updateSpeed (int new_speed) {
        speed = new_speed;
        instrument_panel_reference.updateSpeed();
        RenderingService.invokeRepaint();
    }

    public static void updateBattery (float new_battery) {
        if (battery == new_battery) return;
        battery = new_battery;
        instrument_panel_reference.updateBatteryProgress();
        RenderingService.invokeRepaint();
    }

    public static int getVolumeIconState () {
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

    public static void lowBattery () {
        autopilot.stop();
        cruise_control.stop();

        ap_frame_reference.setAutonomousFunctions(false);
        ap_frame_reference.setAutonomousFunctions(true);
    }

}

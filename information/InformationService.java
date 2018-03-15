package information;

import resources.Constants;
import ui.InstrumentPanel;
import ui.RenderingService;
import ui.StatusBarPanel;

import javax.sound.midi.Instrument;
import javax.swing.Timer;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static boolean left_front_door_locked;
    public static boolean left_back_door_locked;
    public static boolean right_front_door_locked;
    public static boolean right_back_door_locked;

    public static int drive_gear = Constants.GEAR_PARKED;
    public static int drive_mode = Constants.MODE_NORMAL;

    public static int speed = 0;

    public static int butt_warmer_left_state = 0;
    public static int butt_warmer_right_state = 0;

    public static int ac_temp_mode;

    public static int mirror_state = Constants.MIRROR_RETRACTED;

    public static Timer short_term_information_update = new Timer(1000,e -> {
        infoUpdateTime();

        if (Math.abs(driver_ac_temp.getValue() - passenger_ac_temp.getValue()) > 20 || (driver_ac_temp.getValue() > 20 && passenger_ac_temp.getValue() > 20)) {
            ac_temp_mode = Constants.AC_HOT;
        } else {
            ac_temp_mode = Constants.AC_COLD;
        }

    });

    public static void init (){
        short_term_information_update.start();
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

    public static void changeGear (int drive_mode) {

    }

    public static void updateSpeed (int new_speed) {
        speed = new_speed;
        instrument_panel_reference.updateSpeed();
        RenderingService.invokeRepaint();
    }

}

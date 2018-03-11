package information;

import ui.StatusBarPanel;

import javax.swing.Timer;
import java.util.Calendar;

/**
 * Created by freddeng on 2018-03-09.
 */
public class InformationService {

    public static StatusBarPanel status_bar_reference;

    public static Timer information_update = new Timer(1000,e -> {

        infoUpdateTime();

    });

    public static void init () {
        information_update.start();
    }

    public static void infoUpdateTime () {
        Calendar right_now = Calendar.getInstance();
        int hour = right_now.get(Calendar.HOUR_OF_DAY);
        int minute = right_now.get(Calendar.MINUTE);

        String time;

        if (hour == 12) {
            time = 12 + ":" + minute;
        } else {
            time = hour % 12 + ":" + minute;
        }

        if (right_now.get(Calendar.AM_PM) == Calendar.AM) {
            time += " AM";
        } else {
            time += " PM";
        }

        status_bar_reference.updateTime(time);
    }

}

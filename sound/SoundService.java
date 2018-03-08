package sound;

import javax.swing.Timer;

/**
 * Created by freddeng on 2018-03-07.
 */
public class SoundService {

    public static Timer signal_timing_controller = new Timer(500, e -> {
        Resources.signal.play();
    });

    public static void setSignalSound (boolean doSignal) {
        if (doSignal) signal_timing_controller.start();
        else signal_timing_controller.stop();
    }

}

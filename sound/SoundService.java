package sound;

import javax.swing.Timer;

/**
 * Created by freddeng on 2018-03-07.
 */
public class SoundService {

    private static Timer signal_timing_controller = new Timer(295 * 2, e -> {
        Resources.signal.play();
        Resources.signal.play();
        Resources.signal.play();
    });

    public static void setSignalSound (boolean doSignal) {
        if (doSignal && !signal_timing_controller.isRunning()) {
            signal_timing_controller.restart();
        } else signal_timing_controller.stop();
    }

}

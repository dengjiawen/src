/**
 * A class that plays sound effects (primarily the turning signal sounds)
 */

package sound;

import javax.swing.Timer;

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

/**
 * A class hosting sound effects.
 */

package sound;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

class Resources {

    public static Sound signal = TinySound.loadSound(Resources.class.getResource("/resources/resources/sound/signal.wav"), true);

}

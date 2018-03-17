package sound;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

/**
 * Created by freddeng on 2018-03-07.
 */
class Resources {

    public static Sound signal = TinySound.loadSound(Resources.class.getResource("/resources/resources/sound/signal.wav"), true);

}

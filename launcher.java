import information.InformationService;
import kuusisto.tinysound.TinySound;
import music.MusicController;
import ui.LoadFrame;
import ui.MainWindow;
import ui.RenderingService;

/**
 * Created by freddeng on 2018-03-01.
 */
public class launcher {
//Hello

    public static MainWindow window;
    public static void main (String[] args) {

        LoadFrame load = new LoadFrame();

        try {
            Thread.sleep(3000);     // hang the main thread while frame is loaded
        } catch (InterruptedException e) {}

        TinySound.init();
        music.Resources.init();
        resources.Resources.initFont();
        resources.Resources.initImage();
        while (!music.Resources.isInit()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MusicController.init();

        load.setVisible(false);
        load.dispose();
        load = null;

        System.gc();

        window = new MainWindow();
        InformationService.init();
        RenderingService.init(window);
    }

}

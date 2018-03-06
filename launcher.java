import music.MusicController;
import resources.Resources;
import ui.MainWindow;
import ui.RenderingService;

/**
 * Created by freddeng on 2018-03-01.
 */
public class launcher {
//Hello

    public static MainWindow window;
    public static void main (String[] args) {

        Resources.initFont();
        Resources.initImage();
        MusicController.init();

        window = new MainWindow();

        RenderingService.init(window);
    }

}

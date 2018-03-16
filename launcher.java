import information.InformationService;
import kuusisto.tinysound.TinySound;
import music.MusicController;
import test.GearAPFrame;
import test.TestProgram;
import ui.LoadFrame;
import ui.MainWindow;
import ui.RenderingService;

import java.awt.*;

/**
 * Created by freddeng on 2018-03-01.
 */
public class launcher {
//Hello

    public static MainWindow window;

    public static int main_window_x;
    public static int main_window_y;

    public static int acc_window_x;
    public static int acc_window_y;

    private static TestProgram test;
    private static GearAPFrame gear_control;

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

        test = new TestProgram();
        gear_control = new GearAPFrame();

        calcLocation();

        test.reposition(acc_window_x, acc_window_y);
        window.setLocation(main_window_x, main_window_y);
        gear_control.setLocation(acc_window_x, acc_window_y + 250 + 210);

        RenderingService.invokeRepaint();
        test.repaintAll();
        gear_control.validate();
        gear_control.repaint();

    }

    private static void calcLocation () {

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();

        main_window_y = (screen_size.height - window.getHeight())/2;
        acc_window_y = main_window_y;

        int combined_width = 220 + window.getWidth();

        acc_window_x = (screen_size.width - combined_width)/2;
        main_window_x = acc_window_x + 220;
    }

}

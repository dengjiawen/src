package ui;

import java.lang.ref.WeakReference;
import javax.swing.*;

/**
 * Created by freddeng on 2018-03-06.
 */
public class RenderingService {

    public static WeakReference<MainWindow> window;
    public static Timer rendering_service;

    public static boolean doRepaint = false;
    public static int refreshRate = 35;

    public static void init (MainWindow window_reference) {
        window = new WeakReference<>(window_reference);

        rendering_service = new Timer(1000/refreshRate, e -> {

            if (doRepaint) {
                System.out.println(System.nanoTime() + " | Repainted");
                SwingUtilities.invokeLater(() -> {
                    window.get().revalidate();
                    window.get().repaint();
                });
                doRepaint = false;
            }
        });

        rendering_service.start();
    }

    public static void invokeRepaint () {
        doRepaint = true;
    }

}

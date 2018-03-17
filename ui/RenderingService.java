package ui;

import information.Console;

import java.lang.ref.WeakReference;
import javax.swing.*;

/**
 * Created by freddeng on 2018-03-06.
 */
public class RenderingService {

    private static WeakReference<MainWindow> window;
    private static Timer rendering_service;

    private static boolean doRepaint = false;
    private static int refreshRate = 35;

    public static void init (MainWindow window_reference) {
        window = new WeakReference<>(window_reference);

        rendering_service = new Timer(1000/refreshRate, e -> {

            if (doRepaint) {
                Console.printRepaintMessage();
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

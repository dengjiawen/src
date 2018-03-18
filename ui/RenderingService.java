/**
 * Copyright 2018 (C) Jiawen Deng, Ann J.S. and Kareem D. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng, Ann J.S. and Kareem D. (I-LU-V-EH)
 *
 * A guy walks into a bar and asks for 1.4 root beers.
 * The bartender says "I'll have to charge you extra,
 * that's a root beer float".
 * The guy says "In that case, better make it a double."
 *
 *-----------------------------------------------------------------------------
 * RenderingService.java
 *-----------------------------------------------------------------------------
 * A method that handles all repaint events.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import java.lang.ref.WeakReference;
import javax.swing.*;

public class RenderingService {

    private static WeakReference<MainWindow> window;    // a weak reference of the MainWindow
    private static Timer rendering_service;             // a timer that periodically repaints if necessary

    private static boolean doRepaint = false;       // whether a repaint should be triggered
    private static int refreshRate = 35;            // time interval between repaints (35 fps)

    /**
     * Initializes RenderingService
     * @param window_reference  provides a reference of MainWindow
     */
    public static void init (MainWindow window_reference) {
        window = new WeakReference<>(window_reference);

        Console.printGeneralMessage("Starting RenderingService with hook onto " + window_reference.toString());

        // if doRepaint = true, repaint
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

    /**
     * Tells the RendeirngService to repaint by setting doRepaint to true
     */
    public static void invokeRepaint () {
        doRepaint = true;
    }

}

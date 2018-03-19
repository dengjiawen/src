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
 * Some people see a problem and think "I know, I'll use Java!". Now they have a ProblemFactory.
 *
 *-----------------------------------------------------------------------------
 * launcher.java
 *-----------------------------------------------------------------------------
 * The launcher of the software.
 *-----------------------------------------------------------------------------
 */

import information.Console;
import information.InformationService;
import kuusisto.tinysound.TinySound;
import music.MusicController;
import test.GearAPFrame;
import test.TestProgram;
import ui.LoadFrame;
import ui.MainWindow;
import ui.RenderingService;
import java.awt.*;

class launcher {

    private static MainWindow window;   // MainWindow object

    private static int main_window_x;   // x pos of MainWindow
    private static int main_window_y;   // y pos of MainWindow

    private static int acc_window_x;    // x pos of AccessoryWindows
    private static int acc_window_y;    // y pos of AccessoryWindows

    private static TestProgram test;        // test program (AccessoryWindow)
    private static GearAPFrame gear_control;// gear controls (AccessoryWindow

    /**
     * MAIN METHOD
     * @param args
     */
    public static void main (String[] args) {

        Console.printGeneralMessage("Hello World!");
        Console.printGeneralMessage("Tesla Model 3, Concept UI, Console");
        Console.printGeneralMessage("This screen is for logging and development purposes only.");
        Console.printGeneralMessage("Application launched at system time " + System.nanoTime());
        Console.printGeneralMessage("----------------------------------------------------------------------");

        Console.printGeneralMessage("Initializing loading frames");

        // create a load frame
        LoadFrame load = new LoadFrame();

        // hang the main thread while the load frame loads (irony)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}

        Console.printGeneralMessage("Loading resources");
        // initialize tinysound
        // initialize music resources
        // initialize font and image resources
        // wait for the music resources to finish initialization
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
        // initialize music controller
        MusicController.init();

        // dispose loading frame
        load.setVisible(false);
        load.dispose();
        load = null;

        Console.printGeneralMessage("Cleaning up...");
        // collect any cached garbage
        System.gc();

        Console.printGeneralMessage("Initializing main windows");
        // start new MainWindow instance
        window = new MainWindow();
        // start InformationService daemon
        // and hook MainWindow to InfoService
        InformationService.init();
        RenderingService.init(window);

        // initialize test and gear control windows
        test = new TestProgram();
        gear_control = new GearAPFrame();

        // calculate the proper location to place all the windows
        calcLocation();

        // reposition the windows to make them look nice
        test.reposition(acc_window_x, acc_window_y);
        window.setLocation(main_window_x, main_window_y);
        gear_control.setLocation(acc_window_x, acc_window_y + 250 + 210);

        // repaint everything
        RenderingService.invokeRepaint();
        test.repaintAll();
        gear_control.validate();
        gear_control.repaint();

    }

    /**
     * Method that positions the windows depending on screen size
     */
    private static void calcLocation () {

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();

        Console.printGeneralMessage("Calculating window placement. Dimension is " + screen_size.toString());

        main_window_y = (screen_size.height - window.getHeight())/2;
        acc_window_y = main_window_y;

        int combined_width = 220 + window.getWidth();

        acc_window_x = (screen_size.width - combined_width)/2;
        main_window_x = acc_window_x + 220;
    }

}

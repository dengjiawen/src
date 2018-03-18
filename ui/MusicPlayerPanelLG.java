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
 * A foo walks into a bar, takes a look around,
 * and says "Hello World!"
 *
 *-----------------------------------------------------------------------------
 * MusicPlayerPanelLG.java
 *-----------------------------------------------------------------------------
 * The larger version of the music panel
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import resources.Constants;
import resources.Resources;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MusicPlayerPanelLG extends ContainerLG implements NegotiablePanel {

    MusicPlayerPanelSM music_panel_sm;      // reference of the smaller music panel

    private MusicPhoneSubPanel connect;     // MusicPhoneSubPanel

    private boolean show_placeholder;       // show place holder for stream/radio function

    private ModifiedToggleButton stream;    // toggle buttons to trigger streaming, radio, or music on device
    private ModifiedToggleButton radio;
    private ModifiedToggleButton device;

    private GlowButton mini;    // button to return to MusicPlayerSM

    /**
     * Default Constructor
     */
    MusicPlayerPanelLG () {

        super();
        setLayout(null);

        Console.printGeneralMessage("Initializing music player panel (large)");

        // instantiate objects and sub panels
        connect = new MusicPhoneSubPanel();

        // stream, show place holder
        stream = new ModifiedToggleButton(Resources.music_lg_stream, 2, 261, 187, 85, 16);
        stream.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                stream.forceState(1);
                radio.forceState(0);
                device.forceState(0);

                connect.setVisible(false);
                show_placeholder = true;

                RenderingService.invokeRepaint();
            }
        });

        // radio, show place holder
        radio = new ModifiedToggleButton(Resources.music_lg_radio, 2, 388, 184, 55, 22);
        radio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                radio.forceState(1);
                stream.forceState(0);
                device.forceState(0);

                connect.setVisible(false);
                show_placeholder = true;

                RenderingService.invokeRepaint();
            }
        });

        // device, show connect panel
        device = new ModifiedToggleButton(Resources.music_lg_device, 2, 486, 183, 73, 23);
        device.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                device.forceState(1);
                stream.forceState(0);
                radio.forceState(0);

                connect.setVisible(true);
                show_placeholder = false;

                RenderingService.invokeRepaint();
            }
        });

        mini = new GlowButton(Resources.music_mini_button, 493, 384, 212, 26);
        mini.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                MainWindow.window.negotiateTransition(Constants.WindowConstants.STATE_SM, music_panel_sm);
            }
        });

        device.forceState(1);

        add(mini);

        add(connect);

        add(stream);
        add(radio);
        add(device);

        Console.printGeneralMessage("Linking music panel (large) to music panel (small)");
        MusicPlayerPanelSM.panel_lg = this;
        Console.printGeneralMessage("Linkage successful.");

    }

    /**
     * Overriden paintComponent method
     * @param g Abstract Graphics
     */
    @Override
    protected void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS);
        g2d.clip(clip);

        // paint background gradient
        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.MUSIC_LG_GRADIENT_STOP_0, getWidth(), getHeight() - music_panel_sm.getHeight(),
                Constants.MUSIC_LG_GRADIENT_STOP_1);

        g2d.setPaint(primary);
        g2d.fillRect(0, music_panel_sm.getHeight(), getWidth(), getHeight() - music_panel_sm.getHeight());

        // paint background
        g2d.setColor(Constants.MUSIC_LG_SUBPANEL_BACKGROUND);
        g2d.fillRect(0, music_panel_sm.getHeight(), getWidth(), 39);

        // draw fake search bar
        g2d.drawImage(Resources.music_lg_search, 54, 182, 151, 25, null);

        super.paintComponent(g2d);

        // if on stream/radio function, show place holder
        if (show_placeholder) {
            g2d.setColor(Color.black);
            g2d.setFont(Resources.music_lg_favorite_font);

            int x = (getWidth() - g2d.getFontMetrics(Resources.music_lg_favorite_font).stringWidth("THIS FUNCTION IS NOT AVAILABLE IN THIS PROTOTYPE"))/2;
            g2d.drawString("THIS FUNCTION IS NOT AVAILABLE IN THIS PROTOTYPE", x, 305);
        }
    }

    /**
     * Method that updates the music panel sm's invoker when panel is exited
     */
    @Override
    public void updateInvoker() {
        music_panel_sm.updateInvoker();
    }

    /**
     * When inactive, remove the small music panel and add it back onto MainWindow
     * When active, remove small music panel from MainWindow and add it to the top of the UI
     * @param is_active
     */
    @Override
    public void setActive(boolean is_active) {

        if (is_active) {
            MainWindow.window.remove(music_panel_sm);
            music_panel_sm.setMode(Constants.WindowConstants.STATE_LG);
            music_panel_sm.setLocation(0, 0);
            music_panel_sm.setVisible(true);
            music_panel_sm.setActive(true);
            add(music_panel_sm);
            Resources.invokeStatusBarDarkMode(true);
            RenderingService.invokeRepaint();
        }
        else {
            remove(music_panel_sm);
            music_panel_sm.setMode(Constants.WindowConstants.STATE_SM);
            music_panel_sm.setVisible(false);
            music_panel_sm.setActive(false);
            music_panel_sm.setLocation(380, 450);
            MainWindow.window.add(music_panel_sm);
            Resources.invokeStatusBarDarkMode(false);
            RenderingService.invokeRepaint();
        }
    }
}

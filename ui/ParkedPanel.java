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
 * What's the best part about TCP jokes?
 * I get to keep telling them until you get them. (Damn TC Protocols)
 *
 *-----------------------------------------------------------------------------
 * ParkedPanel.java
 *-----------------------------------------------------------------------------
 * A panel that is displayed in InstrumentPanel when the car is parked.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;
import test.TestProgram;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ParkedPanel extends JPanel {

    public static TestProgram test_program_reference;   // reference to the test program

    private ToggleButton fronk;     // toggle button for opening fronk, extending mirror,
    private ToggleButton mirror;    // opening charge port, and opening trunk.
    private ToggleButton charge;
    private ToggleButton trunk;

    private Timer charging_animation_controller;    // timer and int for controlling charging animation
    private int charging_animation_framecount;

    // hard coded UI constants
    private static final int button_width = (int)(0.25 * Resources.button_fronk[0].getWidth());
    private static final int button_height = (int)(0.25 * Resources.button_fronk[0].getHeight());

    /**
     * Default Constructor
     */
    ParkedPanel () {
        super();

        Console.printGeneralMessage("Initializing park panel");

        setBounds(5, 65, 340, 500);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        charging_animation_framecount = 0;

        // instantiate objects (update InformationService when toggles are pressed)
        fronk = new ToggleButton(Resources.button_fronk, 2, (getWidth() - button_width)/2, 130 - button_height + 2,
                button_width, button_height);

        mirror = new ToggleButton(Resources.button_mirror, 2, 40, 200, button_width, button_height);
        mirror.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.mirror_state = (InformationService.mirror_state == Constants.MIRROR_RETRACTED) ? Constants.MIRROR_EXTENDED : Constants.MIRROR_RETRACTED;
            }
        });

        charge = new ToggleButton(Resources.button_charge, 2, 40, 300, button_width, button_height);
        charge.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                test_program_reference.setAllowCharging(!test_program_reference.chargingIsAllowed());
            }
        });

        trunk = new ToggleButton(Resources.button_trunk, 2, (getWidth() - button_width)/2, 370,
                button_width, button_height);

        // instantiate timer to play charging animation.
        charging_animation_controller = new Timer(1000/24, e -> {

            if (InformationService.battery == 100) {
                charging_animation_framecount = 124;
                RenderingService.invokeRepaint();
                return;
            }

            charging_animation_framecount = (charging_animation_framecount == 124) ? 0 : charging_animation_framecount + 1;
            RenderingService.invokeRepaint();
        });

        add(fronk);
        add(mirror);
        add(charge);
        add(trunk);

        Console.printGeneralMessage("Linking park panel to InformationService");
        InformationService.parked_panel_reference = this;
        Console.printGeneralMessage("Linkage successful.");

    }

    /**
     * Overriden setVisible method
     * (if set invisible, reset fronk trunk and charge icons, as well as disable charging)
     * @param b
     */
    @Override
    public void setVisible (boolean b) {
        super.setVisible(b);

        if (!b) {
            fronk.forceState(0);
            trunk.forceState(0);
            charge.forceState(0);

            test_program_reference.setAllowCharging(false);
        }
    }

    /**
     * Overriden paintComponent method
     * @param g Abstract Graphics
     */
    @Override
    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (InformationService.mirror_state == Constants.MIRROR_RETRACTED && mirror.getState() != 0) {
            mirror.forceState(0);
        } else if (InformationService.mirror_state != Constants.MIRROR_RETRACTED && mirror.getState() != 1) {
            mirror.forceState(1);
        }

        g2d.drawImage(Resources.car_icon_fronk[0], (getWidth() - (int)(0.13 * Resources.car_icon_fronk[0].getWidth()))/2, 130,
                (int)(0.13 * Resources.car_icon_fronk[0].getWidth()), (int)(0.13 * Resources.car_icon_fronk[0].getHeight()), null);

        if (charging_animation_controller.isRunning()) {
            g2d.drawImage(AdditionalResources.charging_animation[charging_animation_framecount], 253, 225, 41, 41, null);
        }

    }

    /**
     * Method that starts and stops the charging animation.
     * @param b
     */
    public void setCharging (boolean b) {
        if (b) charging_animation_controller.start();
        else {
            charging_animation_controller.stop();
            charging_animation_framecount = 0;
        }

        RenderingService.invokeRepaint();
    }

}

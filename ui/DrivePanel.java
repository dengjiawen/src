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
 * //Roses are red
 * //Violets are blue
 * abstract class Poem {
 * //TODO
 * }
 *
 *-----------------------------------------------------------------------------
 * DrivePanel.java
 *-----------------------------------------------------------------------------
 * A panel for controlling UI elements for when the car is in DRIVE (D) mode.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;
import javax.swing.*;
import java.awt.*;

public class DrivePanel extends JPanel {

    private AutoPilotAdjustor adjustor;    // for adjusting speed/following dist of cruise control + autopilot

    /**
     * Default Constructor
     */
    DrivePanel() {
        super();

        Console.printGeneralMessage("Initializing drive panel module");

        setBounds(5, 155, 340, 320);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        // adjustor should not be visible until car is in D mode.
        adjustor = new AutoPilotAdjustor();
        adjustor.setVisible(false);

        add(adjustor);

        Console.printGeneralMessage("Linking drive panel module to InformationService");
        InformationService.drive_panel_reference = this;
        Console.printGeneralMessage("Linkage successful.");

    }

    /**
     * Update the driving mode; display adjustor when switching to
     * autopilot/cruise control.
     */
    public void updateDrivingMode () {
        if (InformationService.drive_mode == Constants.MODE_NORMAL) {
            adjustor.setVisible(false);
        } else {
            adjustor.setVisible(true);
        }

        RenderingService.invokeRepaint();
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

        super.paintComponent(g2d);

        // draw the ap guide, the tesla 3 cars at the proper location as dictated by InformationService
        g2d.drawImage(Resources.ap_guide, (getWidth() - 204)/2, 70, 204, 201, null);
        g2d.drawImage(Resources.tesla_3_rear, InformationService.current_distant_car_x, InformationService.current_distant_car_y,
                (int)((((float)InformationService.current_distant_car_y)/(getHeight() - 215)) * 264),
                (int)((((float)InformationService.current_distant_car_y)/(getHeight() - 215)) * 192), null);

        g2d.drawImage(Resources.ap_shielding, (getWidth() - 303)/2, getHeight() - 110, 303, 103, null);
        g2d.drawImage(Resources.ap_shielding_down, (getWidth() - 303)/2, 5, 303, 120, null);

        g2d.drawImage(Resources.tesla_3_rear, InformationService.current_current_car_x, getHeight() - 215, 264, 192, null);

        // draw speed limit sign
        g2d.drawImage(Resources.speed_limit, 40, 30, 38, 51, null);

        // draw autopilot/cruise control icon
        if (InformationService.drive_mode == Constants.MODE_AUTOPILOT) {
            g2d.drawImage(Resources.ap_icon, 280, 30, 27, 27, null);
        } else if (InformationService.drive_mode == Constants.MODE_CRUISE_CONTROL) {
            g2d.drawImage(Resources.cruise_icon, 280, 30, 29, 25, null);
        }

        // show any warning if necessary
        if (InformationService.show_gear_warning)
            g2d.drawImage(AdditionalResources.gear_warning, (getWidth() - (int)(0.25 * AdditionalResources.gear_warning.getWidth()))/2,
                    getHeight() - 200, (int)(0.25 * AdditionalResources.gear_warning.getWidth()),
                    (int)(0.25 * AdditionalResources.gear_warning.getHeight()), null);
        else if (InformationService.show_follow_warning)
            g2d.drawImage(AdditionalResources.follow_warning, (getWidth() - (int)(0.25 * AdditionalResources.follow_warning.getWidth()))/2,
                    getHeight() - 200, (int)(0.25 * AdditionalResources.follow_warning.getWidth()),
                    (int)(0.25 * AdditionalResources.follow_warning.getHeight()), null);
        else if (InformationService.show_speed_warning)
            g2d.drawImage(AdditionalResources.speed_warning, (getWidth() - (int)(0.25 * AdditionalResources.speed_warning.getWidth()))/2,
                    getHeight() - 200, (int)(0.25 * AdditionalResources.speed_warning.getWidth()),
                    (int)(0.25 * AdditionalResources.speed_warning.getHeight()), null);

    }

}

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
 *
 *-----------------------------------------------------------------------------
 * DrivePanel.java
 *-----------------------------------------------------------------------------
 * A panel for controlling UI elements for when the car is in DRIVE (D) mode.
 *-----------------------------------------------------------------------------
 */

package ui;

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
    public DrivePanel() {
        super();

        setBounds(5, 155, 340, 320);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        adjustor = new AutoPilotAdjustor();
        adjustor.setVisible(false);

        add(adjustor);

        InformationService.drive_panel_reference = this;

    }

    public void paintComponent (Graphics g) {

        super.paintComponent(g);

        g.drawImage(Resources.ap_guide, (getWidth() - 204)/2, 70, 204, 201, null);
        g.drawImage(Resources.tesla_3_rear, InformationService.current_distant_car_x, InformationService.current_distant_car_y,
                (int)((((float)InformationService.current_distant_car_y)/(getHeight() - 215)) * 264),
                (int)((((float)InformationService.current_distant_car_y)/(getHeight() - 215)) * 192), null);

        g.drawImage(Resources.ap_shielding, (getWidth() - 303)/2, getHeight() - 110, 303, 103, null);
        g.drawImage(Resources.ap_shielding_down, (getWidth() - 303)/2, 5, 303, 120, null);

        g.drawImage(Resources.tesla_3_rear, InformationService.current_current_car_x, getHeight() - 215, 264, 192, null);

        g.drawImage(Resources.speed_limit, 40, 30, 38, 51, null);

        if (InformationService.drive_mode == Constants.MODE_AUTOPILOT) {
            g.drawImage(Resources.ap_icon, 280, 30, 27, 27, null);
        } else if (InformationService.drive_mode == Constants.MODE_CRUISE_CONTROL) {
            g.drawImage(Resources.cruise_icon, 280, 30, 29, 25, null);
        }

        if (InformationService.show_gear_warning)
            g.drawImage(AdditionalResources.gear_warning, (getWidth() - (int)(0.25 * AdditionalResources.gear_warning.getWidth()))/2,
                    getHeight() - 200, (int)(0.25 * AdditionalResources.gear_warning.getWidth()),
                    (int)(0.25 * AdditionalResources.gear_warning.getHeight()), null);
        else if (InformationService.show_follow_warning)
            g.drawImage(AdditionalResources.follow_warning, (getWidth() - (int)(0.25 * AdditionalResources.follow_warning.getWidth()))/2,
                    getHeight() - 200, (int)(0.25 * AdditionalResources.follow_warning.getWidth()),
                    (int)(0.25 * AdditionalResources.follow_warning.getHeight()), null);
        else if (InformationService.show_speed_warning)
            g.drawImage(AdditionalResources.speed_warning, (getWidth() - (int)(0.25 * AdditionalResources.speed_warning.getWidth()))/2,
                    getHeight() - 200, (int)(0.25 * AdditionalResources.speed_warning.getWidth()),
                    (int)(0.25 * AdditionalResources.speed_warning.getHeight()), null);

    }

    public void updateDrivingMode () {
        if (InformationService.drive_mode == Constants.MODE_NORMAL) {
            adjustor.setVisible(false);
        } else {
            adjustor.setVisible(true);
        }

        RenderingService.invokeRepaint();
    }

}

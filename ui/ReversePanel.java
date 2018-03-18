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
 * A programmer puts two glasses on his bedside table before going to sleep.
 * A full one, in case he gets thirsty, and an empty one, in case he doesn’t.
 *
 * (Universe.NullPointerException: Cannot find glass.)
 *
 *-----------------------------------------------------------------------------
 * ReversePanel.java
 *-----------------------------------------------------------------------------
 * A panel that is shown in the InstrumentPanel when car is in reverse gear.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import resources.AdditionalResources;
import resources.Resources;
import javax.swing.*;
import java.awt.*;

class ReversePanel extends JPanel {

    /**
     * Default Constructor
     */
    ReversePanel() {
        super();

        Console.printGeneralMessage("Initializing reverse panel");

        setBounds(5, 155, 340, 320);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

    }

    /**
     * Overriden paintComponent method
     * @param g Abstract Graphics
     */
    @Override
    public void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g2d);

        g2d.drawImage(Resources.tesla_3_rear, (getWidth() - 264)/2, getHeight() - 215, 264, 192, null);

        // show gear warning if necessary
        if (InformationService.show_gear_warning)
            g2d.drawImage(AdditionalResources.gear_warning, (getWidth() - (int)(0.25 * AdditionalResources.gear_warning.getWidth()))/2,
                    getHeight() - 200, (int)(0.25 * AdditionalResources.gear_warning.getWidth()),
                    (int)(0.25 * AdditionalResources.gear_warning.getHeight()), null);

    }

}

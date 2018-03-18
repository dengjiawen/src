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
 * Some people (me), when confronted with a problem, think,
 * 'I know, I'll use threads' - and then two they hav erpoblesms.
 *
 *-----------------------------------------------------------------------------
 * NeutralPanel.java
 *-----------------------------------------------------------------------------
 * A panel that is shown when the car is in neutral.
 *-----------------------------------------------------------------------------
 */

package ui;

import resources.AdditionalResources;
import resources.Resources;
import javax.swing.*;
import java.awt.*;

class NeutralPanel extends JPanel {

    /**
     * Default Constructor
     */
    NeutralPanel() {
        super();

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

        // draw tesla graphics, and show that parking break is off.
        g2d.drawImage(Resources.tesla_3_rear, (getWidth() - 264)/2, getHeight() - 215, 264, 192, null);
        g2d.drawImage(AdditionalResources.parking_brake, (getWidth() - 100)/2, 50, 100, 54, null);

    }

}

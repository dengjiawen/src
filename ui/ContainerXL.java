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
 * Roses are red,
 * you may think you have won,
 * but everyone knows,
 * arrays start at negative one.
 *
 *-----------------------------------------------------------------------------
 * ContainerXL.java
 *-----------------------------------------------------------------------------
 * The basis/template for all UI objects inheriting ContainerXL.
 * All inherited object are placed in zone 1.
 * Only the BackupCameraXL and MapPanelXL inherit this object.
 *-----------------------------------------------------------------------------
 */

package ui;

import resources.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class ContainerXL extends JPanel {

    private static final int panel_width_expanded = 725;
    private static final int panel_height_expanded = 605;

    /**
     * Default Constructor
     */
    ContainerXL() {

        setBounds(380,20, panel_width_expanded, panel_height_expanded);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);

    }

    /**
     * Overriden paintComponent method
     * @param g Abstract Graphics
     */
    @Override
    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS));
    }

}

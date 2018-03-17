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
 * array is old,
 * linked list is great,
 * where is my gold?
 *
 *-----------------------------------------------------------------------------
 * ContainerSM.java
 *-----------------------------------------------------------------------------
 * The basis/template for all UI objects inheriting ContainerSM.
 * All inherited object are placed in zone 3.
 *-----------------------------------------------------------------------------
 */

package ui;

import resources.Constants;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class ContainerSM extends JPanel {

    static final int panel_width = 725;
    static final int panel_height = 175;

    /**
     * Default Constructor
     */
    ContainerSM() {
        super();

        setBounds(380,450, panel_width ,panel_height);
        setBackground(new Color(0,0,0,0));

    }

    /**
     * Overriden paintComponent class
     * @param g Abstract Graphics
     */
    @Override
    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Constants.PANEL_BRIGHT);
        g2d.fill(new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS));

    }

}

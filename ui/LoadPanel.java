/**
 * Copyright 2018 (C) Jiawen Deng. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng.
 *
 * These methods are recycled from the CrazyEights project.
 *
 *-----------------------------------------------------------------------------
 * LoadPanel.java
 *-----------------------------------------------------------------------------
 * This class contains the GUI that is displayed when resources are being
 * loaded.
 *-----------------------------------------------------------------------------
 */

package ui;

import javax.swing.*;
import java.awt.*;

public class LoadPanel extends JPanel {

    private static final int WIDTH = 500;   // hard coded width
    private static final int HEIGHT = 50;  // hard coded height

    // hard coded parameters
    private static final int LOGO_WIDTH = 200;
    private static final int LOGO_X = (int)((WIDTH - LOGO_WIDTH) / 2f);
    private static final int LOGO_Y = 25;

    // JLabel that shows the loading progress
    private JLabel loadStatus;

    /**
     * Default Constructor
     */
    public LoadPanel() {

        setBounds(0, 0, WIDTH, HEIGHT);
        setBackground(Color.black);
        setLayout(null);
        setOpaque(false);

        loadStatus = new JLabel("Loading Assets...");
        loadStatus.setForeground(Color.white);
        loadStatus.setBounds(10, 10, 500, 40);

        add(loadStatus);

        setVisible(true);

    }

    /**
     * Updates the asset being loaded
     * @param asset_name    asset name
     */
    public void updateLoadedAsset (String asset_name) {
        loadStatus.setText ("Asset " + asset_name + " loaded.");
        repaint();
    }

    /**
     * Updates the name of the constants being loaded
     * @param constant_name constant name
     */
    @ Deprecated
    public void updateLoadedConstants (String constant_name) {
        loadStatus.setText ("Constant " + constant_name + " loaded.");
    }

}

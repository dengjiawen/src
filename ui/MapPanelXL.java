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
 * ["hip","hip"]
 * (hip hip array!)
 *
 *-----------------------------------------------------------------------------
 * MapPanelXL.java
 *-----------------------------------------------------------------------------
 * A humongous, non-functional map panel.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MapPanelXL extends ContainerXL implements NegotiablePanel {

    private GlowButton zoom_in;     // zoom in button
    private GlowButton zoom_out;    // zoom out button

    /**
     * Default Constructor
     */
    MapPanelXL () {

        Console.printGeneralMessage("Initializing full screen map panel");

        setLayout(null);

        zoom_in = new GlowButton(AdditionalResources.map_zoom_in, 649, 52, 43, 42);
        zoom_out = new GlowButton(AdditionalResources.map_zoom_out, 649, 94, 43, 46);

        add(zoom_in);
        add(zoom_out);

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

        g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS));
        g2d.drawImage(Resources.map_LG, 0, 0, (int)(0.6 * Resources.map_LG.getWidth()), (int)(0.6 * Resources.map_LG.getHeight()), null);

        g2d.drawImage(AdditionalResources.map_nav_bar, 27, 52, 179, 45, null);
        g2d.drawImage(AdditionalResources.map_compass, 641, 526, 58, 58, null);
    }

    // overriden methods for NegotiablePanel interface
    @Override
    public void setActive(boolean is_active) {}

    @Override
    public void updateInvoker() {}
}

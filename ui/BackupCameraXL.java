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
 * violets are blue,
 * why didn't we loop
 * at a power of 2?
 *
 *-----------------------------------------------------------------------------
 * BackupCameraXL.java
 *-----------------------------------------------------------------------------
 * A placeholder panel for the back up camera when in reverse gear.
 *-----------------------------------------------------------------------------
 */

package ui;

import information.Console;
import information.InformationService;
import resources.Constants;
import resources.Resources;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class BackupCameraXL extends ContainerXL implements NegotiablePanel {

    /**
     * Default Constructor
     */
    BackupCameraXL () {
        Console.printGeneralMessage("Initializing back up camera");
        Console.printGeneralMessage("Linking back up camera to InformationService");
        InformationService.camera_reference = this;
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

        g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS));

        // paint background gradient color
        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.MUSIC_LG_GRADIENT_STOP_0, getWidth(), getHeight(),
                Constants.MUSIC_LG_GRADIENT_STOP_1);

        g2d.setPaint(primary);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // set proper color and font for placeholder text
        g2d.setColor(Color.black);
        g2d.setFont(Resources.music_lg_favorite_font);

        g2d.drawString("REAR VIEW CAMERA UNAVAILABLE IN PROTOTYPE", (getWidth() -
            g2d.getFontMetrics(Resources.music_lg_favorite_font).stringWidth("REAR VIEW CAMERA UNAVAILABLE IN PROTOTYPE"))/2, getHeight()/2);
    }

    // Overriden methods for NegotiablePanel interface
    @Override
    public void setActive(boolean is_active) {}

    @Override
    public void updateInvoker() {}
}

package ui;

import information.InformationService;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-13.
 */
public class BackupCameraXL extends ContainerXL implements NegotiablePanel {

    public BackupCameraXL () {
        InformationService.camera_reference = this;
    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS));

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.MUSIC_LG_GRADIENT_STOP_0, getWidth(), getHeight(),
                Constants.MUSIC_LG_GRADIENT_STOP_1);

        g2d.setPaint(primary);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.black);
        g2d.setFont(Resources.music_lg_favorite_font);

        g2d.drawString("REAR VIEW CAMERA UNAVAILABLE IN PROTOTYPE", (getWidth() -
            g2d.getFontMetrics(Resources.music_lg_favorite_font).stringWidth("REAR VIEW CAMERA UNAVAILABLE IN PROTOTYPE"))/2, getHeight()/2);
    }

    @Override
    public void setActive(boolean is_active) {

    }

    @Override
    public void updateInvoker() {

    }
}

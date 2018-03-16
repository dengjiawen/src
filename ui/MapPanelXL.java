package ui;

import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-13.
 */
public class MapPanelXL extends ContainerXL implements NegotiablePanel {

    GlowButton zoom_in;
    GlowButton zoom_out;

    public MapPanelXL () {

        setLayout(null);

        zoom_in = new GlowButton(AdditionalResources.map_zoom_in, 649, 52, 43, 42);
        zoom_out = new GlowButton(AdditionalResources.map_zoom_out, 649, 94, 43, 46);

        add(zoom_in);
        add(zoom_out);

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS));
        g2d.drawImage(Resources.map_LG, 0, 0, (int)(0.6 * Resources.map_LG.getWidth()), (int)(0.6 * Resources.map_LG.getHeight()), null);

        g2d.drawImage(AdditionalResources.map_nav_bar, 27, 52, 179, 45, null);
        g2d.drawImage(AdditionalResources.map_compass, 641, 526, 58, 58, null);
    }

    @Override
    public void setActive(boolean is_active) {

    }

    @Override
    public void updateInvoker() {

    }
}

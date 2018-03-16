package ui;

import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-06.
 */
public class MapPanelLG extends ContainerLG implements NegotiablePanel {

    GlowButton zoom_in;
    GlowButton zoom_out;

    public MapPanelLG () {

        setLayout(null);

        zoom_in = new GlowButton(AdditionalResources.map_zoom_in, 649, 52, 43, 42);
        zoom_out = new GlowButton(AdditionalResources.map_zoom_out, 649, 94, 43, 46);

        add(zoom_in);
        add(zoom_out);

    }

    protected void paintComponent (Graphics g) {

        g.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS));
        g.drawImage(Resources.map_LG, 0, 0, (int)(0.4 * Resources.map_LG.getWidth()), (int)(0.4 * Resources.map_LG.getHeight()), null);

        g.drawImage(AdditionalResources.map_nav_bar, 27, 52, 179, 45, null);
        g.drawImage(AdditionalResources.map_compass, 641, 337, 58, 58, null);

        super.paintComponent(g);
    }

    @Override
    public void updateInvoker() {

    }

    @Override
    public void setActive(boolean is_active) {

    }
}

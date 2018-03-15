package ui;

import resources.Constants;
import resources.Resources;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-13.
 */
public class MapPanelSM extends ContainerSM implements NegotiablePanel {

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS));
        g2d.drawImage(Resources.map_LG, 0, 0, (int)(0.4 * Resources.map_LG.getWidth()), (int)(0.4 * Resources.map_LG.getHeight()), null);
    }

    @Override
    public void updateInvoker() {

    }

    @Override
    public void setActive(boolean is_active) {

    }

}

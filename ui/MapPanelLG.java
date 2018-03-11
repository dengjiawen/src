package ui;

import resources.Constants;
import resources.Resources;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-06.
 */
public class MapPanelLG extends ContainerLG {

    protected void paintComponent (Graphics g) {

        g.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.roundness, Constants.roundness));
        g.drawImage(Resources.map_LG, 0, 0, (int)(0.4 * Resources.map_LG.getWidth()), (int)(0.4 * Resources.map_LG.getHeight()), null);

        super.paintComponent(g);
    }

}

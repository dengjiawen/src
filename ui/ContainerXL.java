package ui;

import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-01.
 */
public class ContainerXL extends JPanel {

    private static final int panel_width_expanded = 725;
    private static final int panel_height_expanded = 605;

    public ContainerXL() {

        setBounds(380,20, panel_width_expanded, panel_height_expanded);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS));
    }

}

package ui;

import resources.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-01.
 */
public class ContainerLG extends JPanel {

    private static final int panel_width_expanded = 725;
    private static final int panel_height_expanded = 605;

    private static final int panel_width_contracted = 725;
    private static final int panel_height_contracted = 400;

    public ContainerLG () {
        super();

        setBounds(380,20, panel_width_contracted ,panel_height_contracted);
        setBackground(new Color(0,0,0,0));

    }

    public void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Constants.panel_bright);
        g2d.fill(new RoundRectangle2D.Double(0, 0, panel_width_contracted, panel_height_contracted, Constants.roundness, Constants.roundness));

    }

}

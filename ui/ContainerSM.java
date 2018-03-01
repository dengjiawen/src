package ui;

import resources.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-01.
 */
public class ContainerSM extends JPanel {

    private static final int panel_width = 725;
    private static final int panel_height = 195;

    public ContainerSM () {
        super();

        setBounds(380,430, panel_width ,panel_height);
        setBackground(new Color(0,0,0,0));

    }

    public void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Constants.panel_bright);
        g2d.fill(new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.roundness, Constants.roundness));

    }

}

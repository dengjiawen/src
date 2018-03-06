package ui;

import resources.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-01.
 */
public class ContainerSM extends JPanel {

    public static final int panel_width = 725;
    public static final int panel_height = 195;

    public ContainerSM () {
        super();

        setBounds(380,430, panel_width ,panel_height);
        setBackground(new Color(0,0,0,0));

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Constants.panel_bright);
        g2d.fill(new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.roundness, Constants.roundness));

    }

}

/**
 * Created by freddeng on 2018-03-01.
 */

package ui;

import resources.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class InstrumentPanel extends JPanel {

    public InstrumentPanel () {
        super();

        setBounds(20,20,350,605);
        setBackground(new Color(0,0,0,0));

    }

    public void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
//
        g2d.setPaint(Constants.panel_bright);
        g2d.fill(new RoundRectangle2D.Double(0, 0, 350, 605, Constants.roundness, Constants.roundness));

    }

}

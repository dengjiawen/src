package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-01.
 */
public class InstrumentPanel extends JPanel {

    public InstrumentPanel () {

        setBounds(0,0,100,100);

    }

    public void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.WHITE);
        g2d.setPaint(Color.WHITE);
        g2d.draw(new RoundRectangle2D() {
        }.Double(x, y, w, h, 50, 50));

    }

}

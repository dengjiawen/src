package ui;

import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-02.
 */
public class OvalButton extends JLabel {

    public OvalButton (int x, int y, int width, int height, String text) {
        super();

        setBackground(new Color(0,0,0,0));

        setBounds(x, y, width, height);
        setFont(Resources.oval_button_font);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        setText(text);

        repaint();

    }

    public void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Constants.button_bright);
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));

        super.paintComponent(g);
    }

}

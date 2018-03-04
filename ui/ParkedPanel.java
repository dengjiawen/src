package ui;

import resources.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ann on 2018-03-01.
 */
public class ParkedPanel extends JPanel {

    public ParkedPanel () {
        super();

        setBounds(5, 70, 340, 500);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        OvalButton button1 = new OvalButton((getWidth() - 70) / 2,173,70,30, "OPEN");
        OvalButton button2 = new OvalButton((getWidth() - 70) / 2, 405, 70, 30, "OPEN");

        add(button1);
        add(button2);
    }

    public void paintComponent (Graphics g) {
        g.drawImage(Resources.car_icon_top, (getWidth() - (int)(0.23 * Resources.car_icon_top.getWidth()))/2, 205,
                (int)(0.23 * Resources.car_icon_top.getWidth()), (int)(0.23 * Resources.car_icon_top.getHeight()), null);
    }

}

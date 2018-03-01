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

        setBounds(5, 50, 340, 500);
        setBackground(new Color(0, 0, 0, 0));
    }

    public void paintComponent (Graphics g) {
        g.drawImage(Resources.car_icon_top, (getWidth() - (int)(0.30 * Resources.car_icon_top.getWidth()))/2, 150,
                (int)(0.30 * Resources.car_icon_top.getWidth()), (int)(0.30 * Resources.car_icon_top.getHeight()), null);
    }

}

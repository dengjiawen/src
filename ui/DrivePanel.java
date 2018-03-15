package ui;

import information.InformationService;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Ann on 2018-03-01.
 */
public class DrivePanel extends JPanel {

    Point current_car_pos;
    Point distant_car_pos;

    public DrivePanel() {
        super();

        setBounds(5, 155, 340, 320);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);




    }

    public void paintComponent (Graphics g) {

        super.paintComponent(g);

        g.drawImage(Resources.ap_guide, (getWidth() - 204)/2, 70, 204, 201, null);
        g.drawImage(Resources.tesla_3_rear, (getWidth() - 91)/2, 50, 91, 66, null);

        g.drawImage(Resources.ap_shielding, (getWidth() - 303)/2, getHeight() - 110, 303, 103, null);
        g.drawImage(Resources.ap_shielding_down, (getWidth() - 303)/2, 20, 303, 103, null);

        g.drawImage(Resources.tesla_3_rear, (getWidth() - 264)/2, getHeight() - 215, 264, 192, null);

        g.drawImage(Resources.speed_limit, 40, 30, 38, 51, null);

    }

}

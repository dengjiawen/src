package ui;

import resources.AdditionalResources;
import resources.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ann on 2018-03-01.
 */
class NeutralPanel extends JPanel {

    public NeutralPanel() {
        super();

        setBounds(5, 155, 340, 320);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

    }

    public void paintComponent (Graphics g) {

        super.paintComponent(g);

        g.drawImage(Resources.tesla_3_rear, (getWidth() - 264)/2, getHeight() - 215, 264, 192, null);
        g.drawImage(AdditionalResources.parking_brake, (getWidth() - 100)/2, 50, 100, 54, null);

    }

}

package ui;

import information.InformationService;
import resources.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ann on 2018-03-01.
 */
public class ReversePanel extends JPanel {

    public ReversePanel() {
        super();

        setBounds(5, 155, 340, 320);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

    }

    public void paintComponent (Graphics g) {

        super.paintComponent(g);

        g.drawImage(Resources.tesla_3_rear, (getWidth() - 264)/2, getHeight() - 215, 264, 192, null);

    }

}

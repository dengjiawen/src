package ui;

import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * Created by freddeng on 2018-03-09.
 */
public class CoreControlBarPanel extends JPanel {

    public CoreControlBarPanel () {

        setBounds(20, 720 - 10 - (720 - 20 - 10 - 10 - 605), 1125 - 40, 720 - 20 - 10 - 10 - 605);
        setBackground(Constants.background_grey);

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(Resources.fan[1], (getWidth() - 80)/2, -10,
                80, 80, null);

    }

}

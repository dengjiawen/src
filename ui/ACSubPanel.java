package ui;

import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by freddeng on 2018-03-14.
 */
public class ACSubPanel extends JPanel {

    Point dragger_position;

    public ACSubPanel (int x, int y) {

        setBounds(x, y, 236, 175);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);
        setLayout(null);

        dragger_position = new Point(115, 89);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                dragger_position.x = e.getX() - 36;
                dragger_position.y = e.getY() - 23;

                if (dragger_position.x < 0) dragger_position.x = 0;
                if (dragger_position.y < 0) dragger_position.y = 0;

                if (dragger_position.x + 72 > 236) dragger_position.x = getWidth() - 75;
                if (dragger_position.y + 45 > 175) dragger_position.y = getHeight() - 45;

                RenderingService.invokeRepaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                dragger_position.x = e.getX() - 36;
                dragger_position.y = e.getY() - 23;

                if (dragger_position.x < 0) dragger_position.x = 0;
                if (dragger_position.y < 0) dragger_position.y = 0;

                if (dragger_position.x + 72 > 236) dragger_position.x = getWidth() - 75;
                if (dragger_position.y + 45 > 175) dragger_position.y = getHeight() - 45;

                RenderingService.invokeRepaint();

            }
        });

    }

    protected void paintComponent (Graphics g) {

        g.drawImage(Resources.ac_dragger, dragger_position.x, dragger_position.y, 73, 45, null);

    }

}

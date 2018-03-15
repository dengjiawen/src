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
public class ParkedPanel extends JPanel {

    ToggleButton fronk;
    ToggleButton mirror;
    ToggleButton charge;
    ToggleButton trunk;

    static final int button_width = (int)(0.25 * Resources.button_fronk[0].getWidth());
    static final int button_height = (int)(0.25 * Resources.button_fronk[0].getHeight());

    public ParkedPanel () {
        super();

        setBounds(5, 65, 340, 500);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        fronk = new ToggleButton(Resources.button_fronk, 2, (getWidth() - button_width)/2, 130 - button_height + 2,
                button_width, button_height);

        mirror = new ToggleButton(Resources.button_mirror, 2, 40, 200, button_width, button_height);
        mirror.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.mirror_state = (InformationService.mirror_state == Constants.MIRROR_RETRACTED) ? Constants.MIRROR_EXTENDED : Constants.MIRROR_RETRACTED;
            }
        });

        charge = new ToggleButton(Resources.button_charge, 2, 40, 300, button_width, button_height);
        trunk = new ToggleButton(Resources.button_trunk, 2, (getWidth() - button_width)/2, 370,
                button_width, button_height);

        add(fronk);
        add(mirror);
        add(charge);
        add(trunk);

    }

    public void paintComponent (Graphics g) {

        if (InformationService.mirror_state == Constants.MIRROR_RETRACTED && mirror.getState() != 0) {
            mirror.forceState(0);
        } else if (InformationService.mirror_state != Constants.MIRROR_RETRACTED && mirror.getState() != 1) {
            mirror.forceState(1);
        }

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(Resources.car_icon_fronk[0], (getWidth() - (int)(0.13 * Resources.car_icon_fronk[0].getWidth()))/2, 130,
                (int)(0.13 * Resources.car_icon_fronk[0].getWidth()), (int)(0.13 * Resources.car_icon_fronk[0].getHeight()), null);
    }

}

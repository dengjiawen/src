package ui;

import information.InformationService;
import resources.AdditionalResources;
import resources.Constants;
import resources.Resources;
import test.TestProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Ann on 2018-03-01.
 */
public class ParkedPanel extends JPanel {

    public static TestProgram test_program_reference;

    ToggleButton fronk;
    ToggleButton mirror;
    ToggleButton charge;
    ToggleButton trunk;

    Timer charging_animation_controller;
    int charging_animation_framecount;

    static final int button_width = (int)(0.25 * Resources.button_fronk[0].getWidth());
    static final int button_height = (int)(0.25 * Resources.button_fronk[0].getHeight());

    public ParkedPanel () {
        super();

        setBounds(5, 65, 340, 500);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        charging_animation_framecount = 0;

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
        charge.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                test_program_reference.setAllowCharging(!test_program_reference.chargingIsAllowed());
            }
        });

        trunk = new ToggleButton(Resources.button_trunk, 2, (getWidth() - button_width)/2, 370,
                button_width, button_height);

        charging_animation_controller = new Timer(1000/24, e -> {

            if (InformationService.battery == 100) {
                charging_animation_framecount = 124;
                RenderingService.invokeRepaint();
                return;
            }

            charging_animation_framecount = (charging_animation_framecount == 124) ? 0 : charging_animation_framecount + 1;
            RenderingService.invokeRepaint();
        });

        add(fronk);
        add(mirror);
        add(charge);
        add(trunk);

        InformationService.parked_panel_reference = this;

    }

    public void setVisible (boolean b) {
        super.setVisible(b);

        if (!b) {
            fronk.forceState(0);
            trunk.forceState(0);
            charge.forceState(0);

            test_program_reference.setAllowCharging(false);
        }
    }

    protected void paintComponent (Graphics g) {

        if (InformationService.mirror_state == Constants.MIRROR_RETRACTED && mirror.getState() != 0) {
            mirror.forceState(0);
        } else if (InformationService.mirror_state != Constants.MIRROR_RETRACTED && mirror.getState() != 1) {
            mirror.forceState(1);
        }

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(Resources.car_icon_fronk[0], (getWidth() - (int)(0.13 * Resources.car_icon_fronk[0].getWidth()))/2, 130,
                (int)(0.13 * Resources.car_icon_fronk[0].getWidth()), (int)(0.13 * Resources.car_icon_fronk[0].getHeight()), null);

        if (charging_animation_controller.isRunning()) {
            ((Graphics2D) g).drawImage(AdditionalResources.charging_animation[charging_animation_framecount], 253, 225, 41, 41, null);
        }

    }

    public void setCharging (boolean b) {
        if (b) charging_animation_controller.start();
        else {
            charging_animation_controller.stop();
            charging_animation_framecount = 0;
        }

        RenderingService.invokeRepaint();
    }

}

package test;

import information.InformationService;
import resources.Constants;
import ui.TurningSignal;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by freddeng on 2018-03-16.
 */

public class GearAPFrame extends JFrame {

    JPanel autopilot;
    JPanel gearshifter;

    JButton left_indicator;
    JButton right_indicator;

    JButton cruise_control_button;
    JButton autopilot_button;
    JButton stop_autonomous_button;

    JButton gear_up_button;
    JButton gear_down_button;

    public GearAPFrame () {

        setSize(220, 260);
        getContentPane().setLayout(new GridLayout(2, 1));
        setResizable(false);
        setVisible(true);

        Border gear_border = BorderFactory.createTitledBorder("Gear Control");
        Border ap_border = BorderFactory.createTitledBorder("Autopilot Control");

        Dimension gear_dim = new Dimension(200, 100);
        Dimension ap_dim = new Dimension(200,100);

        gearshifter = new JPanel();
        gearshifter.setPreferredSize(gear_dim);
        gearshifter.setMaximumSize(gear_dim);
        gearshifter.setAlignmentX(Component.CENTER_ALIGNMENT);
        gearshifter.setBorder(gear_border);
        gearshifter.setLayout(new GridLayout(2, 2));
        getContentPane().add(gearshifter);

        autopilot = new JPanel();
        autopilot.setPreferredSize(ap_dim);
        autopilot.setMaximumSize(ap_dim);
        autopilot.setAlignmentX(Component.CENTER_ALIGNMENT);
        autopilot.setBorder(ap_border);
        autopilot.setLayout(new GridLayout(3, 1));
        getContentPane().add(autopilot);

        cruise_control_button = new JButton("Cruise Control");
        cruise_control_button.addActionListener(e -> {

            if (InformationService.battery == 0) return;

            InformationService.changeMode(Constants.MODE_CRUISE_CONTROL);
            cruise_control_button.setEnabled(false);
            autopilot_button.setEnabled(true);
            stop_autonomous_button.setEnabled(true);
        });
        cruise_control_button.setEnabled(false);

        autopilot_button = new JButton("Autopilot");
        autopilot_button.addActionListener(e -> {

            if (InformationService.battery == 0) return;

            InformationService.changeMode(Constants.MODE_AUTOPILOT);
            cruise_control_button.setEnabled(true);
            autopilot_button.setEnabled(false);
            stop_autonomous_button.setEnabled(true);
        });
        autopilot_button.setEnabled(false);

        stop_autonomous_button = new JButton("Stop");
        stop_autonomous_button.addActionListener(e -> {
            InformationService.changeMode(Constants.MODE_NORMAL);
            cruise_control_button.setEnabled(true);
            autopilot_button.setEnabled(true);
            stop_autonomous_button.setEnabled(false);
        });
        stop_autonomous_button.setEnabled(false);

        autopilot.add(cruise_control_button);
        autopilot.add(autopilot_button);
        autopilot.add(stop_autonomous_button);

        gear_up_button = new JButton("Next Gear");
        gear_up_button.addActionListener(e -> {
            InformationService.changeGear(
                    (InformationService.drive_gear == Constants.GEAR_DRIVE) ?
                            Constants.GEAR_PARKED : InformationService.drive_gear + 1
            );
        });

        gear_down_button = new JButton("Prev Gear");
        gear_down_button.addActionListener(e -> {
            InformationService.changeGear(
                    (InformationService.drive_gear == Constants.GEAR_PARKED) ?
                            Constants.GEAR_DRIVE : InformationService.drive_gear - 1
            );
        });

        left_indicator = new JButton("Left");
        left_indicator.addActionListener(e -> {
            TurningSignal.left.activate();
        });

        right_indicator = new JButton("Right");
        right_indicator.addActionListener(e -> {
            TurningSignal.right.activate();
        });

        gearshifter.add(left_indicator);
        gearshifter.add(right_indicator);
        gearshifter.add(gear_down_button);
        gearshifter.add(gear_up_button);

        InformationService.ap_frame_reference = this;

    }

    public void setAutonomousFunctions (boolean b) {
        if (b) {
            stop_autonomous_button.setEnabled(false);
            autopilot_button.setEnabled(true);
            cruise_control_button.setEnabled(true);
        } else {
            stop_autonomous_button.setEnabled(false);
            autopilot_button.setEnabled(false);
            cruise_control_button.setEnabled(false);
        }
    }

}

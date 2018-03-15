package ui;

import information.InformationService;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by freddeng on 2018-03-09.
 */
public class CoreControlBarPanel extends JPanel {

    public static MusicPlayerPanelSM music_panel;
    public static ControlPanelSM control_panel;

    ToggleButton media;
    ToggleButton control;
    ToggleButton left_seat;
    ToggleButton right_seat;

    StateButton ac;

    SlideTemperatureAdjustor driver_adjustor;
    SlideTemperatureAdjustor passenger_adjustor;

    public CoreControlBarPanel () {

        setBounds(20, 720 - 10 - (720 - 20 - 10 - 10 - 605), 1125 - 40, 720 - 20 - 10 - 10 - 605);
        setBackground(Constants.BACKGROUND_GREY);
        setLayout(null);

        media = new ToggleButton(Resources.core_media, 2, 717, 0, 52, 75);
        media.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (!music_panel.isVisible()) {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, music_panel);
                } else {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, music_panel);
                }
            }
        });
        MusicPlayerPanelSM.invoker = media;

        control = new ToggleButton(Resources.core_control, 2, 314, 0, 52, 75);
        control.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (!control_panel.isVisible()) {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_SM, control_panel);
                } else {
                    MainWindow.window.negotiateSpace(Constants.WindowConstants.STATE_IDLE, control_panel);
                }
            }
        });
        ControlPanelSM.invoker = control;

        left_seat = new ToggleButton(Resources.core_left_seat, 4, 177, 0, 52, 75, true);
        left_seat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.butt_warmer_left_state = (InformationService.butt_warmer_left_state == 3) ? 0 : InformationService.butt_warmer_left_state + 1;
                RenderingService.invokeRepaint();
            }
        });

        right_seat = new ToggleButton(Resources.core_right_seat, 4, 855, 0, 52, 75, true);
        right_seat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.butt_warmer_right_state = (InformationService.butt_warmer_right_state == 3) ? 0 : InformationService.butt_warmer_right_state + 1;
                RenderingService.invokeRepaint();
            }
        });


        ac = new StateButton(Resources.core_ac, 3, 490, 0, 105, 75);

        driver_adjustor = new SlideTemperatureAdjustor(35, 0, InformationService.driver_ac_temp);
        passenger_adjustor = new SlideTemperatureAdjustor(985, 0, InformationService.passenger_ac_temp);

        add(driver_adjustor);
        add(passenger_adjustor);

        add(media);
        add(control);
        add(left_seat);
        add(right_seat);

        add(ac);
    }

    protected void paintComponent (Graphics g) {

        left_seat.forceState(InformationService.butt_warmer_left_state);
        right_seat.forceState(InformationService.butt_warmer_right_state);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Constants.CORE_BAR_INACTIVE);
        g2d.setFont(Resources.core_temp_control_font);

        if (InformationService.driver_ac_temp.getValue() == -1) {
            g2d.drawString(" LO", 35, 50);
        } else if (InformationService.driver_ac_temp.getValue() == 31) {
            g2d.drawString(" HI", 35, 50);
        } else if (InformationService.driver_ac_temp.getValue() < 10) {
            g2d.drawString(" " + InformationService.driver_ac_temp.getValue() + "°", 35, 50);
        } else {
            g2d.drawString(InformationService.driver_ac_temp.getValue() + "°", 35, 50);
        }

        if (InformationService.passenger_ac_temp.getValue() == -1) {
            g2d.drawString(" LO", 983, 50);
        } else if (InformationService.passenger_ac_temp.getValue() == 31) {
            g2d.drawString(" HI", 983, 50);
        } else if (InformationService.passenger_ac_temp.getValue() < 10) {
            g2d.drawString(" " + InformationService.passenger_ac_temp.getValue() + "°", 983, 50);
        } else {
            g2d.drawString(InformationService.passenger_ac_temp.getValue() + "°", 983, 50);
        }

    }

}

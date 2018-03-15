package ui;

import information.InformationService;
import resources.Constants;
import resources.Resources;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-05.
 */

public class ACPanelSM extends ContainerSM implements NegotiablePanel {

    int current_temp_mode;

    ACSubPanel left;
    ACSubPanel right;

    ToggleButton butt_warmer_left;
    ToggleButton butt_warmer_right;

    ToggleButton in_your_face;
    ToggleButton in_your_feet;

    ToggleButton power;
    ToggleButton hand_warmer;

    public ACPanelSM() {

        super();

        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);

        current_temp_mode = InformationService.ac_temp_mode;

        left = new ACSubPanel(0, 0);
        right = new ACSubPanel(490, 0);

        butt_warmer_left = new ToggleButton(Resources.ac_butt_warmer_left, 4, 252, 119, 31, 31, true);
        butt_warmer_left.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.butt_warmer_left_state = (InformationService.butt_warmer_left_state == 3) ? 0 : InformationService.butt_warmer_left_state + 1;
                RenderingService.invokeRepaint();
            }
        });

        butt_warmer_right = new ToggleButton(Resources.ac_butt_warmer_right, 4, 443, 119, 31, 31, true);
        butt_warmer_right.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.butt_warmer_right_state = (InformationService.butt_warmer_right_state == 3) ? 0 : InformationService.butt_warmer_right_state + 1;
                RenderingService.invokeRepaint();
            }
        });

        in_your_face = new ToggleButton(Resources.ac_in_your_face[current_temp_mode], 2, 252, 28, 31, 31);
        in_your_feet = new ToggleButton(Resources.ac_in_your_feet[current_temp_mode], 2, 252, 73, 31, 31);

        hand_warmer = new ToggleButton(Resources.ac_hand_warmer, 2, 326, 119, 31, 31);
        power = new ToggleButton(Resources.ac_power[current_temp_mode], 2, 368, 119, 31, 31);

        add(left);
        add(right);

        add(butt_warmer_left);
        add(butt_warmer_right);

        add(in_your_face);
        add(in_your_feet);

        add(hand_warmer);
        add(power);

    }



    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (InformationService.ac_temp_mode != current_temp_mode) {
            current_temp_mode = InformationService.ac_temp_mode;

            in_your_face.changeIcon(Resources.ac_in_your_face[current_temp_mode]);
            in_your_feet.changeIcon(Resources.ac_in_your_feet[current_temp_mode]);

            power.changeIcon(Resources.ac_power[current_temp_mode]);

            RenderingService.invokeRepaint();

            //TODO
        }

        butt_warmer_left.forceState(InformationService.butt_warmer_left_state);
        butt_warmer_right.forceState(InformationService.butt_warmer_right_state);

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS);
        g2d.clip(clip);

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.AC_BACKGROUND_STOP_0, getWidth(), 0f, Constants.AC_BACKGROUND_STOP_1);

        g2d.setPaint(primary);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.drawImage(Resources.ac_accessory, 0, 85, getWidth(), 28, null);
        g2d.drawImage(Resources.ac_main, (getWidth() - 276)/2, 0, 276, getHeight(), null);

    }

    @Override
    public void updateInvoker() {

    }

    @Override
    public void setActive(boolean is_active) {

    }
}

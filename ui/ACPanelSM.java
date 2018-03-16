package ui;

import information.InformationService;
import resources.AdditionalResources;
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

    ToggleButton fresh_air;
    ToggleButton circulation;

    GlowButton control_mode;
    GlowButton up_fan_speed;
    GlowButton down_fan_speed;

    public ACPanelSM() {

        super();

        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);

        current_temp_mode = InformationService.ac_temp_mode;

        left = new ACSubPanel(0, 0);
        right = new ACSubPanel(490, 0);

        fresh_air = new ToggleButton(AdditionalResources.ac_fresh_air[0], 2, 443, 28, 31, 31);
        fresh_air.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (fresh_air.isDisabled()) return;
                forcePowerOn();
                circulation.forceState(0);
            }
        });

        circulation = new ToggleButton(AdditionalResources.ac_circulation[0], 2, 443, 73, 31, 31);
        circulation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (circulation.isDisabled()) return;
                forcePowerOn();
                fresh_air.forceState(0);
            }
        });

        control_mode = new GlowButton(AdditionalResources.ac_manual, 310, 31, 105, 25);
        control_mode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.ac_control_mode = (InformationService.ac_control_mode == Constants.AC_AUTO) ? Constants.AC_MANUAL : Constants.AC_AUTO;
                control_mode.changeIcon((InformationService.ac_control_mode == Constants.AC_AUTO) ? AdditionalResources.ac_auto : AdditionalResources.ac_manual);

                up_fan_speed.setDisable(InformationService.ac_control_mode == Constants.AC_AUTO);
                down_fan_speed.setDisable(InformationService.ac_control_mode == Constants.AC_AUTO);

                fresh_air.setDisable(InformationService.ac_control_mode == Constants.AC_AUTO);
                circulation.setDisable(InformationService.ac_control_mode == Constants.AC_AUTO);

                RenderingService.invokeRepaint();
            }
        });

        up_fan_speed = new GlowButton(AdditionalResources.ac_fan_up, 393, 77, 22, 25);
        up_fan_speed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (up_fan_speed.isDisabled()) return;
                forcePowerOn();
                InformationService.ac_fan_speed = (InformationService.ac_fan_speed == 5) ? 5 : InformationService.ac_fan_speed + 1;
                RenderingService.invokeRepaint();
            }
        });

        down_fan_speed = new GlowButton(AdditionalResources.ac_fan_down, 310, 77, 22, 25);
        down_fan_speed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (up_fan_speed.isDisabled()) return;
                forcePowerOn();
                InformationService.ac_fan_speed = (InformationService.ac_fan_speed == 1) ? 1 : InformationService.ac_fan_speed - 1;
                RenderingService.invokeRepaint();
            }
        });

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
        in_your_face.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                forcePowerOn();
            }
        });

        in_your_feet = new ToggleButton(Resources.ac_in_your_feet[current_temp_mode], 2, 252, 73, 31, 31);
        in_your_feet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                forcePowerOn();
            }
        });

        hand_warmer = new ToggleButton(Resources.ac_hand_warmer, 2, 326, 119, 31, 31);

        power = new ToggleButton(Resources.ac_power[current_temp_mode], 2, 368, 119, 31, 31);
        power.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                InformationService.ac_is_on = !InformationService.ac_is_on;
                if (!InformationService.ac_is_on) {
                    in_your_face.forceState(0);
                    in_your_feet.forceState(0);
                    fresh_air.forceState(0);
                    circulation.forceState(0);
                } else {
                    in_your_face.forceState(1);
                    in_your_face.forceState(1);
                }
            }
        });

        add(left);
        add(right);

        add(butt_warmer_left);
        add(butt_warmer_right);

        add(in_your_face);
        add(in_your_feet);

        add(hand_warmer);
        add(power);

        add(fresh_air);
        add(circulation);

        add(control_mode);
        add(up_fan_speed);
        add(down_fan_speed);

        CoreControlBarPanel.ac_panel = this;

    }

    public void forcePowerOn () {
        if (InformationService.ac_is_on) return;
        power.click();
    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (InformationService.ac_temp_mode != current_temp_mode) {
            current_temp_mode = InformationService.ac_temp_mode;

            in_your_face.changeIcon(Resources.ac_in_your_face[current_temp_mode]);
            in_your_feet.changeIcon(Resources.ac_in_your_feet[current_temp_mode]);

            power.changeIcon(Resources.ac_power[current_temp_mode]);

            fresh_air.changeIcon(AdditionalResources.ac_fresh_air[current_temp_mode]);
            circulation.changeIcon(AdditionalResources.ac_circulation[current_temp_mode]);

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

        if (InformationService.ac_control_mode == Constants.AC_MANUAL) {
            g2d.drawImage(AdditionalResources.ac_up_down_adjustor[1], 310, 77, 105, 25, null);
            g2d.drawImage(AdditionalResources.ac_fan_icon[0], 350, 84, 11, 11, null);

            g2d.setColor(Constants.AC_FAN_SPEED_ACTIVE);
        } else {
            g2d.drawImage(AdditionalResources.ac_up_down_adjustor[0], 310, 77, 105, 25, null);
            g2d.drawImage(AdditionalResources.ac_fan_icon[1], 350, 84, 11, 11, null);

            g2d.setColor(Constants.AC_FAN_SPEED_INACTIVE);
        }

        g2d.setFont(Resources.ac_fanspeed_font);
        g2d.drawString(String.valueOf(InformationService.ac_fan_speed), 365, 95);

    }

    @Override
    public void updateInvoker() {

    }

    @Override
    public void setActive(boolean is_active) {

    }
}

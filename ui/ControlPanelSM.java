package ui;

import information.InformationService;
import javafx.scene.control.Toggle;
import resources.Constants;
import resources.Resources;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-05.
 */

public class ControlPanelSM extends ContainerSM implements NegotiablePanel {

    public static ToggleButton invoker;

    ToggleButton door_lock_left_front;
    ToggleButton door_lock_left_back;
    ToggleButton door_lock_right_front;
    ToggleButton door_lock_right_back;

    MirrorController left_mirror;
    MirrorController right_mirror;

    ModernSlider sunroof_slider;
    ModernIncrementalSlider mirror_slider;

    public ControlPanelSM() {

        super();

        setLayout(null);

        door_lock_left_front = new ToggleButton(Resources.control_door_lock, 2, 197, 50, 49, 30);
        door_lock_left_front.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.left_front_door_locked = !InformationService.left_front_door_locked;
            }
        });

        door_lock_left_back = new ToggleButton(Resources.control_door_lock, 2, 197, 98, 49, 30);
        door_lock_left_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.left_back_door_locked = !InformationService.left_back_door_locked;
            }
        });

        door_lock_right_front = new ToggleButton(Resources.control_door_lock, 2, 476, 50, 49, 30);
        door_lock_right_front.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.right_front_door_locked = !InformationService.right_front_door_locked;
            }
        });

        door_lock_right_back = new ToggleButton(Resources.control_door_lock, 2, 476, 98, 49, 30);
        door_lock_right_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                InformationService.right_back_door_locked = !InformationService.right_back_door_locked;
            }
        });

        left_mirror = new MirrorController(MirrorController.TYPE_LEFT_MIRROR, 27, 50);
        right_mirror = new MirrorController(MirrorController.TYPE_RIGHT_MIRROR, 538, 50);

        sunroof_slider = new ModernSlider(256, 49);
        mirror_slider = new ModernIncrementalSlider(256, 97);

        add(door_lock_left_front);
        add(door_lock_left_back);
        add(door_lock_right_front);
        add(door_lock_right_back);

        add(left_mirror);
        add(right_mirror);

        add(sunroof_slider);
        add(mirror_slider);

        CoreControlBarPanel.control_panel = this;

    }

    protected void paintComponent (Graphics g) {

        if (InformationService.mirror_state == Constants.MIRROR_RETRACTED && mirror_slider.getState() != 0) {
            mirror_slider.changeState(false);
        } else if (InformationService.mirror_state == Constants.MIRROR_EXTENDED && mirror_slider.getState() != 1) {
            mirror_slider.changeState(true);
        }

        if (InformationService.allDoorsLocked()) {
            door_lock_right_back.forceState(1);
            door_lock_right_front.forceState(1);
            door_lock_left_front.forceState(1);
            door_lock_left_back.forceState(1);
        } else if (InformationService.allDoorsUnlocked()) {
            door_lock_right_back.forceState(0);
            door_lock_right_front.forceState(0);
            door_lock_left_front.forceState(0);
            door_lock_left_back.forceState(0);
        }

        Graphics2D g2d = (Graphics2D)g;

        Shape clip = new RoundRectangle2D.Double(0, 0, panel_width, panel_height, Constants.ROUNDNESS, Constants.ROUNDNESS);
        g2d.clip(clip);

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.CONTROL_INTERFACE_STOP_0, getWidth(), 0f, Constants.CONTROL_INTERFACE_STOP_1);
        g2d.setPaint(primary);

        g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));

        super.paintComponent(g);

    }

    @Override
    public void setActive(boolean is_active) {

    }

    @Override
    public void updateInvoker() {
        invoker.forceState(0);
    }
}

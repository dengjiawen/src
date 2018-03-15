package ui;

import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by freddeng on 2018-03-13.
 */
public class MirrorController extends JPanel {

    public static final int TYPE_RIGHT_MIRROR = 0;
    public static final int TYPE_LEFT_MIRROR = 1;

    public static final int STATE_IDLE = 0;
    public static final int STATE_UP = 1;
    public static final int STATE_LEFT = 2;
    public static final int STATE_DOWN = 3;
    public static final int STATE_RIGHT = 4;

    private Rectangle hit_box_up;
    private Rectangle hit_box_down;
    private Rectangle hit_box_left;
    private Rectangle hit_box_right;

    private int mirror_type;
    private int current_state;

    public MirrorController (int mirror_type, int x, int y) {

        setBounds(x, y, 157, 97);

        this.mirror_type = mirror_type;
        this.current_state = STATE_IDLE;

        hit_box_up = new Rectangle(50, 0, 57, 50);
        hit_box_down = new Rectangle(50, getHeight() - 50, 57, 50);

        hit_box_left = new Rectangle(0,20,50,57);
        hit_box_right = new Rectangle(getWidth() - 50, 20,50, 57);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                Point p = e.getPoint();
                if (hit_box_up.contains(p)) {
                    current_state = STATE_UP;
                } else if (hit_box_down.contains(p)) {
                    current_state = STATE_DOWN;
                } else if (hit_box_left.contains(p)) {
                    current_state = STATE_LEFT;
                } else if (hit_box_right.contains(p)) {
                    current_state = STATE_RIGHT;
                } else {
                    current_state = STATE_IDLE;
                }

                RenderingService.invokeRepaint();
            }

            @Override
            public void mouseDragged (MouseEvent e) {
                super.mouseDragged(e);

                Point p = e.getPoint();
                if (hit_box_up.contains(p)) {
                    current_state = STATE_UP;
                } else if (hit_box_down.contains(p)) {
                    current_state = STATE_DOWN;
                } else if (hit_box_left.contains(p)) {
                    current_state = STATE_LEFT;
                } else if (hit_box_right.contains(p)) {
                    current_state = STATE_RIGHT;
                } else {
                    current_state = STATE_IDLE;
                }

                RenderingService.invokeRepaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                current_state = STATE_IDLE;
                RenderingService.invokeRepaint();
            }
        });

    }

    protected void paintComponent (Graphics g) {

        switch (mirror_type) {
            case TYPE_LEFT_MIRROR:
                g.drawImage(Resources.control_left_mirror[current_state], 0, 0, getWidth(), getHeight(), null);
                break;
            case TYPE_RIGHT_MIRROR:
                g.drawImage(Resources.control_right_mirror[current_state], 0, 0, getWidth(), getHeight(), null);
                break;
        }

    }

}

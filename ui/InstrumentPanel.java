/**
 * Created by freddeng on 2018-03-01.
 */

package ui;

import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class InstrumentPanel extends JPanel {

    JLabel speedometer;
    JLabel range;

    ToggleButton Fdefrost;
    ToggleButton Rdefrost;
    ToggleButton lock;
    ToggleButton safety;
    ToggleButton wiper;
    ToggleButton light;

    FlashButton hazard;

    TurningSignal left;
    TurningSignal right;

    public InstrumentPanel () {
        super();

        setBounds(20,20,350,605);
        setBackground(new Color(0,0,0,0));
        setLayout(null);

        ParkedPanel parked = new ParkedPanel();

        speedometer = new JLabel("P");
        speedometer.setFont(Resources.speedometer_font);
        speedometer.setForeground(Color.BLACK);
        speedometer.setOpaque(false);
        speedometer.setBounds((getWidth() - 80)/2,40,80,100);
        speedometer.setVerticalAlignment(JLabel.CENTER);
        speedometer.setHorizontalAlignment(JLabel.CENTER);

        range = new JLabel("216 KM");
        range.setFont(Resources.range_font);
        range.setForeground(Color.BLACK);
        range.setOpaque(false);
        range.setBounds((getWidth() - 80)/2, getHeight() - 30, 80, 25);
        range.setVerticalAlignment(JLabel.CENTER);
        range.setHorizontalAlignment(JLabel.CENTER);

        wiper = new ToggleButton(Resources.control_wiper, 6, 23, 480,
                (int)(0.25 * Resources.control_wiper[0].getWidth()), (int)(0.25 * Resources.control_wiper[0].getHeight()));
        light = new ToggleButton(Resources.control_light, 6, 350 - 23 - (int)(0.25 * Resources.control_light[0].getWidth()), 480,
                (int)(0.25 * Resources.control_light[0].getWidth()), (int)(0.25 * Resources.control_light[0].getHeight()));
        Fdefrost = new ToggleButton(Resources.control_Fdefrost, 2, 23, 480 + (int)(0.25 * Resources.control_light[0].getHeight() + 5),
                (int)(0.25 * Resources.control_Fdefrost[0].getWidth()), (int)(0.25 * Resources.control_Fdefrost[0].getHeight()));
        safety = new ToggleButton(Resources.control_safety, 2, 350 - 23 - (int)(0.25 * Resources.control_safety[0].getWidth()),
                480 + (int)(0.25 * Resources.control_safety[0].getHeight() + 5), (int)(0.25 * Resources.control_safety[0].getWidth()),
                (int)(0.25 * Resources.control_safety[0].getHeight()));

        hazard = new FlashButton(Resources.control_hazard, (int)((350 - 0.25 * Resources.control_hazard[0].getWidth()) / 2),
                480 + (int)(0.25 * Resources.control_light[0].getHeight() + 5),
                (int)(0.25 * Resources.control_hazard[0].getWidth()), (int)(0.25 * Resources.control_hazard[0].getHeight()), 295);
        hazard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (!right.isEmergency()) {
                    right.emergency(true);
                    left.emergency(true);
                } else {
                    left.emergency(false);
                    right.emergency(false);
                }
            }
        });

        Rdefrost = new ToggleButton(Resources.control_Rdefrost, 2, Fdefrost.getX() + Fdefrost.getWidth() + (hazard.getX() - 23 - Fdefrost.getWidth() * 2) / 2,
                480 + (int)(0.25 * Resources.control_light[0].getHeight() + 5), (int)(0.25 * Resources.control_Rdefrost[0].getWidth()),
                (int)(0.25 * Resources.control_Rdefrost[0].getHeight()));
        lock = new ToggleButton(Resources.control_lock, 2, hazard.getX() + hazard.getWidth() + (safety.getX() - hazard.getX() - hazard.getWidth() - safety.getWidth()) / 2,
                480 + (int)(0.25 * Resources.control_light[0].getHeight() + 5), (int)(0.25 * Resources.control_Rdefrost[0].getWidth()),
                (int)(0.25 * Resources.control_Rdefrost[0].getHeight()));

        right = new TurningSignal(TurningSignal.RIGHT_SIGNAL);
        right.setLocation(255,80);
        left = new TurningSignal(TurningSignal.LEFT_SIGNAL);
        left.setLocation(60,80);

        add(speedometer);
        add(parked);

        add(right);
        add(left);

        add(wiper);
        add(light);
        add(hazard);

        add(Fdefrost);
        add(Rdefrost);
        add(safety);
        add(lock);

        add(range);

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Constants.panel_bright);
        g2d.fill(new RoundRectangle2D.Double(0, 0, 350, 605, Constants.roundness, Constants.roundness));

        g2d.setPaint(Constants.text_inactive);
        g2d.fillRect(30, 155, 290, 2);

        g2d.setPaint(Constants.control_center);
        g2d.fill(new RoundRectangle2D.Double(15, 475, 320, 97, Constants.roundness, Constants.roundness));

        g2d.clip(new RoundRectangle2D.Double(0, 0, 350, 605, Constants.roundness, Constants.roundness));

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.battery_progress_stop_0, 200, 0f, Constants.battery_progress_stop_1);

        g2d.setPaint(primary);
        g2d.fill(new Rectangle2D.Float(0, getHeight() - 4, 200, 8));

        super.paintComponent(g);

    }

}

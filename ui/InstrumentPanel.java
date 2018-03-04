/**
 * Created by freddeng on 2018-03-01.
 */

package ui;

import com.sun.awt.AWTUtilities;
import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class InstrumentPanel extends JPanel {

    Timer slide_out_timer;
    int slide_out_framecount;

    public InstrumentPanel () {
        super();

        setBounds(-350,20,350,605);
        setBackground(new Color(0,0,0,0));
        setLayout(null);

        ParkedPanel parked = new ParkedPanel();

        JLabel speedometer = new JLabel("P");
        speedometer.setFont(Resources.speedometer_font);
        speedometer.setForeground(Color.BLACK);
        speedometer.setOpaque(false);
        speedometer.setBounds((getWidth() - 70)/2,115,80,100);
        speedometer.setVerticalAlignment(JLabel.CENTER);
        speedometer.setHorizontalAlignment(JLabel.CENTER);

        slide_out_framecount = -350;
        slide_out_timer = new Timer(1000/60, e -> {

            if (slide_out_framecount < 20) {
                slide_out_framecount += 5;
            } else {
                slide_out_timer.stop();
            }
            setBounds(slide_out_framecount,20,350,605);
        });

        add(speedometer);
        add(parked);

    }

    public void init () {
        slide_out_timer.start();
    }

    public void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
//
        g2d.setPaint(Constants.panel_bright);
        g2d.fill(new RoundRectangle2D.Double(0, 0, 350, 605, Constants.roundness, Constants.roundness));

        g2d.drawImage(Resources.signal_left, 0, 0, null);

        super.paintComponent(g);

    }

}

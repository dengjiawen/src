/**
 * Created by freddeng on 2018-03-01.
 */

package ui;

import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class InstrumentPanel extends JPanel {

    private TurningSignal left;
    private TurningSignal right;

    public InstrumentPanel () {
        super();

        setBounds(20,20,350,605);
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

        right = new TurningSignal(TurningSignal.RIGHT_SIGNAL);
        left = new TurningSignal(TurningSignal.LEFT_SIGNAL);

        add(speedometer);
        add(parked);

        add(right);
        add(left);

    }

    protected void paintComponent (Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Constants.panel_bright);
        g2d.fill(new RoundRectangle2D.Double(0, 0, 350, 605, Constants.roundness, Constants.roundness));

        super.paintComponent(g2d);

    }

}

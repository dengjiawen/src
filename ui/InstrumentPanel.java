/**
 * Created by freddeng on 2018-03-01.
 */

package ui;

import information.InformationService;
import resources.Constants;
import resources.Resources;
import sound.SoundService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class InstrumentPanel extends JPanel {

    JLabel parked_label;
    JLabel neutral_label;
    JLabel speed_label;
    JLabel speed_unit_label;
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

    ParkedPanel parked;
    DrivePanel drive;
    ReversePanel reverse;
    NeutralPanel neutral;

    float battery_progress;

    public InstrumentPanel () {
        super();

        setBounds(20,20,350,605);
        setBackground(new Color(0,0,0,0));
        setLayout(null);

        parked = new ParkedPanel();

        reverse = new ReversePanel();
        reverse.setVisible(false);

        neutral = new NeutralPanel();
        neutral.setVisible(false);

        drive = new DrivePanel();
        drive.setVisible(false);

        battery_progress = 100;

        parked_label = new JLabel("P");
        parked_label.setFont(Resources.speedometer_font);
        parked_label.setForeground(Color.BLACK);
        parked_label.setOpaque(false);
        parked_label.setBounds((getWidth() - 80)/2,40,80,100);
        parked_label.setVerticalAlignment(JLabel.CENTER);
        parked_label.setHorizontalAlignment(JLabel.CENTER);

        neutral_label = new JLabel("N");
        neutral_label.setFont(Resources.speedometer_font);
        neutral_label.setForeground(Color.BLACK);
        neutral_label.setOpaque(false);
        neutral_label.setBounds((getWidth() - 80)/2,40,80,100);
        neutral_label.setVerticalAlignment(JLabel.CENTER);
        neutral_label.setHorizontalAlignment(JLabel.CENTER);
        neutral_label.setVisible(false);

        speed_label = new JLabel("0");
        speed_label.setFont(Resources.speed_font);
        speed_label.setForeground(Color.BLACK);
        speed_label.setOpaque(false);
        speed_label.setBounds((getWidth() - 150)/2,35,150,100);
        speed_label.setVerticalAlignment(JLabel.CENTER);
        speed_label.setHorizontalAlignment(JLabel.CENTER);
        speed_label.setVisible(false);

        speed_unit_label = new JLabel("KMH");
        speed_unit_label.setFont(Resources.speed_unit_font);
        speed_unit_label.setForeground(Constants.SPEED_UNIT_COLOR);
        speed_unit_label.setOpaque(false);
        speed_unit_label.setBounds((getWidth() - 80)/2,85,80,100);
        speed_unit_label.setVerticalAlignment(JLabel.CENTER);
        speed_unit_label.setHorizontalAlignment(JLabel.CENTER);
        speed_unit_label.setVisible(false);

        range = new JLabel("449 KM");
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
                    right.activate(false);
                    left.activate(false);

                    right.emergency(true);
                    left.emergency(true);

                    SoundService.setSignalSound(true);
                } else {
                    left.emergency(false);
                    right.emergency(false);

                    right.activate(false);
                    left.activate(false);
                }
            }
        });

        Rdefrost = new ToggleButton(Resources.control_Rdefrost, 2, Fdefrost.getX() + Fdefrost.getWidth() + (hazard.getX() - 23 - Fdefrost.getWidth() * 2) / 2,
                480 + (int)(0.25 * Resources.control_light[0].getHeight() + 5), (int)(0.25 * Resources.control_Rdefrost[0].getWidth()),
                (int)(0.25 * Resources.control_Rdefrost[0].getHeight()));
        lock = new ToggleButton(Resources.control_lock, 2, hazard.getX() + hazard.getWidth() + (safety.getX() - hazard.getX() - hazard.getWidth() - safety.getWidth()) / 2,
                480 + (int)(0.25 * Resources.control_light[0].getHeight() + 5), (int)(0.25 * Resources.control_Rdefrost[0].getWidth()),
                (int)(0.25 * Resources.control_Rdefrost[0].getHeight()));
        lock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (!InformationService.allDoorsLocked()) InformationService.lockAllDoors();
                else InformationService.unlockAllDoors();
            }
        });

        right = new TurningSignal(TurningSignal.RIGHT_SIGNAL);
        right.setLocation(255,80);
        left = new TurningSignal(TurningSignal.LEFT_SIGNAL);
        left.setLocation(60,80);

        add(parked_label);
        add(neutral_label);
        add(speed_label);
        add(speed_unit_label);

        add(parked);
        add(drive);
        add(reverse);
        add(neutral);

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

        InformationService.instrument_panel_reference = this;

    }

    public void shiftGear (int gear_modifier) {

        switch (gear_modifier) {
            case Constants.GEAR_PARKED:
                reverse.setVisible(false);
                drive.setVisible(false);
                speed_label.setVisible(false);
                speed_unit_label.setVisible(false);

                parked_label.setVisible(true);
                parked.setVisible(true);
                break;
            case Constants.GEAR_REVERSE:
                parked.setVisible(false);
                parked_label.setVisible(false);
                neutral.setVisible(false);
                neutral_label.setVisible(false);

                speed_unit_label.setVisible(true);
                speed_label.setVisible(true);
                reverse.setVisible(true);
                break;
            case Constants.GEAR_NEUTRAL:
                speed_unit_label.setVisible(false);
                speed_label.setVisible(false);
                reverse.setVisible(false);
                drive.setVisible(false);

                neutral.setVisible(true);
                neutral_label.setVisible(true);
                break;
            case Constants.GEAR_DRIVE:
                neutral_label.setVisible(false);
                parked_label.setVisible(false);

                parked.setVisible(false);
                neutral.setVisible(false);

                speed_label.setVisible(true);
                speed_unit_label.setVisible(true);
                drive.setVisible(true);
                break;
        }

        RenderingService.invokeRepaint();
    }

    public void updateSpeed () {
        speed_label.setText(String.valueOf(InformationService.speed));
    }

    public void updateBatteryProgress () {
        battery_progress = InformationService.battery;
        range.setText((int)((battery_progress/100f) * 449f) + " KM");
    }

    protected void paintComponent (Graphics g) {

        if (lock.getState() == 0 && InformationService.allDoorsLocked()) {
            lock.forceState(1);
        } else if (lock.getState() == 1 && !InformationService.allDoorsLocked()) {
            lock.forceState(0);
        }

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Constants.PANEL_BRIGHT);
        g2d.fill(new RoundRectangle2D.Double(0, 0, 350, 605, Constants.ROUNDNESS, Constants.ROUNDNESS));

        g2d.setPaint(Constants.TEXT_INACTIVE);
        g2d.fillRect(30, 155, 290, 2);

        if (InformationService.accelerating) {
            g2d.setPaint(Constants.LOW_ECO);
            g2d.fillRect(175 - (int)((InformationService.speed/227f) * 145), 155, (int)((InformationService.speed/227f) * 145), 4);
        } else {
            g2d.setPaint(Constants.HIGH_ECO);
            g2d.fillRect(175, 155, (int)((InformationService.speed/227f) * 145), 4);
        }

        g2d.setPaint(Constants.CONTROL_CENTER);
        g2d.fill(new RoundRectangle2D.Double(15, 475, 320, 97, Constants.ROUNDNESS, Constants.ROUNDNESS));

        g2d.clip(new RoundRectangle2D.Double(0, 0, 350, 605, Constants.ROUNDNESS, Constants.ROUNDNESS));

        GradientPaint primary;
        if (battery_progress > 20) {
            primary = new GradientPaint(
                    0f, 0f, Constants.BATTERY_PROGRESS_STOP_0, (battery_progress / 100f) * getWidth(), 0f, Constants.BATTERY_PROGRESS_STOP_1);
        } else {
            primary = new GradientPaint(
                    0f, 0f, Constants.BATTERY_DANGER_STOP_0, (battery_progress / 100f) * getWidth(), 0f, Constants.BATTERY_DANGER_STOP_1);
        }

        g2d.setPaint(primary);
        g2d.fill(new Rectangle2D.Float(0, getHeight() - 4, (battery_progress/100f) * getWidth(), 8));

        super.paintComponent(g);

    }

}

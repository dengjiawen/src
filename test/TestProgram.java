/**
 * Created by Ann on 2018-03-05.
 */

package test;

import information.InformationService;
import resources.Constants;
import ui.ParkedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestProgram {

        JFrame SpeedFrame, BatteryFrame;
        JButton Acceleration, Brakes, Recharge;
        JLabel BPLabel; // This is the label that displays the battery percentage of the car (Can be removed afterwards)
        Timer tAccel, tDecel, tBrake, tBat, tCharge;
            // tAccel is the timer used to time the acceleration, tDecel for deceleration, tBrake for brakes
            // tBat is the one used to calculate how much battery is used while the car is running
            // tCharge is the timer used to keep track of the energy charging
        int intSpeed, intCount1, intCount2;
            // intDistance is used to calculate the distance travelled in that second
            // intCount1 is used to keep track of whether the warning message was previously displayed
            // intCount2 is used for the charging message
            // Perhaps we can make the car stop after a certain percentage
        float fltDistance;
        float flPercent = 1.0f;
            // This float represents the battery percentage. Starts at 100%
        boolean charging = false;
            // The same button is used for starting and stopping charging, so a boolean is used to keep track of it

        Timer ambient_power_depletion;

    public static void main (String [] args) {
        TestProgram BuildTest = new TestProgram();
    }

    public TestProgram(){

        GUI();
        Timers();

        InformationService.test_program_reference = this;
        ParkedPanel.test_program_reference = this;

    }

    /* int getSpeed () {
        return intSpeed;
    } */

    public void GUI(){
        SpeedFrame = new JFrame("Speed");
        SpeedFrame.setSize(220, 250);
        SpeedFrame.setLayout(null);
        SpeedFrame.setResizable(false);
        SpeedFrame.setVisible(true);

        Acceleration = new JButton("Acceleration");
        Acceleration.setLocation(5,5);
        Acceleration.setSize(100,200);
        Acceleration.setVisible(true);
        SpeedFrame.add(Acceleration);

        Brakes = new JButton("Brakes");
        Brakes.setLocation(105, 5);
        Brakes.setSize(100,200);
        Brakes.setVisible(true);
        SpeedFrame.add(Brakes);

        BatteryFrame = new JFrame("Battery");
        BatteryFrame.setLayout(null);
        BatteryFrame.setSize(220, 210);
        BatteryFrame.setResizable(false);
        BatteryFrame.setVisible(true);

        BPLabel = new JLabel("Battery Percent:");
        BPLabel.setLocation(5, 110);
        BPLabel.setSize(200, 50);
        BPLabel.setBackground(Color.ORANGE);
        BPLabel.setVisible(true);
        BatteryFrame.add(BPLabel);

        Recharge = new JButton("Recharge Battery");
        Recharge.setLocation(5,5);
        Recharge.setSize(200,100);
        Recharge.setVisible(true);
        Recharge.setEnabled(false);
        BatteryFrame.add(Recharge);
    }

    private void Timers(){ // We should remove the println after
        tAccel = new Timer(1000/80, e -> {

            if (InformationService.drive_gear == Constants.GEAR_DRIVE) {
                if (tAccel.getDelay() != 1000/20) tAccel.setDelay(1000/20);
                if ((intSpeed + 1) < 227) { // The speed uniformly increases speed by 24 km/h
                    intSpeed += 1; // If adding the speed is under the max speed, the speed gets added
                    InformationService.updateSpeed(intSpeed);
                } else if ((intSpeed + 1) > 227) {
                    intSpeed = 227; // If adding the speed goes over max speed, the car just travels at max
                    InformationService.updateSpeed(intSpeed);
                }
            } else if (InformationService.drive_gear == Constants.GEAR_REVERSE) {
                if (tAccel.getDelay() != 1000/10) tAccel.setDelay(1000/10);
                if ((intSpeed + 1) < 80) { // The speed uniformly increases speed by 24 km/h
                    intSpeed += 1; // If adding the speed is under the max speed, the speed gets added
                    InformationService.updateSpeed(intSpeed);
                } else if ((intSpeed + 1) > 80) {
                    intSpeed = 80; // If adding the speed goes over max speed, the car just travels at max
                    InformationService.updateSpeed(intSpeed);
                }
            }
        });

        tDecel = new Timer(1000/5, e ->{
            if((intSpeed - 1) >= 0){ // The car decelerates by 5 km/h and does not go under 0
                intSpeed -= 1;
                InformationService.updateSpeed(intSpeed);
            }
            else if ((intSpeed > 0)&&((intSpeed - 1) < 0)){
                intSpeed = 0;
                InformationService.updateSpeed(intSpeed);
                InformationService.short_term_car_position_update.stop();
                tDecel.stop();
            }

            regenerativeBraking();

        });


        Acceleration.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if (InformationService.drive_gear == Constants.GEAR_PARKED || InformationService.drive_gear == Constants.GEAR_NEUTRAL) return;
                if (InformationService.battery == 0) {
                    mouseReleased(e);
                    return;
                }

                tAccel.start();
                tDecel.stop();
                InformationService.accelerating = true;

                if (!InformationService.short_term_car_position_update.isRunning()) InformationService.short_term_car_position_update.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                tAccel.stop();
                tDecel.start();
                InformationService.accelerating = false;
            }
        });

        tBrake = new Timer(1000/60, e ->{
            if((intSpeed - 1) >= 0){ // The brakes decelerates the car at 60 km/h
                intSpeed -= 1;
                InformationService.updateSpeed(intSpeed);
            }
            else if ((intSpeed > 0)&&((intSpeed - 1) < 0)){ // The speed should not go below 0
                intSpeed = 0;
                InformationService.updateSpeed(intSpeed);
            }
        });

        Brakes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                tBrake.start();

                InformationService.changeMode(Constants.MODE_NORMAL);
                tAccel.stop();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                tBrake.stop();
            }
        });

        ambient_power_depletion = new Timer(6000, e -> {
            if (InformationService.battery == 0) {
                flPercent = 0;
                return;
            }
            flPercent -= 0.1;
            InformationService.updateBattery(flPercent);
        });
        ambient_power_depletion.start();

        tBat = new Timer(500, e->{
            if (InformationService.accelerating) calculateTime();
            if (flPercent < 0) flPercent = 0;
            InformationService.updateBattery(flPercent);
            BPLabel.setText("Battery Percent: " + flPercent + "%"); // The battery percentage should be recalculated every second
            BatteryFrame.repaint();
        });
        tBat.start();

        Recharge.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(!tCharge.isRunning()){ // If the car wasn't charging, it starts charging

                    InformationService.setCharging(true);

                    tCharge.start();
                }
                else if(tCharge.isRunning()){ // If the car was charging, it stops

                    InformationService.setCharging(false);

                    tCharge.stop();
                }

            }
        });

        tCharge = new Timer( 2000, e ->{
            if(flPercent + 1 <= 100){
                flPercent += 1; // If the car wasn't fully charged, it increases by one
            } else {
                flPercent = 100;
            }
        });
    }

    public void calculateTime() {
        fltDistance = ((intSpeed / 3600f));

        if (intSpeed > 100) {
            flPercent -= ((fltDistance / 75) * 100);
        } else if (intSpeed > 200) {
            flPercent -= ((fltDistance / 40) * 100);
        } else {
            flPercent -= ((fltDistance / 100) * 100);
        }

    }

    public void regenerativeBraking() {
        if (InformationService.battery == 0) {
            return;
        }
        fltDistance = ((intSpeed / 3600f));

        flPercent += ((fltDistance / 8000) * 100);

        if (flPercent > 100) {
            flPercent = 100;
        }

    }

    public void reposition (int x, int y) {

        SpeedFrame.setLocation(x, y);
        BatteryFrame.setLocation(x, y + SpeedFrame.getHeight());

    }

    public void repaintAll () {
        SpeedFrame.revalidate();
        SpeedFrame.repaint();
    }

    public void setAllowCharging (boolean b) {
        if (b) Recharge.setEnabled(true);
        else {
            Recharge.setEnabled(false);
            tCharge.stop();
        }
    }

    public boolean chargingIsAllowed () {
        return Recharge.isEnabled();
    }

    public void artificialAccel (boolean b) {

        if (Acceleration.getModel().isPressed()) return;

        if (b) tAccel.start();
        else tAccel.stop();
    }

    public boolean isCharging () {
        return tCharge.isRunning();
    }

}

/**
 * Created by Ann on 2018-03-05.
 */

package test;

import com.sun.org.apache.regexp.internal.RE;

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
        int intSpeed, intDistance, intCount1, intCount2;
            // intDistance is used to calculate the distance travelled in that second
            // intCount1 is used to keep track of whether the warning message was previously displayed
            // intCount2 is used for the charging message
            // Perhaps we can make the car stop after a certain percentage
        float flPercent = 100f;
            // This float represents the battery percentage. Starts at 100%
        boolean charging = false;
            // The same button is used for starting and stopping charging, so a boolean is used to keep track of it

    public static void main (String [] args) {
        TestProgram BuildTest = new TestProgram();
    }

    TestProgram(){

        GUI();
        Timers();
    }

    /* int getSpeed () {
        return intSpeed;
    } */

    private void GUI(){
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
        BatteryFrame.setSize(500, 210);
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
        BatteryFrame.add(Recharge);
    }

    private void Timers(){ // We should remove the println after
        tAccel = new Timer(1000, e -> {
            if((intSpeed + 24) < 227){ // The speed uniformly increases speed by 24 km/h
                intSpeed +=  24; // If adding the speed is under the max speed, the speed gets added
                System.out.println(intSpeed);
            }
            else if((intSpeed + 24)>227){
                intSpeed = 227; // If adding the speed goes over max speed, the car just travels at max
                System.out.println(intSpeed);
            }
        });

        tDecel = new Timer(1000, e ->{
            if((intSpeed - 5) > 0){ // The car decelerates by 5 km/h and does not go under 0
                intSpeed -= 5;
                System.out.println(intSpeed);
            }
            else if ((intSpeed >0)&&((intSpeed - 5)<0)){
                intSpeed = 0;
                System.out.println(intSpeed);
            }
        });


        Acceleration.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                tAccel.start();
                tDecel.stop();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                tAccel.stop();
                tDecel.start();
            }
        });

        tBrake = new Timer(1000, e ->{
            if((intSpeed - 60) > 0){ // The brakes decelerates the car at 60 km/h
                intSpeed -= 60;
                System.out.println(intSpeed);
            }
            else if ((intSpeed >0)&&((intSpeed - 60)<0)){ // The speed should not go below 0
                intSpeed = 0;
                System.out.println(intSpeed);
            }
        });

        Brakes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                tBrake.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                tBrake.stop();
            }
        });


        tBat = new Timer(1000, e->{
                BPLabel.setText("Battery Percent: " + calculateTime() + "%"); // The battery percentage should be recalculated every second
        });
        tBat.start();

        Recharge.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(charging == false){ // If the car wasn't charging, it starts charging
                    charging = true;

                    tCharge.start();
                }
                else if(charging == true){ // If the car was charging, it stops
                    charging = false;

                    tCharge.stop();
                    intCount2 = 0;
                }

            }
        });

        tCharge = new Timer( 6000, e ->{
            if(flPercent < 100){
                flPercent += 1; // If the car wasn't fully charged, it increases by one
            }
            else{
                intCount1 = 0; // If the car is charged, the warning message resets
                if(intCount2 == 0){
                    JOptionPane.showMessageDialog(null, "Battery is fully charged", "Battery", JOptionPane.INFORMATION_MESSAGE, null);
                    intCount2 = 1;
                }

            }
        });
    }

    public float calculateTime() {
        intDistance = ((intSpeed / 60) / 60);

        flPercent -= ((intDistance / 499) * 100);

        if ((flPercent < 10)&&(intCount1 == 0)) {
            warning();
            intCount1 = 1;
        }

        return flPercent;
    }

    public void warning(){
        JOptionPane.showMessageDialog(null, "Battery is low", "Warning", JOptionPane.WARNING_MESSAGE, null);
    }
}

/**
 * Created by freddeng on 2018-03-01.
 */

package ui;

import resources.Constants;

import javax.swing.*;

public class MainWindow extends JFrame {

    private final static int frame_width = 1125;
    private final static int frame_height = 720;

    public MainWindow () {
        super();

        setSize(frame_width, frame_height);
        setUndecorated(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Constants.background_grey);

        InstrumentPanel panel = new InstrumentPanel();
        ContainerLG container_large = new ContainerLG();
        ContainerSM container_small = new ContainerSM();

        add(panel);
        add(container_large);
        add(container_small);

        setVisible(true);

    }


}

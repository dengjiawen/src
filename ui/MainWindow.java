/**
 * Created by freddeng on 2018-03-01.
 */

package ui;

import resources.Constants;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        MusicPlayerPanelSM container_small = new MusicPlayerPanelSM();

        add(new StatusBarPanel());
        add(new MapPanelLG());
        add(new CoreControlBarPanel());
        add(panel);
        add(container_large);
        add(container_small);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    TurningSignal.left.activate();
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    TurningSignal.right.activate();
                }
            }
        });

        setVisible(true);

    }


}

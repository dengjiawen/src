package ui;

import resources.Constants;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-01.
 */
public class ContainerLG extends JPanel {

    private static final int panel_width_expanded = 725;
    private static final int panel_height_expanded = 605;

    private static final int panel_width_contracted = 725;
    private static final int panel_height_contracted = 420;

    MapPanelLG map_lg;

    public ContainerLG () {

        setBounds(380,20, panel_width_contracted ,panel_height_contracted);
        setBackground(new Color(0,0,0,0));
        setOpaque(false);

    }

}

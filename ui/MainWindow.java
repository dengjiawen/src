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

    public static MainWindow window;

    private static NegotiablePanel current_XL;
    private static NegotiablePanel current_SM;
    private static NegotiablePanel current_LG;

    private ControlPanelSM control_sm;
    private WeatherPanelSM weather_sm;
    private MusicPlayerPanelSM music_sm;
    private ACPanelSM ac_panel_sm;

    private VolumePanelSM volume_sm;
    private WarningPanelSM battery_warning;

    private MapPanelSM map_sm;
    private MapPanelLG map_lg;
    private MapPanelXL map_xl;

    private StatusBarPanel status_bar;
    private CoreControlBarPanel core_bar;

    private InstrumentPanel instrument;

    MusicPlayerPanelLG music_lg;

    public MainWindow () {
        super();

        window = this;

        setSize(frame_width, frame_height);
        setUndecorated(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Constants.BACKGROUND_GREY);

        control_sm = new ControlPanelSM();
        weather_sm = new WeatherPanelSM();
        music_sm = new MusicPlayerPanelSM();
        ac_panel_sm = new ACPanelSM();

        volume_sm = new VolumePanelSM();
        battery_warning = new WarningPanelSM();

        control_sm.setVisible(false);
        weather_sm.setVisible(false);
        weather_sm.setActive(false);
        music_sm.setVisible(false);
        ac_panel_sm.setVisible(false);

        volume_sm.setVisible(false);
        battery_warning.setVisible(false);

        map_sm = new MapPanelSM();
        map_sm.setVisible(false);
        map_sm.setActive(false);
        map_lg = new MapPanelLG();
        map_lg.setVisible(false);
        map_lg.setActive(false);
        map_xl = new MapPanelXL();

        current_SM = null;
        current_LG = null;
        current_XL = map_xl;

        status_bar = new StatusBarPanel();
        core_bar = new CoreControlBarPanel();

        instrument = new InstrumentPanel();

        add(status_bar);
        add(core_bar);
        add(instrument);

        add(control_sm);
        add(weather_sm);
        add(music_sm);
        add(ac_panel_sm);

        add(volume_sm);
        add(battery_warning);

        add(map_lg);
        add(map_xl);
        add(map_sm);

        music_lg = new MusicPlayerPanelLG();
        music_lg.music_panel_sm = music_sm;
        music_lg.setVisible(false);

        add(music_lg);

        setVisible(true);

    }

    public void negotiateTransition (int desired_state, NegotiablePanel panel) {
        current_LG.setVisible(false);
        current_LG.setActive(false);
        current_LG = null;

        current_SM.setVisible(false);
        current_SM.setActive(false);
        current_SM.updateInvoker();

        map_lg.setVisible(true);
        map_lg.setActive(true);
        current_LG = map_lg;

        panel.setVisible(true);
        panel.setActive(true);
        current_SM = panel;

    }

    public void negotiateSpace (int desired_state, NegotiablePanel panel) {
        if (desired_state == Constants.WindowConstants.STATE_SM && current_SM != null) {
            current_SM.setVisible(false);
            current_SM.setActive(false);
            current_SM.updateInvoker();

            if (current_SM == map_sm) {

                current_LG.setVisible(false);
                current_LG.setActive(false);
                current_LG.updateInvoker();

                map_lg.setVisible(true);
                map_lg.setActive(true);
                current_LG = map_lg;
            }

            panel.setVisible(true);
            panel.setActive(true);
            current_SM = panel;

        } else if (desired_state == Constants.WindowConstants.STATE_SM && current_SM == null) {
            current_XL.setVisible(false);
            current_XL.setActive(false);
            current_XL.updateInvoker();

            if (current_XL == map_xl) {
                map_lg.setVisible(true);
                map_lg.setActive(true);

                current_LG = map_lg;
            }

            panel.setVisible(true);
            panel.setActive(true);
            current_SM = panel;

        } else if (desired_state == Constants.WindowConstants.STATE_LG) {

            map_lg.setVisible(false);
            map_lg.setActive(false);
            map_lg.updateInvoker();

            current_SM.setVisible(false);
            current_SM.setActive(false);
            if (current_SM != music_sm) {
                current_SM.updateInvoker();
            }

            map_sm.setVisible(true);
            map_sm.setActive(true);
            current_SM = map_sm;

            panel.setVisible(true);
            panel.setActive(true);
            current_LG = panel;

        } else if (desired_state == Constants.WindowConstants.STATE_IDLE) {

            if (current_LG == map_lg) {
                map_lg.setVisible(false);
                map_lg.setActive(false);
                map_lg.updateInvoker();

                current_LG = null;

                panel.setVisible(false);
                panel.setActive(false);
                panel.updateInvoker();

                current_SM = null;
            } else if (current_SM == map_sm) {
                map_sm.setVisible(false);
                map_sm.setActive(false);
                map_sm.updateInvoker();

                current_SM = null;

                current_LG.setVisible(false);
                current_LG.setActive(false);
                current_LG.updateInvoker();

                current_LG = null;
            }

            map_xl.setVisible(true);
            map_xl.setActive(true);
            map_xl.updateInvoker();

            current_XL = map_xl;

        }

        RenderingService.invokeRepaint();
    }


}

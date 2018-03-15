package ui;

import resources.Constants;
import resources.Resources;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by freddeng on 2018-03-13.
 */
public class MusicPlayerPanelLG extends ContainerLG implements NegotiablePanel {

    public MusicPlayerPanelSM music_panel_sm;

    private MusicPhoneSubPanel connect;

    private boolean show_placeholder;

    private ModifiedToggleButton stream;
    private ModifiedToggleButton radio;
    private ModifiedToggleButton device;

    private GlowButton mini;

    public MusicPlayerPanelLG () {

        super();
        setLayout(null);

        connect = new MusicPhoneSubPanel();

        stream = new ModifiedToggleButton(Resources.music_lg_stream, 2, 261, 187, 85, 16);
        stream.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                stream.forceState(1);
                radio.forceState(0);
                device.forceState(0);

                connect.setVisible(false);
                show_placeholder = true;

                RenderingService.invokeRepaint();
            }
        });

        radio = new ModifiedToggleButton(Resources.music_lg_radio, 2, 388, 184, 55, 22);
        radio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                radio.forceState(1);
                stream.forceState(0);
                device.forceState(0);

                connect.setVisible(false);
                show_placeholder = true;

                RenderingService.invokeRepaint();
            }
        });

        device = new ModifiedToggleButton(Resources.music_lg_device, 2, 486, 183, 73, 23);
        device.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                device.forceState(1);
                stream.forceState(0);
                radio.forceState(0);

                connect.setVisible(true);
                show_placeholder = false;

                RenderingService.invokeRepaint();
            }
        });

        mini = new GlowButton(Resources.music_mini_button, 493, 384, 212, 26);
        mini.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                MainWindow.window.negotiateTransition(Constants.WindowConstants.STATE_SM, music_panel_sm);
            }
        });

        device.forceState(1);

        add(mini);

        add(connect);

        add(stream);
        add(radio);
        add(device);

        MusicPlayerPanelSM.panel_lg = this;

    }

    protected void paintComponent (Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape clip = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), Constants.ROUNDNESS, Constants.ROUNDNESS);
        g2d.clip(clip);

        GradientPaint primary = new GradientPaint(
                0f, 0f, Constants.MUSIC_LG_GRADIENT_STOP_0, getWidth(), getHeight() - music_panel_sm.getHeight(),
                Constants.MUSIC_LG_GRADIENT_STOP_1);

        g2d.setPaint(primary);
        g2d.fillRect(0, music_panel_sm.getHeight(), getWidth(), getHeight() - music_panel_sm.getHeight());

        g2d.setColor(Constants.MUSIC_LG_SUBPANEL_BACKGROUND);
        g2d.fillRect(0, music_panel_sm.getHeight(), getWidth(), 39);

        g2d.drawImage(Resources.music_lg_search, 54, 182, 151, 25, null);

        super.paintComponent(g2d);

        if (show_placeholder) {
            g2d.setColor(Color.black);
            g2d.setFont(Resources.music_lg_favorite_font);

            int x = (getWidth() - g2d.getFontMetrics(Resources.music_lg_favorite_font).stringWidth("THIS FUNCTION IS NOT AVAILABLE IN THIS PROTOTYPE"))/2;
            g2d.drawString("THIS FUNCTION IS NOT AVAILABLE IN THIS PROTOTYPE", x, 305);
        }
    }

    @Override
    public void updateInvoker() {
        music_panel_sm.updateInvoker();
    }

    @Override
    public void setActive(boolean is_active) {

        if (is_active) {
            MainWindow.window.remove(music_panel_sm);
            music_panel_sm.setMode(Constants.WindowConstants.STATE_LG);
            music_panel_sm.setLocation(0, 0);
            music_panel_sm.setVisible(true);
            music_panel_sm.setActive(true);
            add(music_panel_sm);
            Resources.invokeStatusBarDarkMode(true);
            RenderingService.invokeRepaint();
        }
        else {
            remove(music_panel_sm);
            music_panel_sm.setMode(Constants.WindowConstants.STATE_SM);
            music_panel_sm.setVisible(false);
            music_panel_sm.setActive(false);
            music_panel_sm.setLocation(380, 450);
            MainWindow.window.add(music_panel_sm);
            Resources.invokeStatusBarDarkMode(false);
            RenderingService.invokeRepaint();
        }
    }
}

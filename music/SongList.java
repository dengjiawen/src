package music;

import music.Resources;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by freddeng on 2018-03-07.
 */
public class SongList {

    private static final int BACKDROP = 0;
    private static final int HIGHLIGHT = 1;
    private static final int BUTTON = 2;

    private ArrayList<Music> music;
    private BufferedImage[] assets;

    private String list_name;

    public SongList (int[] music_numbers) {
        music = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            music.add(Resources.music[music_numbers[i]]);
        }

        assets = new BufferedImage[3];

        int album_number = 0;
        if (music.get(0).getAlbum().equals("Frozen OST")) {
            album_number = 0;
        } else if (music.get(0).getAlbum().equals("Nine Track Mind")) {
            album_number = 1;
        } else if (music.get(0).getAlbum().equals("La La Land OST")) {
            album_number = 2;
        }

        list_name = music.get(0).getAlbum();

        assets[BACKDROP] = resources.Resources.music_backdrop_SM[album_number];
        assets[HIGHLIGHT] = resources.Resources.music_highlight[album_number];
        assets[BUTTON] = resources.Resources.music_button[album_number];
    }

    public Music getMusic (int index) {
        return this.music.get(index);
    }

    public BufferedImage getBackdrop () {
        return assets[BACKDROP];
    }

    public BufferedImage getHighlight () {
        return assets[HIGHLIGHT];
    }

    public BufferedImage getButton () {
        return assets[BUTTON];
    }

    public int getIndex (Music music) {
        return this.music.indexOf(music);
    }

    public String getName () {
        return list_name;
    }

}

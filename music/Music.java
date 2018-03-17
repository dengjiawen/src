package music;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import information.InformationService;
import kuusisto.tinysound.TinySound;
import ui.LoadFrame;

import javax.swing.*;
import java.io.*;

/**
 * Created by freddeng on 2018-03-07.
 */
public class Music {

    private kuusisto.tinysound.Music music;

    private String music_name;
    private String artist_name;
    private String album_name;
    private int music_length;

    public Music (String path) {

        InputStream is = null;
        OutputStream os = null;
        File temp_file = null;

        try {
            is = this.getClass().getResourceAsStream(path);
            temp_file = File.createTempFile(path, "_tmp");
            temp_file.deleteOnExit();

            os = new FileOutputStream(temp_file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Mp3File mp3_file = null;
        try {
            mp3_file = new Mp3File(temp_file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mp3_file.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mp3_file.getId3v1Tag();
            music_name = id3v1Tag.getTitle();
            artist_name = id3v1Tag.getArtist();
            album_name = id3v1Tag.getAlbum();
        } else if (mp3_file.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3_file.getId3v2Tag();
            music_name = id3v2Tag.getTitle();
            artist_name = id3v2Tag.getArtist();
            album_name = id3v2Tag.getAlbum();
        }

        music_length = (int)mp3_file.getLengthInSeconds();
        music = TinySound.loadMusic(MusicController.class.getResource(path), true);

        SwingUtilities.invokeLater(() -> LoadFrame.requestLoadPanelReference().updateLoadedAsset(path));

    }

    public void play () {
        music.play(false, InformationService.current_volume);
    }

    public void updateVolume () {
        music.setVolume(InformationService.current_volume);
    }

    public void rewind () {
        music.rewind();
    }

    public void pause () {
        music.pause();
    }

    public void resume () {
        music.resume();
    }

    public void stop () {
        music.stop();
    }

    public String getName () {
        return music_name;
    }

    public String getArtist () {
        return artist_name;
    }

    public String getAlbum () {
        return album_name;
    }

    public int getLength () {
        return music_length;
    }

}

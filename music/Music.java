/**
 * Copyright 2018 (C) Jiawen Deng, Ann J.S. and Kareem D. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng, Ann J.S. and Kareem D. (I-LU-V-EH)
 *
 * Java programmers wear glasses because they can't C#.
 *
 *-----------------------------------------------------------------------------
 * Music.java
 *-----------------------------------------------------------------------------
 * Music object, containing instance variables such as music name, artist
 * name, album name, duration, etc
 *-----------------------------------------------------------------------------
 */

package music;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import information.*;
import kuusisto.tinysound.TinySound;
import ui.LoadFrame;
import javax.swing.*;
import java.io.*;

public class Music {

    private kuusisto.tinysound.Music music; // the actual music

    private String music_name;      // name of the music
    private String artist_name;     // name of the artist
    private String album_name;      // name of the album
    private int music_length;       // duration of the music in seconds

    /**
     * Constructor
     * @param path  path to the music file
     */
    Music (String path) {

        InputStream is = null;
        OutputStream os = null;
        File temp_file = null;

        // copy music to a temporary file to be read
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

        // parse music meta-data
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

        information.Console.printGeneralMessage("New Music object created, " + getName());

    }

    /**
     * Method that plays music
     */
    void play () {
        music.play(false, InformationService.current_volume);
    }

    /**
     * Method that updates music volume
     */
    void updateVolume () {
        music.setVolume(InformationService.current_volume);
    }

    /**
     * Method that rewinds to beginning
     */
    void rewind () {
        music.rewind();
    }

    /**
     * Method that pauses the music
     */
    void pause () {
        music.pause();
    }

    /**
     * Method that resumes the music
     */
    void resume () {
        music.resume();
    }

    /**
     * Method that gets the album name
     * @return  String of album name
     */
    String getAlbum () {
        return album_name;
    }

    /**
     * Method that stops the music
     */
    public void stop () {
        music.stop();
    }

    /**
     * Method that gets the name of the music
     * @return  music name
     */
    public String getName () {
        return music_name;
    }

    /**
     * Method that gets the artist name of the music
     * @return  artist name
     */
    public String getArtist () {
        return artist_name;
    }

    /**
     * Method that gets the duration of the music in seconds
     * @return  music duration in seconds
     */
    public int getLength () {
        return music_length;
    }

}

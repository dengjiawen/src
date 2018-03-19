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
 * Why don't keyboards sleep?
 * Because they have two shifts...
 *
 *-----------------------------------------------------------------------------
 * SongList.java
 *-----------------------------------------------------------------------------
 * A list of Music objects.
 *-----------------------------------------------------------------------------
 */

package music;

import information.Console;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SongList {

    private static final int BACKDROP = 0;  // asset modifiers/irrelevant
    private static final int HIGHLIGHT = 1;
    private static final int BUTTON = 2;

    // arraylist of Music objects
    // and a collection of image assets
    private ArrayList<Music> music;
    private BufferedImage[] assets;

    // a String of the name of the songlist
    private String list_name;

    /**
     * Constructor
     * @param music_numbers index of music to be included
     */
    SongList (int[] music_numbers) {
        music = new ArrayList<>(4);

        Console.printGeneralMessage("Initializing SongList object with " + music.toString());

        for (int i = 0; i < 4; i++) {
            music.add(Resources.music[music_numbers[i]]);
        }

        // initialize assets based on the album name
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

    /**
     * Method that gets a music at an index on the list
     * @param index music index
     * @return  corresponding Music object
     */
    public Music getMusic (int index) {
        return this.music.get(index);
    }

    /**
     * Method that gets various assets associated with the SongList.
     */
    public BufferedImage getBackdrop () {
        return assets[BACKDROP];
    }

    public BufferedImage getHighlight () {
        return assets[HIGHLIGHT];
    }

    public BufferedImage getButton () {
        return assets[BUTTON];
    }

    /**
     * Method that gets the index of a music on the list.
     * @param music target music
     * @return  corresponding index
     */
    public int getIndex (Music music) {
        return this.music.indexOf(music);
    }

    /**
     * Method that gets the name of the SongList
     * @return  String of the name of the SongList
     */
    public String getName () {
        return list_name;
    }

}

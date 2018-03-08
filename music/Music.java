package music;

/**
 * Created by freddeng on 2018-03-07.
 */
public class Music {

    private sound.Music music;

    private String music_name;
    private String artist_name;
    private int music_length;

    public Music (int music_number) {

        music = Resources.music[music_number];
        music_name = Resources.music_names[music_number];
        artist_name = Resources.artist_names[music_number];
        music_length = Resources.music_lengths[music_number];

    }

    public void play () {
        music.play(false);
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

    public int getLength () {
        return music_length;
    }

}

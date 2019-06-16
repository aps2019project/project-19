package view.Graphic;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Music {
    private static String mainMenuMusicPath = "src/view/Graphic/music/music_mainmenu.m4a";
    private static Media mainMenuMusic = new Media(new File(mainMenuMusicPath).toURI().toString());
    private static MediaPlayer mainMenuMusicPlayer = new MediaPlayer(mainMenuMusic);

    public static MediaPlayer getMainMenuMusicPlayer() {
        return mainMenuMusicPlayer;
    }

    public static void stopAllMusics(){
        mainMenuMusicPlayer.pause();
    }
}

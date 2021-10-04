package com.alcachofra.xinada;

import android.app.Application;
import android.media.MediaPlayer;

public class Music extends Application {

    private static MediaPlayer media;
    private static boolean playing = false;

    private static void init(MediaPlayer media) {
        Music.media = media;
    }

    public static boolean isPlaying() {
        return playing;
    }

    public static void stop() {
        media.pause();
        playing = false;
    }

    public static void start() {
        media.start();
        playing = true;
    }

    public static boolean toggle() {
        if (isPlaying()) {
            stop();
            return false;
        }
        else {
            start();
            return true;
        }
    }

    public static final class Builder {
        private MediaPlayer media;

        public Builder setMediaPlayer(MediaPlayer media) {
           this.media = media;
            return this;
        }

        public void build() {
            if (media == null) throw new RuntimeException("Music Context not set.");
            Music.init(media);
        }
    }
}
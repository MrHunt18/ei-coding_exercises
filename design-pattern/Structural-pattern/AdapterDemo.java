interface MediaPlayer { void play(String file); }

class Mp3Player implements MediaPlayer {
    public void play(String file) { System.out.println("Playing MP3: " + file); }
}

class Mp4Player {
    public void playMp4(String file) { System.out.println("Playing MP4: " + file); }
}

class MediaAdapter implements MediaPlayer {
    private Mp4Player mp4 = new Mp4Player();
    public void play(String file) { mp4.playMp4(file); }
}

public class AdapterDemo {
    public static void main(String[] args) {
        MediaPlayer player = new Mp3Player();
        player.play("song.mp3");

        player = new MediaAdapter();
        player.play("video.mp4");
    }
}

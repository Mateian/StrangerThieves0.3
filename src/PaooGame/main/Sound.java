package PaooGame.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    // The file that's going to be streamed
    Clip clip;

    // Control
    FloatControl fc;
    public int volumeScale = 3;
    float volume;

    // Array of all sounds used in the game
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/musicBackground.wav");
        soundURL[5] = getClass().getResource("/sound/punch.wav");
        soundURL[6] = getClass().getResource("/sound/death.wav");
    }

    public void setFile(int i) {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
    public void checkVolume() {
        switch(volumeScale) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -15f;
                break;
            case 2:
                volume = -6f;
                break;
            case 3:
                volume = 0f;
                break;
            case 4:
                volume = 6f;
                break;
        }
        fc.setValue(volume);
    }
}

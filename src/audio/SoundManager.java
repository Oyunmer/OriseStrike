package audio;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFormat;

public class SoundManager {
  public static final AudioFormat AUDIO_FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, 11025.0F, 8, 1, 1, 11025.0F, true);
  
  public static final int MAX_SIMULTANEOUS_SOUNDS = 10;
  
  private boolean running = false;
  
  private final List<SoundPlayer> soundPlayers = new ArrayList<>();
  
  private static SoundManager instance;
  
  public static SoundManager getInstance() {
    if (instance == null) {
      instance = new SoundManager();
      instance.start();
    } 
    return instance;
  }
  
  public boolean isRunning() {
    return this.running;
  }
  
  public void start() {
    this.running = true;
    for (int s = 0; s < 10; s++) {
      SoundPlayer soundPlayer = new SoundPlayer(s + 1, this);
      if (soundPlayer.start())
        this.soundPlayers.add(soundPlayer); 
    } 
  }
  
  public void play(Sound sound) {
    play(sound, false);
  }
  
  public void play(Sound sound, boolean loop) {
    for (SoundPlayer soundPlayer : this.soundPlayers) {
      if (soundPlayer.isAvailable()) {
        soundPlayer.play(sound, loop);
        break;
      } 
    } 
  }
  
  public void stop(Sound sound) {
    for (SoundPlayer soundPlayer : this.soundPlayers) {
      if (soundPlayer.getSound() == sound)
        soundPlayer.stop(); 
    } 
  }
  
  public void stopAll() {
    this.soundPlayers.forEach(soundPlayer -> soundPlayer.stop());
  }
}

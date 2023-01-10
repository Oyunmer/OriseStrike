package audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

public class SoundPlayer implements Runnable {
  private final int id;
  
  private final SoundManager soundManager;
  
  private Thread thread;
  
  private SourceDataLine line;
  
  private Sound sound;
  
  private boolean loop;
  
  public SoundPlayer(int id, SoundManager soundManager) {
    this.id = id;
    this.soundManager = soundManager;
  }
  
  private void createLine() throws Exception {
    Mixer mixer = AudioSystem.getMixer(null);
    DataLine.Info sourceDataLineInfo = new DataLine.Info(SourceDataLine.class, SoundManager.AUDIO_FORMAT);
    this.line = (SourceDataLine)mixer.getLine(sourceDataLineInfo);
  }
  
  public Sound getSound() {
    return this.sound;
  }
  
  public boolean start() {
    try {
      createLine();
      this.line.open();
      this.line.start();
      this.thread = new Thread(this);
      this.thread.start();
    } catch (Exception ex) {
      return false;
    } 
    return true;
  }
  
  public boolean isAvailable() {
    return (this.sound == null);
  }
  
  public void play(Sound sound, boolean loop) {
    synchronized (this) {
      this.loop = loop;
      this.sound = sound;
      notify();
    } 
  }
  
  public void run() {
    synchronized (this) {
      while (this.soundManager.isRunning()) {
        if (this.sound != null) {
          this.line.write(this.sound.getData(), 0, this.sound.getSize());
          this.line.drain();
          if (!this.loop)
            this.sound = null; 
        } 
        if (this.sound == null)
          try {
            wait();
          } catch (InterruptedException interruptedException) {} 
      } 
    } 
  }
  
  public void stop() {
    this.sound = null;
    this.loop = false;
    this.line.flush();
  }
}

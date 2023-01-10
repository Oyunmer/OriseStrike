package physics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Input extends KeyAdapter {
  public static boolean[] keyPressed = new boolean[256];
  
  public static boolean isKeyPressed(int keyCode) {
    return keyPressed[keyCode];
  }
  
  public void keyPressed(KeyEvent e) {
    keyPressed[e.getKeyCode()] = true;
  }
  
  public void keyReleased(KeyEvent e) {
    keyPressed[e.getKeyCode()] = false;
  }
}

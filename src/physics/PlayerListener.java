package physics;

public interface PlayerListener {
  void onPlayerStop();
  
  void onPlayerFoward();
  
  void onPlayerBackward();
  
  void onPlayerRotateLeft();
  
  void onPlayerRotateRight();
  
  void onPlayerJumpStart();
  
  void onPlayerJumpTop();
  
  void onPlayerJumpEnd();
  
  void onPlayerStrafe();
}

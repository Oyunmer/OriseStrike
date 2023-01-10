package renderer.core;

public class Time {
  private static boolean started;
  
  private static int fixedFrames;
  
  private static double deltaTime;
  
  private static double currentTime;
  
  private static double previousTime;
  
  private static double unprocessedTime;
  
  private static double fixedDeltaTime = 0.016666666666666666D;
  
  private static int fixedUpdateCount;
  
  private static int fps = 0;
  
  private static int fpsCount;
  
  private static double fpsTime;
  
  public static int getFixedFrames() {
    return fixedFrames;
  }
  
  public static double getCurrentTime() {
    return currentTime;
  }
  
  public static double getDeltaTime() {
    return deltaTime;
  }
  
  public static double getFixedDeltaTime() {
    return fixedDeltaTime;
  }
  
  private static void start() {
    currentTime = System.nanoTime() * 1.0E-9D;
    previousTime = currentTime - fixedDeltaTime;
  }
  
  public static void update() {
    if (!started) {
      start();
      started = true;
    } 
    currentTime = System.nanoTime() * 1.0E-9D;
    deltaTime = currentTime - previousTime;
    unprocessedTime += deltaTime;
    while (unprocessedTime >= fixedDeltaTime) {
      unprocessedTime -= fixedDeltaTime;
      fixedUpdateCount++;
      fixedFrames++;
    } 
    fpsTime += deltaTime;
    if (fpsTime >= 1.0D) {
      fps = fpsCount;
      fpsTime = (fpsCount = 0);
    } else {
      fpsCount++;
    } 
    previousTime = currentTime;
  }
  
  public static boolean needsFixedUpdate() {
    if (fixedUpdateCount > 0) {
      fixedUpdateCount--;
      return true;
    } 
    return false;
  }
  
  public static int getFPS() {
    return fps;
  }
  
  public static long delta2 = 0L;
  
  public static int fps2 = 0;
  
  private static int fpsCount2;
  
  private static long fpsTime2;
  
  private static long lastTime2;
  
  public static void updateFPS2() {
    long currentTime2 = System.nanoTime();
    delta2 = currentTime2 - lastTime2;
    fpsTime2 += delta2;
    if (fpsTime2 > 1000000000L) {
      fps2 = fpsCount2;
      fpsTime2 = (fpsCount2 = 0);
    } else {
      fpsCount2++;
    } 
    lastTime2 = currentTime2;
  }
}

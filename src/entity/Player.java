package entity;

import audio.SoundManager;
import audio.Sounds;
import input.Mouse;
import java.util.List;
import md2.MD2Obj;
import physics.Input;
import physics.PhysicsPlayer;
import physics.PlayerListener;
import renderer.core.Image;
import renderer.core.Material;
import renderer.core.Renderer;
import renderer.math.Vec3;
import renderer.parser.wavefront.Obj;

public class Player extends Entity implements PlayerListener, Runnable {
  private PhysicsPlayer player;
  
  private final List<Obj> objsPlayer;
  
  private MD2Obj md2FPS;
  
  private MD2Obj md2Current;
  
  private Material playerMd2Material = new Material("player");
  
  private boolean fire;
  
  private boolean reloading;
  
  private long fireTime;
  
  public Player(PhysicsPlayer player, List<Obj> objsPlayer) {
    this.player = player;
    player.setListener(this);
    this.objsPlayer = objsPlayer;
    try {
      this.md2FPS = new MD2Obj("/res/fps_gun.md2", 40.0D);
      this.md2FPS.createAnimation("idle", 0, 180);
      this.md2FPS.createAnimation("reload", 181, 249);
      this.md2FPS.createAnimation("fire", 250, 266);
      this.md2FPS.createAnimation("walk", 267, 290);
      this.md2Current = this.md2FPS;
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    } 
    this.playerMd2Material.map_kd = new Image("/res/Arm_Texture_Digital_Camo_Desert_01.png");
    Mouse.addPressedListener(this);
  }
  
  public void update() {
    this.player.update();
    if (!this.reloading && !this.fire && Input.isKeyPressed(82)) {
      this.reloading = true;
      this.md2FPS.setAnimation("reload");
      SoundManager.getInstance().play(Sounds.RELOAD);
    } 
    this.md2Current.nextFrame(0.5D);
    if (this.fire && "fire".equals(this.md2FPS.getCurrentAnimation()) && this.md2Current.getCurrentFrame() >= this.md2Current.getEndFrame()) {
      this.md2FPS.setAnimation("idle");
      this.fire = false;
    } else if (this.reloading && "reload".equals(this.md2FPS.getCurrentAnimation()) && this.md2Current.getCurrentFrame() >= this.md2Current.getEndFrame()) {
      this.md2FPS.setAnimation("idle");
      this.reloading = false;
    } 
  }
  
  public void draw(Renderer renderer) {
    Vec3 playerPosition = this.player.getCollider().getPosition();
    renderer.translate(playerPosition.x, playerPosition.y, playerPosition.z);
    renderer.rotateY(this.player.getAngle() + Math.toRadians(180.0D));
    renderer.translate(0.0D, 65.0D, 6.0D);
    for (int t = 0; t < (this.md2Current.getTriangles()).length; t++) {
      renderer.setMaterial(this.playerMd2Material);
      renderer.begin();
      for (int p = 0; p < 3; p++) {
        double[] tv = this.md2Current.getTriangleVertex(t, p);
        renderer.setTextureCoordinates(tv[6], 1.0D - tv[7]);
        renderer.setNormal(tv[4], tv[5], tv[3]);
        renderer.setVertex(tv[1], tv[2], tv[0]);
      } 
      renderer.end();
    } 
  }
  
  public void onPlayerJumpStart() {}
  
  public void onPlayerJumpTop() {}
  
  public void onPlayerJumpEnd() {}
  
  public void onPlayerFoward() {
    if (!this.reloading && !this.fire && !"walk".equals(this.md2FPS.getCurrentAnimation())) {
      this.md2FPS.setAnimation("walk");
      SoundManager.getInstance().play(Sounds.WALKING, true);
    } 
  }
  
  public void onPlayerBackward() {
    if (!this.reloading && !this.fire && !"walk".equals(this.md2FPS.getCurrentAnimation())) {
      this.md2FPS.setAnimation("walk");
      SoundManager.getInstance().play(Sounds.WALKING, true);
    } 
  }
  
  public void onPlayerRotateLeft() {
    if (!this.reloading && !this.fire && !"walk".equals(this.md2FPS.getCurrentAnimation())) {
      this.md2FPS.setAnimation("walk");
      SoundManager.getInstance().play(Sounds.WALKING, true);
    } 
  }
  
  public void onPlayerRotateRight() {
    if (!this.reloading && !this.fire && !"walk".equals(this.md2FPS.getCurrentAnimation())) {
      this.md2FPS.setAnimation("walk");
      SoundManager.getInstance().play(Sounds.WALKING, true);
    } 
  }
  
  public void onPlayerStrafe() {
    if (!this.reloading && !this.fire && !"walk".equals(this.md2FPS.getCurrentAnimation())) {
      this.md2FPS.setAnimation("walk");
      SoundManager.getInstance().play(Sounds.WALKING, true);
    } 
  }
  
  public void onPlayerStop() {
    if (!this.reloading && !this.fire && !"idle".equals(this.md2FPS.getCurrentAnimation())) {
      this.md2FPS.setAnimation("idle");
      SoundManager.getInstance().stop(Sounds.WALKING);
    } 
  }
  
  public void run() {
    if (!this.reloading && System.currentTimeMillis() - this.fireTime > 150L) {
      this.fire = true;
      this.fireTime = System.currentTimeMillis();
      this.md2FPS.setAnimation("fire");
      SoundManager.getInstance().play(Sounds.SHOOT);
    } 
  }
}

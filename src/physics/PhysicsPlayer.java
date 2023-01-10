package physics;

import java.awt.Graphics2D;
import physics.partition.Box;
import renderer.core.FpsMouseCapturer;
import renderer.math.Vec3;

public class PhysicsPlayer {
  private Sphere collider = new Sphere();
  
  private double angle;
  
  private final Vec3 velocity = new Vec3();
  
  private final Box box = new Box();
  
  private PlayerListener listener;
  
  private boolean jumping;
  
  private FpsMouseCapturer fpsMouseCapturer;
  
  public PhysicsPlayer(FpsMouseCapturer fpsMouseCapturer) {
    this.fpsMouseCapturer = fpsMouseCapturer;
    this.collider = new Sphere(80.0D, 257.1D, 71.6D, -1064.1D);
  }
  
  public void setListener(PlayerListener listener) {
    this.listener = listener;
  }
  
  public Sphere getCollider() {
    return this.collider;
  }
  
  public double getAngle() {
    return this.angle;
  }
  
  public Vec3 getVelocity() {
    return this.velocity;
  }
  
  public void update() {
    double targetAngle = this.fpsMouseCapturer.getValue().getX();
    this.angle += (targetAngle - this.angle) * 0.1D;
    double speedAdd = 0.75D;
    double speed = 0.0D;
    if (Input.isKeyPressed(87)) {
      speed = -speedAdd;
      if (this.listener != null)
        this.listener.onPlayerFoward(); 
    } else if (Input.isKeyPressed(83)) {
      speed = speedAdd;
      if (this.listener != null)
        this.listener.onPlayerBackward(); 
    } 
    this.velocity.x *= 0.95D;
    this.velocity.z *= 0.95D;
    if (speed != 0.0D) {
      double dx = speed * Math.cos(this.angle + Math.toRadians(90.0D));
      double dz = speed * Math.sin(this.angle + Math.toRadians(90.0D));
      this.velocity.x += dx;
      this.velocity.z += dz;
    } 
    if (Input.isKeyPressed(65)) {
      speed = speedAdd;
      double dx = speed * Math.cos(this.angle - Math.toRadians(180.0D));
      double dz = speed * Math.sin(this.angle - Math.toRadians(180.0D));
      this.velocity.x += dx;
      this.velocity.z += dz;
      if (this.listener != null)
        this.listener.onPlayerStrafe(); 
    } else if (Input.isKeyPressed(68)) {
      speed = -speedAdd;
      double dx = speed * Math.cos(this.angle - Math.toRadians(180.0D));
      double dz = speed * Math.sin(this.angle - Math.toRadians(180.0D));
      this.velocity.x += dx;
      this.velocity.z += dz;
      if (this.listener != null)
        this.listener.onPlayerStrafe(); 
    } 
    if (speed == 0.0D && 
      this.listener != null)
      this.listener.onPlayerStop(); 
    if (this.jumping && this.velocity.y > 1.0D) {
      this.jumping = false;
      if (this.listener != null)
        this.listener.onPlayerJumpEnd(); 
    } else if (Input.isKeyPressed(32)) {
      this.jumping = true;
      this.velocity.y += 10.0D;
      if (this.velocity.y > 10.0D)
        this.velocity.y = 10.0D; 
      if (this.listener != null)
        this.listener.onPlayerJumpStart(); 
    } 
    this.velocity.y -= 0.25D;
  }
  
  public void draw(Graphics2D g, double scale) {
    this.collider.draw(g, scale);
  }
  
  public void draw3D(Graphics2D g, double scale) {
    this.collider.draw3D(g, scale, this.angle, this.collider.getPosition());
  }
  
  public Box getBox() {
    this.box.getMin().set(this.collider.getPosition());
    (this.box.getMin()).x -= this.collider.getRadius();
    (this.box.getMin()).y -= this.collider.getRadius();
    (this.box.getMin()).z -= this.collider.getRadius();
    this.box.getMax().set(this.collider.getPosition());
    (this.box.getMax()).x += this.collider.getRadius();
    (this.box.getMax()).y += this.collider.getRadius();
    (this.box.getMax()).z += this.collider.getRadius();
    this.box.updateLength();
    return this.box;
  }
}

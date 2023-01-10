package physics;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import physics.partition.Octree;
import renderer.core.FpsMouseCapturer;
import renderer.math.Vec3;

public class World {
  private final List<Face> faces = new ArrayList<>();
  
  private final PhysicsPlayer player;
  
  private final List<Response> collidedResponses = new ArrayList<>();
  
  private final Vec3 resultPush = new Vec3();
  
  private MeshLoader terrain;
  
  private Octree spatialPartition;
  
  private final List<Face> retrievedFaces = new ArrayList<>();
  
  public World(FpsMouseCapturer fpsMouseCapturer) {
    this.player = new PhysicsPlayer(fpsMouseCapturer);
    createLevel();
  }
  
  private void createLevel() {
    this.terrain = new MeshLoader();
    try {
      this.terrain.load("/res/cs_collider.obj", 100.0D, 0.0D, 0.0D, 0.0D);
    } catch (Exception ex) {
      Logger.getLogger(World.class.getName()).log(Level.SEVERE, (String)null, ex);
      System.exit(1);
    } 
    this.faces.addAll(this.terrain.getFaces());
    this.spatialPartition = new Octree(this.terrain.getMin(), this.terrain.getMax());
    this.spatialPartition.addFaces(this.terrain.getFaces());
    this.player.getCollider().getPosition().set(100.0D, 1.0D, 100.0D);
  }
  
  public PhysicsPlayer getPlayer() {
    return this.player;
  }
  
  public void update() {
    this.player.update();
    this.collidedResponses.clear();
    this.retrievedFaces.clear();
    this.spatialPartition.retrieveFaces(this.player.getBox(), this.retrievedFaces);
    for (Face face : this.retrievedFaces) {
      Response response = face.checkCollision(this.player.getCollider());
      if (response.isCollides())
        this.collidedResponses.add(response); 
    } 
    this.resultPush.set(0.0D, 0.0D, 0.0D);
    if (!this.collidedResponses.isEmpty()) {
      this.collidedResponses.forEach(response -> this.resultPush.add(response.getContactNormal()));
      this.player.getCollider().getPosition().add(this.resultPush);
    } 
    if (!this.collidedResponses.isEmpty()) {
      this.resultPush.normalize();
      double dot = this.resultPush.dot(this.player.getVelocity());
      this.resultPush.scale(dot);
      this.player.getVelocity().sub(this.resultPush);
    } 
    this.player.getCollider().getPosition().add(this.player.getVelocity());
  }
  
  public void draw(Graphics2D g) {
    double scale = 50.0D;
    this.player.draw3D(g, scale);
    this.faces.forEach(face -> face.draw3D(g, scale, this.player.getAngle(), this.player.getCollider().getPosition()));
  }
}

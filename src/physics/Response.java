package physics;

import renderer.math.Vec3;

public class Response {
  private boolean collides;
  
  private final Vec3 contactPoint = new Vec3();
  
  private final Vec3 contactNormal = new Vec3();
  
  public boolean isCollides() {
    return this.collides;
  }
  
  public void setCollides(boolean collides) {
    this.collides = collides;
  }
  
  public Vec3 getContactPoint() {
    return this.contactPoint;
  }
  
  public Vec3 getContactNormal() {
    return this.contactNormal;
  }
  
  public String toString() {
    return "Response{collides=" + this.collides + ", contactPoint=" + this.contactPoint + ", contactNormal=" + this.contactNormal + '}';
  }
}

package physics.partition;

import renderer.math.Vec3;

public class Box {
  protected final Vec3 min = new Vec3();
  
  protected final Vec3 max = new Vec3();
  
  protected final Vec3 length = new Vec3();
  
  public Box() {}
  
  public Box(Vec3 min, Vec3 max) {
    this.min.set(min);
    this.max.set(max);
    updateLengthInternal();
  }
  
  public void updateLength() {
    updateLengthInternal();
  }
  
  private void updateLengthInternal() {
    this.length.set(this.max);
    this.length.sub(this.min);
    this.length.x = Math.abs(this.length.x);
    this.length.y = Math.abs(this.length.y);
    this.length.z = Math.abs(this.length.z);
  }
  
  public Vec3 getMin() {
    return this.min;
  }
  
  public Vec3 getMax() {
    return this.max;
  }
  
  public Vec3 getLength() {
    return this.length;
  }
  
  public boolean intersects(Box box) {
    double xt1 = box.length.x + this.length.x;
    double xt2 = Math.abs(Math.max(this.max.x, box.max.x) - 
        Math.min(this.min.x, box.min.x));
    if (xt2 > xt1)
      return false; 
    double yt1 = box.length.y + this.length.y;
    double yt2 = Math.abs(Math.max(this.max.y, box.max.y) - 
        Math.min(this.min.y, box.min.y));
    if (yt2 > yt1)
      return false; 
    double zt1 = box.length.z + this.length.z;
    double zt2 = Math.abs(Math.max(this.max.z, box.max.z) - 
        Math.min(this.min.z, box.min.z));
    return (zt2 <= zt1);
  }
  
  public boolean isCompletelyInside(Box box) {
    return (this.min.x >= box.min.x && this.min.x <= box.max.x && this.max.x >= box.min.x && this.max.x <= box.max.x && this.min.y >= box.min.y && this.min.y <= box.max.y && this.max.y >= box.min.y && this.max.y <= box.max.y && this.min.z >= box.min.z && this.min.z <= box.max.z && this.max.z >= box.min.z && this.max.z <= box.max.z);
  }
  
  public String toString() {
    return "Box{min=" + this.min + ", max=" + this.max + '}';
  }
}

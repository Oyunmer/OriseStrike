package renderer.math;

public class Vec2 {
  public double x;
  
  public double y;
  
  public Vec2() {}
  
  public Vec2(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public void set(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public void set(Vec2 v) {
    this.x = v.x;
    this.y = v.y;
  }
  
  public void multiply(double s) {
    this.x *= s;
    this.y *= s;
  }
  
  public void setLerp(Vec2 a, Vec2 b, double p) {
    a.x += p * (b.x - a.x);
    a.y += p * (b.y - a.y);
  }
  
  public String toString() {
    return "Vec2{x=" + this.x + ", y=" + this.y + '}';
  }
}

package renderer.math;

public class Vec3 {
  public double x;
  
  public double y;
  
  public double z;
  
  public Vec3() {}
  
  public Vec3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public Vec3(Vec3 v) {
    this.x = v.x;
    this.y = v.y;
    this.z = v.z;
  }
  
  public Vec3(Vec3 a, Vec3 b) {
    b.x -= a.x;
    b.y -= a.y;
    b.z -= a.z;
  }
  
  public void set(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public void set(Vec3 v) {
    this.x = v.x;
    this.y = v.y;
    this.z = v.z;
  }
  
  public void add(Vec3 v) {
    this.x += v.x;
    this.y += v.y;
    this.z += v.z;
  }
  
  public void sub(Vec3 v) {
    this.x -= v.x;
    this.y -= v.y;
    this.z -= v.z;
  }
  
  public void scale(double s) {
    scale(s, s, s);
  }
  
  public void scale(double sx, double sy, double sz) {
    this.x *= sx;
    this.y *= sy;
    this.z *= sz;
  }
  
  public double getLength() {
    return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
  }
  
  public void normalize() {
    double length = getLength();
    if (length != 0.0D) {
      this.x /= length;
      this.y /= length;
      this.z /= length;
    } 
  }
  
  public double dot(Vec3 v) {
    return this.x * v.x + this.y * v.y + this.z * v.z;
  }
  
  public double dot2D(Vec3 v) {
    return this.x * v.x + this.z * v.z;
  }
  
  public void cross(Vec3 v) {
    double cx = this.y * v.z - this.z * v.y;
    double cy = this.z * v.x - this.x * v.z;
    double cz = this.x * v.y - this.y * v.x;
    this.x = cx;
    this.y = cy;
    this.z = cz;
  }
  
  public double cross2D(Vec3 v) {
    return this.x * v.z - this.z * v.x;
  }
  
  public void rotateY(double angle) {
    double s = Math.sin(angle);
    double c = Math.cos(angle);
    double nx = this.x * c - this.z * s;
    double nz = this.x * s + this.z * c;
    set(nx, this.y, nz);
  }
  
  public String toString() {
    return "Vec3{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
  }
}

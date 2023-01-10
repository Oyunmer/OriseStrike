package renderer.math;

public class Vec4 {
  public double x;
  
  public double y;
  
  public double z;
  
  public double w;
  
  public Vec4() {}
  
  public Vec4(double x, double y, double z, double w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }
  
  public Vec4(Vec4 v) {
    this.x = v.x;
    this.y = v.y;
    this.z = v.z;
    this.w = v.w;
  }
  
  public void set(double x, double y, double z, double w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }
  
  public void set(Vec4 v) {
    this.x = v.x;
    this.y = v.y;
    this.z = v.z;
    this.w = v.w;
  }
  
  public void add(Vec4 v) {
    this.x += v.x;
    this.y += v.y;
    this.z += v.z;
  }
  
  public void sub(Vec4 v) {
    this.x -= v.x;
    this.y -= v.y;
    this.z -= v.z;
  }
  
  public void multiply(double s) {
    this.x *= s;
    this.y *= s;
    this.z *= s;
  }
  
  public void translate(double dx, double dy, double dz) {
    this.x += dx;
    this.y += dy;
    this.z += dz;
  }
  
  public void rotateZ(double angle) {
    double s = Math.sin(angle);
    double c = Math.cos(angle);
    double nx = this.x * c - this.y * s;
    double ny = this.x * s + this.y * c;
    this.x = nx;
    this.y = ny;
  }
  
  public void doPerspectiveDivision() {
    this.x /= this.w;
    this.y /= this.w;
    this.z /= this.w;
  }
  
  public double getSize() {
    return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
  }
  
  public void normalize() {
    double sizeInv = 1.0D / getSize();
    multiply(sizeInv);
  }
  
  public double dot(Vec4 v) {
    return this.x * v.x + this.y * v.y + this.z * v.z;
  }
  
  public double getRelativeCosBetween(Vec4 v) {
    return dot(v) / getSize() * v.getSize();
  }
  
  public double getRelativeAngleBetween(Vec4 v) {
    return Math.acos(getRelativeCosBetween(v));
  }
  
  public void setLerp(Vec4 a, Vec4 b, double p) {
    a.x += p * (b.x - a.x);
    a.y += p * (b.y - a.y);
    a.z += p * (b.z - a.z);
    a.w += p * (b.w - a.w);
  }
  
  public static void cross(Vec4 a, Vec4 b, Vec4 r) {
    r.x = a.y * b.z - a.z * b.y;
    r.y = a.z * b.x - a.x * b.z;
    r.z = a.x * b.y - a.y - b.x;
  }
  
  public static void sub(Vec4 a, Vec4 b, Vec4 r) {
    a.x -= b.x;
    a.y -= b.y;
    a.z -= b.z;
  }
  
  public static void lerp(Vec4 a, Vec4 b, Vec4 r, double p) {
    p = (p < 0.0D) ? 0.0D : ((p > 1.0D) ? 1.0D : p);
    a.x += (b.x - a.x) * p;
    a.y += (b.y - a.y) * p;
    a.z += (b.z - a.z) * p;
  }
  
  public String toString() {
    return "Vec4{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", w=" + this.w + '}';
  }
}

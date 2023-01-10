package renderer.math;

public class Mat4 {
  public double m00;
  
  public double m01;
  
  public double m02;
  
  public double m03;
  
  public double m10;
  
  public double m11;
  
  public double m12;
  
  public double m13;
  
  public double m20;
  
  public double m21;
  
  public double m22;
  
  public double m23;
  
  public double m30;
  
  public double m31;
  
  public double m32;
  
  public double m33;
  
  public void set(Mat4 m) {
    this.m00 = m.m00;
    this.m01 = m.m01;
    this.m02 = m.m02;
    this.m03 = m.m03;
    this.m10 = m.m10;
    this.m11 = m.m11;
    this.m12 = m.m12;
    this.m13 = m.m13;
    this.m20 = m.m20;
    this.m21 = m.m21;
    this.m22 = m.m22;
    this.m23 = m.m23;
    this.m30 = m.m30;
    this.m31 = m.m31;
    this.m32 = m.m32;
    this.m33 = m.m33;
  }
  
  public void setIdentity() {
    this.m01 = this.m02 = this.m03 = 0.0D;
    this.m10 = this.m12 = this.m13 = 0.0D;
    this.m20 = this.m21 = this.m23 = 0.0D;
    this.m30 = this.m31 = this.m32 = 0.0D;
    this.m00 = this.m11 = this.m22 = this.m33 = 1.0D;
  }
  
  public void setPerspectiveProjection(double fov, double screenWidth) {
    double d = screenWidth * 0.5D / Math.tan(fov * 0.5D);
    setIdentity();
    this.m32 = -1.0D / d;
    this.m33 = 0.0D;
  }
  
  public void setScale(double sx, double sy, double sz) {
    setIdentity();
    this.m00 = sx;
    this.m11 = sy;
    this.m22 = sz;
    this.m33 = 1.0D;
  }
  
  public void setTranslation(double x, double y, double z) {
    setIdentity();
    this.m03 = x;
    this.m13 = y;
    this.m23 = z;
  }
  
  public void setRotationX(double angle) {
    setIdentity();
    double s = Math.sin(angle);
    double c = Math.cos(angle);
    this.m11 = c;
    this.m12 = s;
    this.m21 = -s;
    this.m22 = c;
  }
  
  public void setRotationY(double angle) {
    setIdentity();
    double s = Math.sin(angle);
    double c = Math.cos(angle);
    this.m00 = c;
    this.m02 = -s;
    this.m20 = s;
    this.m22 = c;
  }
  
  public void setRotationZ(double angle) {
    setIdentity();
    double s = Math.sin(angle);
    double c = Math.cos(angle);
    this.m00 = c;
    this.m01 = s;
    this.m10 = -s;
    this.m11 = c;
  }
  
  public void multiply(Mat4 m) {
    double nm00 = this.m00 * m.m00 + this.m01 * m.m10 + this.m02 * m.m20 + this.m03 * m.m30;
    double nm01 = this.m00 * m.m01 + this.m01 * m.m11 + this.m02 * m.m21 + this.m03 * m.m31;
    double nm02 = this.m00 * m.m02 + this.m01 * m.m12 + this.m02 * m.m22 + this.m03 * m.m32;
    double nm03 = this.m00 * m.m03 + this.m01 * m.m13 + this.m02 * m.m23 + this.m03 * m.m33;
    double nm10 = this.m10 * m.m00 + this.m11 * m.m10 + this.m12 * m.m20 + this.m13 * m.m30;
    double nm11 = this.m10 * m.m01 + this.m11 * m.m11 + this.m12 * m.m21 + this.m13 * m.m31;
    double nm12 = this.m10 * m.m02 + this.m11 * m.m12 + this.m12 * m.m22 + this.m13 * m.m32;
    double nm13 = this.m10 * m.m03 + this.m11 * m.m13 + this.m12 * m.m23 + this.m13 * m.m33;
    double nm20 = this.m20 * m.m00 + this.m21 * m.m10 + this.m22 * m.m20 + this.m23 * m.m30;
    double nm21 = this.m20 * m.m01 + this.m21 * m.m11 + this.m22 * m.m21 + this.m23 * m.m31;
    double nm22 = this.m20 * m.m02 + this.m21 * m.m12 + this.m22 * m.m22 + this.m23 * m.m32;
    double nm23 = this.m20 * m.m03 + this.m21 * m.m13 + this.m22 * m.m23 + this.m23 * m.m33;
    double nm30 = this.m30 * m.m00 + this.m31 * m.m10 + this.m32 * m.m20 + this.m33 * m.m30;
    double nm31 = this.m30 * m.m01 + this.m31 * m.m11 + this.m32 * m.m21 + this.m33 * m.m31;
    double nm32 = this.m30 * m.m02 + this.m31 * m.m12 + this.m32 * m.m22 + this.m33 * m.m32;
    double nm33 = this.m30 * m.m03 + this.m31 * m.m13 + this.m32 * m.m23 + this.m33 * m.m33;
    this.m00 = nm00;
    this.m01 = nm01;
    this.m02 = nm02;
    this.m03 = nm03;
    this.m10 = nm10;
    this.m11 = nm11;
    this.m12 = nm12;
    this.m13 = nm13;
    this.m20 = nm20;
    this.m21 = nm21;
    this.m22 = nm22;
    this.m23 = nm23;
    this.m30 = nm30;
    this.m31 = nm31;
    this.m32 = nm32;
    this.m33 = nm33;
  }
  
  public void multiply(Vec4 v) {
    double nx = this.m00 * v.x + this.m01 * v.y + this.m02 * v.z + this.m03 * v.w;
    double ny = this.m10 * v.x + this.m11 * v.y + this.m12 * v.z + this.m13 * v.w;
    double nz = this.m20 * v.x + this.m21 * v.y + this.m22 * v.z + this.m23 * v.w;
    double nw = this.m30 * v.x + this.m31 * v.y + this.m32 * v.z + this.m33 * v.w;
    v.x = nx;
    v.y = ny;
    v.z = nz;
    v.w = nw;
  }
}

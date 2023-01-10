package physics;

import java.awt.Color;
import java.awt.Graphics2D;
import renderer.math.Vec3;

public class Edge {
  private final Face face;
  
  private final Vec3 a;
  
  private final Vec3 b;
  
  private double length;
  
  private final Vec3 normal = new Vec3();
  
  private final Vec3 vTmp1 = new Vec3();
  
  private final Vec3 vTmp2 = new Vec3();
  
  private final Vec3 vTmp3 = new Vec3();
  
  private final Vec3 da;
  
  private final Vec3 db;
  
  private final Vec3 camera;
  
  private void updateLengthAndNormal() {
    this.normal.set(this.b);
    this.normal.sub(this.a);
    this.length = this.normal.getLength();
    this.normal.cross(this.face.getNormal());
    this.normal.normalize();
  }
  
  public Vec3 getA() {
    return this.a;
  }
  
  public Vec3 getB() {
    return this.b;
  }
  
  public boolean isInside(Vec3 contactPoint) {
    this.vTmp1.set(contactPoint);
    this.vTmp1.sub(this.a);
    return (this.vTmp1.dot(this.normal) >= 0.0D);
  }
  
  public Response checkCollision(Vec3 contactPoint, Sphere sphere, Response response) {
    this.vTmp1.set(contactPoint);
    this.vTmp1.sub(this.a);
    double dot = this.vTmp1.dot(this.normal);
    this.vTmp1.set(this.normal);
    this.vTmp1.scale(-dot);
    this.vTmp1.add(contactPoint);
    this.vTmp3.set(this.b);
    this.vTmp3.sub(this.a);
    this.vTmp2.set(this.vTmp1);
    this.vTmp2.sub(this.a);
    double dot1 = this.vTmp3.dot(this.vTmp3);
    double dot2 = this.vTmp3.dot(this.vTmp2);
    if (dot2 < 0.0D) {
      this.vTmp1.set(this.a);
    } else if (dot2 > dot1) {
      this.vTmp1.set(this.b);
    } 
    this.vTmp2.set(sphere.getPosition());
    this.vTmp2.sub(this.vTmp1);
    response.setCollides((this.vTmp2.getLength() <= sphere.getRadius()));
    response.getContactPoint().set(this.vTmp1);
    response.getContactNormal().set(this.vTmp2);
    response.getContactNormal().normalize();
    response.getContactNormal().scale(sphere.getRadius() - this.vTmp2.getLength());
    return response;
  }
  
  public void draw(Graphics2D g, double scale) {
    g.setColor(Color.BLACK);
    g.drawLine((int)(scale * this.a.x), (int)(scale * this.a.y), (int)(scale * this.b.x), (int)(scale * this.b.y));
  }
  
  public Edge(Face face, Vec3 a, Vec3 b) {
    this.da = new Vec3();
    this.db = new Vec3();
    this.camera = new Vec3(0.0D, -50.0D, -500.0D);
    this.face = face;
    this.a = a;
    this.b = b;
    updateLengthAndNormal();
  }
  
  public void draw3D(Graphics2D g, double scale, double angle, Vec3 translate) {
    this.da.set(this.a);
    this.da.sub(translate);
    this.da.rotateY(-angle);
    this.da.scale(scale);
    this.da.add(this.camera);
    this.db.set(this.b);
    this.db.sub(translate);
    this.db.rotateY(-angle);
    this.db.scale(scale);
    this.db.add(this.camera);
    if (this.da.z >= -0.1D)
      return; 
    if (this.db.z >= -0.1D)
      return; 
    int sax = (int)(500.0D * this.da.x / -this.da.z);
    int say = (int)(500.0D * this.da.y / -this.da.z);
    int sbx = (int)(500.0D * this.db.x / -this.db.z);
    int sby = (int)(500.0D * this.db.y / -this.db.z);
    g.setColor(Color.RED);
    g.fillOval(sax - 3, say - 3, 6, 6);
    g.fillOval(sbx - 3, sby - 3, 6, 6);
    g.setColor(Color.BLACK);
    g.drawLine(sax, say, sbx, sby);
  }
  
  public String toString() {
    return "Edge{face=" + this.face + ", a=" + this.a + ", b=" + this.b + ", normal=" + this.normal + '}';
  }
}

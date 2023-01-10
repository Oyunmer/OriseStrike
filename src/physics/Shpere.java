package physics;

import java.awt.Color;
import java.awt.Graphics2D;
import renderer.math.Vec3;

public class Sphere {
  private double radius;
  
  private Vec3 position = new Vec3();
  
  public Sphere() {}
  
  public Sphere(double radius) {
    this.radius = radius;
  }
  
  public Sphere(double radius, double x, double y, double z) {
    this.radius = radius;
    this.position.set(x, y, z);
  }
  
  public double getRadius() {
    return this.radius;
  }
  
  public void setRadius(double radius) {
    this.radius = radius;
  }
  
  public Vec3 getPosition() {
    return this.position;
  }
  
  public void setPosition(Vec3 position) {
    this.position = position;
  }
  
  public void draw(Graphics2D g, double scale) {
    g.setColor(Color.BLACK);
    int diameter = (int)(2.0D * this.radius * scale);
    g.drawOval((int)(scale * (this.position.x - this.radius)), (int)(scale * (this.position.y - this.radius)), diameter, diameter);
  }
  
  public void draw3D(Graphics2D g, double scale, double angle, Vec3 translate) {
    Camera.DA.set(this.position);
    Camera.DA.sub(translate);
    Camera.DA.rotateY(-angle);
    Camera.DA.scale(scale);
    Camera.DA.add(Camera.DISTANCE);
    Camera.DB.set(this.position);
    Camera.DB.sub(translate);
    Camera.DB.rotateY(-angle);
    Camera.DB.y -= this.radius;
    Camera.DB.scale(scale);
    Camera.DB.add(Camera.DISTANCE);
    int sax = (int)(500.0D * Camera.DA.x / -Camera.DA.z);
    int say = (int)(500.0D * Camera.DA.y / -Camera.DA.z);
    int sax2 = (int)(500.0D * Camera.DB.x / -Camera.DA.z);
    int say2 = (int)(500.0D * Camera.DB.y / -Camera.DA.z);
    int sr = Math.abs(sax - sax2);
    g.setColor(Color.BLACK);
    int radius = Math.abs(say - say2);
    int diameter = radius * 2;
    g.drawOval(sax - radius, say - radius, diameter, diameter);
  }
  
  public String toString() {
    return "Sphere{radius=" + this.radius + ", position=" + this.position + '}';
  }
}

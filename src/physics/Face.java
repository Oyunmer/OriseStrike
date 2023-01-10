package physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import renderer.math.Vec3;

public class Face {
  private final Vec3 normal = new Vec3();
  
  private final List<Vec3> points = new ArrayList<>();
  
  private final List<Edge> edges = new ArrayList<>();
  
  private final Response response = new Response();
  
  private final Vec3 vTmp;
  
  public Vec3 getNormal() {
    return this.normal;
  }
  
  public List<Vec3> getPoints() {
    return this.points;
  }
  
  public void addPoint(Vec3 point) {
    this.points.add(point);
  }
  
  public void addPoint(double x, double y, double z) {
    this.points.add(new Vec3(x, y, z));
  }
  
  public void close() {
    Vec3 v1 = new Vec3(this.points.get(0));
    v1.sub(this.points.get(1));
    Vec3 v2 = new Vec3(this.points.get(2));
    v2.sub(this.points.get(1));
    this.normal.set(v1);
    this.normal.cross(v2);
    this.normal.normalize();
    for (int i = 0; i < this.points.size(); i++) {
      Vec3 a = this.points.get(i);
      Vec3 b = this.points.get((i + 1) % this.points.size());
      Edge edge = new Edge(this, a, b);
      this.edges.add(edge);
    } 
  }
  
  public Face() {
    this.vTmp = new Vec3();
  }
  
  public Response checkCollision(Sphere sphere) {
    this.vTmp.set(sphere.getPosition());
    this.vTmp.sub(this.points.get(0));
    double dot = this.vTmp.dot(this.normal);
    this.vTmp.set(this.normal);
    this.vTmp.scale(-dot);
    this.vTmp.add(sphere.getPosition());
    for (Edge edge : this.edges) {
      if (!edge.isInside(this.vTmp))
        return edge.checkCollision(this.vTmp, sphere, this.response); 
    } 
    this.response.setCollides((Math.abs(dot) <= sphere.getRadius()));
    this.response.getContactPoint().set(this.vTmp);
    this.response.getContactNormal().set(this.normal);
    this.response.getContactNormal().scale(sphere.getRadius() - dot);
    return this.response;
  }
  
  public void draw(Graphics2D g, double scale) {
    for (Edge edge : this.edges)
      edge.draw(g, scale); 
  }
  
  public void draw3D(Graphics2D g, double scale, double angle, Vec3 translate) {
    for (Edge edge : this.edges)
      edge.draw3D(g, scale, angle, translate); 
    if (this.response.isCollides()) {
      Camera.DA.set(this.response.getContactPoint());
      Camera.DA.sub(translate);
      Camera.DA.rotateY(-angle);
      Camera.DA.scale(scale);
      Camera.DA.add(Camera.DISTANCE);
      int cpx = (int)(500.0D * Camera.DA.x / -Camera.DA.z);
      int cpy = (int)(500.0D * Camera.DA.y / -Camera.DA.z);
      g.setColor(Color.ORANGE);
      g.fillOval(cpx - 5, cpy - 5, 10, 10);
    } 
  }
}

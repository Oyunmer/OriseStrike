package renderer.rasterizer;

import renderer.math.Vec2;
import renderer.math.Vec4;

public class Vertex implements Comparable<Vertex> {
  public Vec4 p = new Vec4();
  
  public Vec4 normal = new Vec4();
  
  public Vec2 st = new Vec2();
  
  public double[] extraDatas;
  
  public double[] vars;
  
  public Vertex(Vertex v) {
    this(v.extraDatas.length, v.vars.length);
  }
  
  public Vertex(int extraDatasSize, int varsSize) {
    this.extraDatas = new double[extraDatasSize];
    this.vars = new double[varsSize];
  }
  
  public double getValueByPlane(int plane) {
    switch (plane) {
      case 1:
        return this.p.x;
      case 2:
        return this.p.y;
      case 3:
      case 4:
        return this.p.z;
    } 
    return 0.0D;
  }
  
  public void setLerp(Vertex a, Vertex b, double porc) {
    this.p.setLerp(a.p, b.p, porc);
    this.normal.setLerp(a.normal, b.normal, porc);
    this.st.setLerp(a.st, b.st, porc);
    for (int i = 0; i < this.extraDatas.length; i++)
      this.extraDatas[i] = a.extraDatas[i] + porc * (b.extraDatas[i] - a.extraDatas[i]); 
  }
  
  public void multiply(double s) {
    this.p.multiply(s);
    this.normal.multiply(s);
    this.st.multiply(s);
  }
  
  public int compareTo(Vertex t) {
    return (int)(100000.0D * (this.p.y - t.p.y));
  }
}

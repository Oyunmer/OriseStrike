package physics;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import renderer.math.Vec3;

public class MeshLoader {
  private final List<Vec3> vertices = new ArrayList<>();
  
  private final List<Face> faces = new ArrayList<>();
  
  private double scaleFactor;
  
  private double translateX;
  
  private double translateY;
  
  private double translateZ;
  
  private Vec3 min = new Vec3();
  
  private Vec3 max = new Vec3();
  
  public MeshLoader() {
    this.min.set(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
    this.max.set(-1.7976931348623157E308D, -1.7976931348623157E308D, -1.7976931348623157E308D);
  }
  
  public List<Vec3> getVertices() {
    return this.vertices;
  }
  
  public List<Face> getFaces() {
    return this.faces;
  }
  
  public double getScaleFactor() {
    return this.scaleFactor;
  }
  
  public double getTranslateX() {
    return this.translateX;
  }
  
  public double getTranslateY() {
    return this.translateY;
  }
  
  public double getTranslateZ() {
    return this.translateZ;
  }
  
  public Vec3 getMin() {
    return this.min;
  }
  
  public void setMin(Vec3 min) {
    this.min = min;
  }
  
  public Vec3 getMax() {
    return this.max;
  }
  
  public void setMax(Vec3 max) {
    this.max = max;
  }
  
  public void load(String meshRes, double scaleFactor, double translateX, double translateY, double translateZ) throws Exception {
    this.vertices.clear();
    this.faces.clear();
    this.scaleFactor = scaleFactor;
    this.translateX = translateX;
    this.translateY = translateY;
    this.translateZ = translateZ;
    InputStream is = MeshLoader.class.getResourceAsStream(meshRes);
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    String line;
    while ((line = br.readLine()) != null) {
      if (line.startsWith("v ")) {
        parseVertex(line);
        continue;
      } 
      if (line.startsWith("f "))
        parseFace(line); 
    } 
    br.close();
  }
  
  private void parseVertex(String line) {
    String[] data = line.split(" ");
    double x = Double.parseDouble(data[1]);
    double y = Double.parseDouble(data[2]);
    double z = Double.parseDouble(data[3]);
    double stx = x * this.scaleFactor + this.translateX;
    double sty = y * this.scaleFactor + this.translateY;
    double stz = z * this.scaleFactor + this.translateZ;
    Vec3 v = new Vec3(stx, sty, stz);
    this.vertices.add(v);
    updateMinMax(v);
  }
  
  private void parseFace(String line) {
    String[] data = line.split(" ");
    Face face = new Face();
    for (int i = 1; i < data.length; i++) {
      String[] data2 = data[i].split("/");
      int index = Integer.parseInt(data2[0]);
      Vec3 p = this.vertices.get(index - 1);
      face.addPoint(p);
    } 
    face.close();
    this.faces.add(face);
  }
  
  private void updateMinMax(Vec3 p) {
    if (p.x < this.min.x)
      this.min.x = p.x; 
    if (p.y < this.min.y)
      this.min.y = p.y; 
    if (p.z < this.min.z)
      this.min.z = p.z; 
    if (p.x > this.max.x)
      this.max.x = p.x; 
    if (p.y > this.max.y)
      this.max.y = p.y; 
    if (p.z > this.max.z)
      this.max.z = p.z; 
  }
  
  public String toString() {
    return "MeshLoader{vertices=" + this.vertices + ", faces=" + this.faces + ", scaleFactor=" + this.scaleFactor + ", translateX=" + this.translateX + ", translateY=" + this.translateY + '}';
  }
}

package renderer.parser.wavefront;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import renderer.core.Material;
import renderer.math.Vec2;
import renderer.math.Vec4;

public class WavefrontParser {
  public static class Face {
    public Vec4[] vertex;
    
    public Vec4[] normal;
    
    public Vec2[] texture;
    
    public Face(Vec4[] vertex, Vec4[] normal, Vec2[] vt) {
      this.vertex = vertex;
      this.normal = normal;
      this.texture = vt;
    }
  }
  
  public static List<Vec4> vertices = new ArrayList<>();
  
  public static List<Vec4> normals = new ArrayList<>();
  
  public static List<Vec2> textures = new ArrayList<>();
  
  public static Obj obj;
  
  public static List<Obj> objs = new ArrayList<>();
  
  public static List<Obj> load(String resource, double scaleFactor) throws Exception {
    objs.clear();
    vertices.clear();
    normals.clear();
    textures.clear();
    BufferedReader br = new BufferedReader(new InputStreamReader(WavefrontParser.class.getResourceAsStream(resource)));
    String line = null;
    while ((line = br.readLine()) != null) {
      if (line.startsWith("mtllib "))
        extractMaterial(line); 
      if (line.startsWith("usemtl ")) {
        WavefrontParser.obj = new Obj();
        objs.add(WavefrontParser.obj);
        String materialName = line.substring(7);
        Material material = MaterialParser.materials.get(materialName);
        WavefrontParser.obj.material = material;
        continue;
      } 
      if (line.startsWith("v ")) {
        extractVertex(line, vertices, scaleFactor);
        continue;
      } 
      if (line.startsWith("vt ")) {
        extractVertexTexture(line, textures);
        continue;
      } 
      if (line.startsWith("vn ")) {
        extractVertexNormal(line, normals);
        continue;
      } 
      if (line.startsWith("f "))
        extractFace(line, vertices, normals, WavefrontParser.obj); 
    } 
    br.close();
    int facesCount = 0;
    for (Obj obj : objs)
      facesCount += obj.faces.size(); 
    System.out.println("Faces count: " + facesCount);
    return objs;
  }
  
  private static void extractMaterial(String line) throws Exception {
    line = line.substring(7);
    MaterialParser.load(line);
  }
  
  private static void extractVertex(String line, List<Vec4> vertices, double scaleFactor) {
    line = line.substring(2).trim();
    String[] v = line.split("\\ ");
    Vec4 vertex = new Vec4(Double.parseDouble(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2]), 1.0D);
    vertex.multiply(scaleFactor);
    vertices.add(vertex);
  }
  
  private static void extractVertexTexture(String line, List<Vec2> textures) {
    line = line.substring(3).trim();
    String[] v = line.split("\\ ");
    Vec2 texture = new Vec2(Double.parseDouble(v[0]), Double.parseDouble(v[1]));
    textures.add(texture);
  }
  
  private static void extractVertexNormal(String line, List<Vec4> normals) {
    line = line.substring(3).trim();
    String[] v = line.split("\\ ");
    Vec4 normal = new Vec4(Double.parseDouble(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2]), 1.0D);
    normals.add(normal);
  }
  
  private static void extractFace(String line, List<Vec4> vertices, List<Vec4> normals, Obj obj) {
    List<Face> faces = obj.faces;
    line = line.substring(2).trim();
    String[] v = line.split("\\ ");
    String[] i1 = v[0].split("/");
    String[] i2 = v[1].split("/");
    String[] i3 = v[2].split("/");
    String[] i4 = null;
    Vec4 p1 = vertices.get(Integer.parseInt(i1[0]) - 1);
    Vec4 p2 = vertices.get(Integer.parseInt(i2[0]) - 1);
    Vec4 p3 = vertices.get(Integer.parseInt(i3[0]) - 1);
    Vec4 p4 = null;
    Vec4 n1 = new Vec4();
    Vec4 n2 = new Vec4();
    Vec4 n3 = new Vec4();
    Vec4 n4 = new Vec4();
    if (v.length > 3) {
      i4 = v[3].split("/");
      p4 = vertices.get(Integer.parseInt(i4[0]) - 1);
    } 
    if (i1.length > 2) {
      n1 = normals.get(Integer.parseInt(i1[2]) - 1);
      n2 = normals.get(Integer.parseInt(i2[2]) - 1);
      n3 = normals.get(Integer.parseInt(i3[2]) - 1);
    } 
    if (i1.length > 2 && v.length > 3)
      n4 = normals.get(Integer.parseInt(i4[2]) - 1); 
    Vec2 t1 = new Vec2();
    Vec2 t2 = new Vec2();
    Vec2 t3 = new Vec2();
    Vec2 t4 = new Vec2();
    if (!i1[1].trim().isEmpty()) {
      t1 = textures.get(Integer.parseInt(i1[1]) - 1);
      t2 = textures.get(Integer.parseInt(i2[1]) - 1);
      t3 = textures.get(Integer.parseInt(i3[1]) - 1);
      if (v.length > 3)
        t4 = textures.get(Integer.parseInt(i4[1]) - 1); 
    } 
    Face face = new Face(new Vec4[] { p1, p2, p3 }, new Vec4[] { n1, n2, n3 }, new Vec2[] { t1, t2, t3 });
    faces.add(face);
    if (v.length > 3) {
      face = new Face(new Vec4[] { p1, p3, p4 }, new Vec4[] { n1, n3, n4 }, new Vec2[] { t1, t3, t4 });
      faces.add(face);
    } 
  }
  
  public static void main(String[] args) throws Exception {
    load("/res/luigi_circuit.obj", 1.0D);
  }
}

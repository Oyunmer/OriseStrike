package physics.partition;

import java.util.ArrayList;
import java.util.List;
import physics.Face;
import renderer.math.Vec3;

public class Node extends Box {
  protected List<FaceBox> faces = new ArrayList<>();
  
  protected Node[] children = new Node[8];
  
  protected FaceBox boxTmp = new FaceBox();
  
  protected boolean partitioned;
  
  public Node(Vec3 min, Vec3 max) {
    super(min, max);
  }
  
  protected void partition() {
    Vec3 b1Min = new Vec3(this.min.x, this.min.y, this.min.z);
    Vec3 b1Max = new Vec3(this.min.x + this.length.x / 2.0D, this.min.y + this.length.y / 2.0D, this.min.z + this.length.z / 2.0D);
    Vec3 b2Min = new Vec3(this.min.x + this.length.x / 2.0D, this.min.y, this.min.z);
    Vec3 b2Max = new Vec3(this.min.x + this.length.x, this.min.y + this.length.y / 2.0D, this.min.z + this.length.z / 2.0D);
    Vec3 b3Min = new Vec3(this.min.x, this.min.y, this.min.z + this.length.z / 2.0D);
    Vec3 b3Max = new Vec3(this.min.x + this.length.x / 2.0D, this.min.y + this.length.y / 2.0D, this.min.z + this.length.z);
    Vec3 b4Min = new Vec3(this.min.x + this.length.x / 2.0D, this.min.y, this.min.z + this.length.z / 2.0D);
    Vec3 b4Max = new Vec3(this.min.x + this.length.x, this.min.y + this.length.y / 2.0D, this.min.z + this.length.z);
    Vec3 b5Min = new Vec3(this.min.x, this.min.y + this.length.y / 2.0D, this.min.z);
    Vec3 b5Max = new Vec3(this.min.x + this.length.x / 2.0D, this.min.y + this.length.y, this.min.z + this.length.z / 2.0D);
    Vec3 b6Min = new Vec3(this.min.x + this.length.x / 2.0D, this.min.y + this.length.y / 2.0D, this.min.z);
    Vec3 b6Max = new Vec3(this.min.x + this.length.x, this.min.y + this.length.y, this.min.z + this.length.z / 2.0D);
    Vec3 b7Min = new Vec3(this.min.x, this.min.y + this.length.y / 2.0D, this.min.z + this.length.z / 2.0D);
    Vec3 b7Max = new Vec3(this.min.x + this.length.x / 2.0D, this.min.y + this.length.y, this.min.z + this.length.z);
    Vec3 b8Min = new Vec3(this.min.x + this.length.x / 2.0D, this.min.y + this.length.y / 2.0D, this.min.z + this.length.z / 2.0D);
    Vec3 b8Max = new Vec3(this.min.x + this.length.x, this.min.y + this.length.y, this.min.z + this.length.z);
    Vec3[][] minMax = { { b1Min, b1Max }, { b2Min, b2Max }, { b3Min, b3Max }, { b4Min, b4Max }, { b5Min, b5Max }, { b6Min, b6Max }, { b7Min, b7Max }, { b8Min, b8Max } };
    for (int i = 0; i < 8; i++)
      this.children[i] = new Node(minMax[i][0], minMax[i][1]); 
    this.partitioned = true;
  }
  
  public void addFace(Face face) {
    if (!this.partitioned)
      partition(); 
    boolean addFaceInThisNode = true;
    this.boxTmp.setFace(face);
    for (Node node : this.children) {
      if (this.boxTmp.isCompletelyInside(node)) {
        addFaceInThisNode = false;
        node.addFace(face);
        break;
      } 
    } 
    if (addFaceInThisNode)
      this.faces.add(new FaceBox(face)); 
  }
  
  public void retrieveFaces(Box box, List<Face> result) {
    if (this.partitioned)
      for (Node node : this.children) {
        if (node.intersects(box))
          node.retrieveFaces(box, result); 
      }  
    if (intersects(box))
      for (FaceBox faceBox : this.faces) {
        if (faceBox.intersects(box))
          result.add(faceBox.getFace()); 
      }  
  }
}

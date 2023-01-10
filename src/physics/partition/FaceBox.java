package physics.partition;

import physics.Face;
import renderer.math.Vec3;

public class FaceBox extends Box {
  private Face face;
  
  public FaceBox() {}
  
  public FaceBox(Face face) {
    setFaceInternal(face);
  }
  
  public Face getFace() {
    return this.face;
  }
  
  public void setFace(Face face) {
    setFaceInternal(face);
  }
  
  private void setFaceInternal(Face face) {
    this.face = face;
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double minZ = Double.MAX_VALUE;
    double maxX = -1.7976931348623157E308D;
    double maxY = -1.7976931348623157E308D;
    double maxZ = -1.7976931348623157E308D;
    for (Vec3 point : face.getPoints()) {
      if (point.x < minX)
        minX = point.x; 
      if (point.y < minY)
        minY = point.y; 
      if (point.z < minZ)
        minZ = point.z; 
      if (point.x > maxX)
        maxX = point.x; 
      if (point.y > maxY)
        maxY = point.y; 
      if (point.z > maxZ)
        maxZ = point.z; 
    } 
    this.min.set(minX, minY, minZ);
    this.max.set(maxX, maxY, maxZ);
    updateLength();
  }
}

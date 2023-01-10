package renderer.core;

import java.util.ArrayList;
import java.util.List;
import renderer.math.Mat4;

public class Transform {
  public int matrixIndex = -1;
  
  public List<Mat4> matrices = new ArrayList<>();
  
  protected Mat4 tmp;
  
  private void init() {
    push();
  }
  
  public Mat4 getMatrix() {
    return this.matrices.get(this.matrixIndex);
  }
  
  public void push() {
    if (this.matrixIndex + 1 >= this.matrices.size()) {
      Mat4 matrix = new Mat4();
      matrix.setIdentity();
      this.matrices.add(matrix);
    } 
    this.matrixIndex++;
  }
  
  public void pop() {
    this.matrixIndex--;
    if (this.matrixIndex < 0)
      throw new RuntimeException("Matrix stack is empty !"); 
  }
  
  public Transform() {
    this.tmp = new Mat4();
    init();
  }
  
  public void setIdentity() {
    getMatrix().setIdentity();
  }
  
  public void setPerspectiveProjection(double fov, double screenWidth) {
    getMatrix().setPerspectiveProjection(fov, screenWidth);
  }
  
  public void scale(double sx, double sy, double sz) {
    this.tmp.setScale(sx, sy, sz);
    getMatrix().multiply(this.tmp);
  }
  
  public void translate(double dx, double dy, double dz) {
    this.tmp.setTranslation(dx, dy, dz);
    getMatrix().multiply(this.tmp);
  }
  
  public void rotateX(double angle) {
    this.tmp.setRotationX(angle);
    getMatrix().multiply(this.tmp);
  }
  
  public void rotateY(double angle) {
    this.tmp.setRotationY(angle);
    getMatrix().multiply(this.tmp);
  }
  
  public void rotateZ(double angle) {
    this.tmp.setRotationZ(angle);
    getMatrix().multiply(this.tmp);
  }
}

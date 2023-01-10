package renderer.rasterizer;

import renderer.core.Renderer;
import renderer.core.Shader;

public class Scan {
  private Edge e1;
  
  private Edge e2;
  
  private int x1;
  
  private int x2;
  
  private int y1;
  
  private int y2;
  
  private double[] vars;
  
  private double[] dVars;
  
  public void setVars(double[] vars, double[] dVars) {
    this.vars = vars;
    this.dVars = dVars;
  }
  
  public void setTop(Edge e1, Edge e2) {
    this.e1 = e1;
    this.e2 = e2;
    if (e1.dx > e2.dx)
      swapE1E2(); 
    this.y1 = (int)e1.a.p.y;
    this.y2 = (int)e1.b.p.y;
  }
  
  public void setBottom(Edge e1, Edge e2) {
    this.e1 = e1;
    this.e2 = e2;
    if (e1.dx < e2.dx)
      swapE1E2(); 
    this.y1 = (int)e1.a.p.y;
    this.y2 = (int)e1.b.p.y;
  }
  
  public void drawTop(Renderer renderer) {
    Shader shader = renderer.getShader();
    for (int y = this.y1; y <= this.y2; y++) {
      initX();
      for (int x = this.x1; x <= this.x2; x++) {
        shader.processPixel(renderer, this.x1, this.x2, x, y, this.vars);
        nextX();
      } 
      this.e1.nextYTop();
      this.e2.nextYTop();
    } 
  }
  
  public void drawBottom(Renderer renderer) {
    Shader shader = renderer.getShader();
    for (int y = this.y1; y > this.y2; y--) {
      initX();
      for (int x = this.x1; x <= this.x2; x++) {
        shader.processPixel(renderer, this.x1, this.x2, x, y, this.vars);
        nextX();
      } 
      this.e1.nextYBottom();
      this.e2.nextYBottom();
    } 
  }
  
  private void swapE1E2() {
    Edge tmp = this.e1;
    this.e1 = this.e2;
    this.e2 = tmp;
  }
  
  private void initX() {
    this.x1 = (int)this.e1.x;
    this.x2 = (int)this.e2.x;
    int dx = this.x2 - this.x1;
    System.arraycopy(this.e1.vars, 0, this.vars, 0, this.vars.length);
    double dxInv = 1.0D / dx;
    for (int k = 0; k < this.vars.length; k++)
      this.dVars[k] = (this.e2.vars[k] - this.e1.vars[k]) * dxInv; 
  }
  
  private void nextX() {
    for (int k = 0; k < this.vars.length; k++)
      this.vars[k] = this.vars[k] + this.dVars[k]; 
  }
}

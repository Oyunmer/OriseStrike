package renderer.rasterizer;

import java.util.Arrays;
import renderer.core.Renderer;
import renderer.core.Shader;

public class TriangleRasterizer {
  private Edge e1 = new Edge();
  
  private Edge e2 = new Edge();
  
  private Scan scan = new Scan();
  
  private Vertex[] vertices = new Vertex[3];
  
  public void draw(Renderer renderer, Vertex v1, Vertex v2, Vertex v3) {
    this.vertices[0] = v1;
    this.vertices[1] = v2;
    this.vertices[2] = v3;
    Arrays.sort((Object[])this.vertices);
    Shader shader = renderer.getShader();
    this.scan.setVars(shader.varsScan, shader.dVarsScan);
    drawTop(renderer);
    drawBottom(renderer);
  }
  
  private void drawTop(Renderer renderer) {
    Shader shader = renderer.getShader();
    this.e1.set(this.vertices[0], this.vertices[1], shader.varsE1, shader.dVarsE1);
    this.e2.set(this.vertices[0], this.vertices[2], shader.varsE2, shader.dVarsE2);
    this.scan.setTop(this.e1, this.e2);
    this.scan.drawTop(renderer);
  }
  
  private void drawBottom(Renderer renderer) {
    Shader shader = renderer.getShader();
    this.e1.set(this.vertices[2], this.vertices[1], shader.varsE1, shader.dVarsE1);
    this.e2.set(this.vertices[2], this.vertices[0], shader.varsE2, shader.dVarsE2);
    this.scan.setBottom(this.e1, this.e2);
    this.scan.drawBottom(renderer);
  }
}

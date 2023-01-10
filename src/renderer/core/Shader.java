package renderer.core;

import java.util.ArrayList;
import java.util.List;
import renderer.rasterizer.Vertex;

public class Shader {
  public Vertex[] vertices;
  
  public double[] uniforms;
  
  public double[] dVarsE1;
  
  public double[] varsE1;
  
  public double[] dVarsE2;
  
  public double[] varsE2;
  
  public double[] dVarsScan;
  
  public double[] varsScan;
  
  public int[] color = new int[] { 255, 255, 255, 255 };
  
  private int vertexExtraDatasSize;
  
  private int vertexVarsSize;
  
  private int vertexCacheIndex;
  
  private List<Vertex> vertexCache;
  
  public void preDraw(Renderer renderer, Vertex v1, Vertex v2, Vertex v3) {}
  
  public void postDraw(Renderer renderer, Vertex v1, Vertex v2, Vertex v3) {}
  
  public void processVertex(Renderer renderer, Vertex v) {}
  
  public void processPixel(Renderer renderer, int xMin, int xMax, int x, int y, double[] vars) {}
  
  public Shader(int uniformsSize, int vertexExtraDatasSize, int vertexVarsSize) {
    this.vertexCacheIndex = 0;
    this.vertexCache = new ArrayList<>();
    this.vertexExtraDatasSize = vertexExtraDatasSize;
    this.vertexVarsSize = vertexVarsSize;
    this.vertices = new Vertex[3];
    for (int i = 0; i < 3; i++)
      this.vertices[i] = new Vertex(vertexExtraDatasSize, vertexVarsSize); 
    this.uniforms = new double[uniformsSize];
    this.dVarsE1 = new double[vertexVarsSize];
    this.varsE1 = new double[vertexVarsSize];
    this.dVarsE2 = new double[vertexVarsSize];
    this.varsE2 = new double[vertexVarsSize];
    this.dVarsScan = new double[vertexVarsSize];
    this.varsScan = new double[vertexVarsSize];
  }
  
  public void initVertexCache() {
    this.vertexCacheIndex = 0;
  }
  
  public Vertex getVertexFromCache() {
    Vertex cachedVertex = null;
    if (this.vertexCacheIndex > this.vertexCache.size() - 1) {
      cachedVertex = new Vertex(this.vertexExtraDatasSize, this.vertexVarsSize);
      this.vertexCache.add(cachedVertex);
      this.vertexCacheIndex++;
    } else {
      cachedVertex = this.vertexCache.get(this.vertexCacheIndex++);
    } 
    return cachedVertex;
  }
}

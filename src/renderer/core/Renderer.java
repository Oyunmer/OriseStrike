package renderer.core;

import java.util.ArrayList;
import java.util.List;
import renderer.buffer.ColorBuffer;
import renderer.buffer.DepthBuffer;
import renderer.math.Mat4;
import renderer.math.Vec4;
import renderer.rasterizer.TriangleRasterizer;
import renderer.rasterizer.Vertex;

public class Renderer {
  private ColorBuffer colorBuffer;
  
  private DepthBuffer depthBuffer;
  
  private boolean depthBufferEnabled = true;
  
  private Shader shader;
  
  private boolean backfaceCullingEnabled = true;
  
  private int vertexIndex = 0;
  
  private TriangleRasterizer triangleRasterizer = new TriangleRasterizer();
  
  private List<Image> textures = new ArrayList<>();
  
  private Material material;
  
  public enum MatrixMode {
    MODEL, VIEW, PROJECTION;
  }
  
  private Mat4 mvp = new Mat4();
  
  private Transform modelTransform = new Transform();
  
  private Transform viewTranform = new Transform();
  
  private Transform projectionTranform = new Transform();
  
  private Transform currentTransform;
  
  private MatrixMode matrixMode;
  
  private List<Light> lights = new ArrayList<>();
  
  private ViewFrustrumClipper polygonClipper = new ViewFrustrumClipper();
  
  private double clipX;
  
  private double clipY;
  
  private double clipZfar = -1.7976931348623157E308D;
  
  private double clipZNear = -1.0D;
  
  public Renderer(int width, int height) {
    this.colorBuffer = new ColorBuffer(width, height);
    this.depthBuffer = new DepthBuffer(width, height);
    this.currentTransform = this.projectionTranform;
    this.matrixMode = MatrixMode.PROJECTION;
    this.clipX = (width / 2) - 0.5D;
    this.clipY = (height / 2) - 0.5D;
  }
  
  public ColorBuffer getColorBuffer() {
    return this.colorBuffer;
  }
  
  public void setColorBuffer(ColorBuffer colorBuffer) {
    this.colorBuffer = colorBuffer;
  }
  
  public DepthBuffer getDepthBuffer() {
    return this.depthBuffer;
  }
  
  public void setDepthBuffer(DepthBuffer depthBuffer) {
    this.depthBuffer = depthBuffer;
  }
  
  public boolean isDepthBufferEnabled() {
    return this.depthBufferEnabled;
  }
  
  public void setDepthBufferEnabled(boolean depthBufferEnabled) {
    this.depthBufferEnabled = depthBufferEnabled;
  }
  
  public boolean isBackfaceCullingEnabled() {
    return this.backfaceCullingEnabled;
  }
  
  public void setBackfaceCullingEnabled(boolean backfaceCullingEnabled) {
    this.backfaceCullingEnabled = backfaceCullingEnabled;
  }
  
  public boolean isBackFaced(Vertex v1, Vertex v2, Vertex v3) {
    double x1 = v2.p.x - v1.p.x;
    double y1 = v2.p.y - v1.p.y;
    double x2 = v3.p.x - v1.p.x;
    double y2 = v3.p.y - v1.p.y;
    double z = x1 * y2 - x2 * y1;
    return (z < 0.0D);
  }
  
  public double getClipX() {
    return this.clipX;
  }
  
  public void setClipX(double clipX) {
    this.clipX = clipX;
  }
  
  public double getClipY() {
    return this.clipY;
  }
  
  public void setClipY(double clipY) {
    this.clipY = clipY;
  }
  
  public double getClipZfar() {
    return this.clipZfar;
  }
  
  public void setClipZfar(double clipZfar) {
    this.clipZfar = clipZfar;
  }
  
  public double getClipZNear() {
    return this.clipZNear;
  }
  
  public void setClipZNear(double clipZNear) {
    this.clipZNear = clipZNear;
  }
  
  public Shader getShader() {
    return this.shader;
  }
  
  public void setShader(Shader shader) {
    this.shader = shader;
  }
  
  public Material getMaterial() {
    return this.material;
  }
  
  public void setMaterial(Material material) {
    this.material = material;
  }
  
  public List<Image> getTextures() {
    return this.textures;
  }
  
  public void addTexture(Image texture) {
    this.textures.add(texture);
  }
  
  public List<Light> getLights() {
    return this.lights;
  }
  
  public void addLight(Light light) {
    this.lights.add(light);
  }
  
  public Mat4 getMvp() {
    return this.mvp;
  }
  
  public MatrixMode getMatrixMode() {
    return this.matrixMode;
  }
  
  public void setMatrixMode(MatrixMode matrixMode) {
    this.matrixMode = matrixMode;
    switch (matrixMode) {
      case MODEL:
        this.currentTransform = this.modelTransform;
        break;
      case VIEW:
        this.currentTransform = this.viewTranform;
        break;
      case PROJECTION:
        this.currentTransform = this.projectionTranform;
        break;
    } 
  }
  
  public void pushMatrix() {
    this.currentTransform.push();
  }
  
  public void popMatrix() {
    this.currentTransform.pop();
  }
  
  public Transform getCurrentTranform() {
    return this.currentTransform;
  }
  
  public void setIdentity() {
    this.currentTransform.setIdentity();
  }
  
  public void setPerspectiveProjection(double fov) {
    this.currentTransform.setPerspectiveProjection(fov, this.colorBuffer.getWidth());
  }
  
  public void scale(double sx, double sy, double sz) {
    this.currentTransform.scale(sx, sy, sz);
  }
  
  public void translate(double dx, double dy, double dz) {
    this.currentTransform.translate(dx, dy, dz);
  }
  
  public void rotateX(double angle) {
    this.currentTransform.rotateX(angle);
  }
  
  public void rotateY(double angle) {
    this.currentTransform.rotateY(angle);
  }
  
  public void rotateZ(double angle) {
    this.currentTransform.rotateZ(angle);
  }
  
  public void clearAllBuffers() {
    this.depthBuffer.clear();
    this.colorBuffer.clear();
  }
  
  public void begin() {
    this.vertexIndex = 0;
    this.mvp.set(this.projectionTranform.getMatrix());
    this.mvp.multiply(this.viewTranform.getMatrix());
    this.mvp.multiply(this.modelTransform.getMatrix());
  }
  
  public void setUniforms(double[] uniforms) {
    System.arraycopy(uniforms, 0, this.shader.uniforms, 0, uniforms.length);
  }
  
  public void setVertexExtraDatas(double[] vertexExtraDatas) {
    System.arraycopy(vertexExtraDatas, 0, (this.shader.vertices[this.vertexIndex]).extraDatas, 0, vertexExtraDatas.length);
  }
  
  public void setTextureCoordinates(double s, double t) {
    (this.shader.vertices[this.vertexIndex]).st.set(s, t);
  }
  
  public void setNormal(double x, double y, double z) {
    (this.shader.vertices[this.vertexIndex]).normal.set(x, y, z, 0.0D);
  }
  
  public void setVertex(double x, double y, double z) {
    Vec4 p = (this.shader.vertices[this.vertexIndex]).p;
    p.set(x, y, z, 1.0D);
    this.vertexIndex++;
    if (this.vertexIndex % 3 == 0) {
      render();
      this.vertexIndex = 0;
    } 
  }
  
  private void render() {
    for (int i = 0; i < this.shader.vertices.length; i++)
      doVertexMVPTransformation(this.shader.vertices[i]); 
    Vertex v1 = this.shader.vertices[0];
    Vertex v2 = this.shader.vertices[1];
    Vertex v3 = this.shader.vertices[2];
    if (v1.p.z > -10.0D && v2.p.z > -10.0D && v3.p.z > -10.0D)
      return; 
    List<Vertex> clippedVertices = this.polygonClipper.clip(this.shader, this.shader.vertices, this.clipX, this.clipY, this.clipZNear, this.clipZfar);
    if (clippedVertices.isEmpty())
      return; 
    for (Vertex v : clippedVertices) {
      this.shader.processVertex(this, v);
      v.p.doPerspectiveDivision();
    } 
    drawPolygon(clippedVertices);
  }
  
  private void drawPolygon(List<Vertex> vertices) {
    Vertex va = vertices.get(0);
    for (int i = 1; i <= vertices.size() - 2; i++) {
      Vertex vb = vertices.get(i);
      Vertex vc = vertices.get((i + 1) % vertices.size());
      if (this.backfaceCullingEnabled) {
        if (!isBackFaced(va, vb, vc)) {
          this.shader.preDraw(this, va, vb, vc);
          this.triangleRasterizer.draw(this, va, vb, vc);
          this.shader.postDraw(this, va, vb, vc);
        } 
      } else {
        this.shader.preDraw(this, va, vb, vc);
        this.triangleRasterizer.draw(this, va, vb, vc);
        this.shader.postDraw(this, va, vb, vc);
      } 
    } 
  }
  
  public void end() {}
  
  public void doVertexMVPTransformation(Vertex vertex) {
    this.mvp.multiply(vertex.p);
    this.mvp.multiply(vertex.normal);
  }
  
  public void setPixel(int x, int y, int[] color) {
    this.colorBuffer.setPixel(x, y, color);
  }
  
  public void setPixel(int x, int y, int[] color, double depth) {
    if (this.depthBufferEnabled) {
      if (depth < this.depthBuffer.get(x, y)) {
        this.depthBuffer.set(x, y, depth);
        setPixel(x, y, color);
      } 
    } else {
      setPixel(x, y, color);
    } 
  }
}

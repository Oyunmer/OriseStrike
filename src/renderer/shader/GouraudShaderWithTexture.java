package renderer.shader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import renderer.core.Image;
import renderer.core.Light;
import renderer.core.Material;
import renderer.core.Renderer;
import renderer.core.Shader;
import renderer.math.Util;
import renderer.math.Vec4;
import renderer.rasterizer.Vertex;

public class GouraudShaderWithTexture extends Shader {
  private Vec4 vertexLightDirection;
  
  private Polygon polyTmp;
  
  public GouraudShaderWithTexture() {
    super(0, 0, 6);
    this.vertexLightDirection = new Vec4();
    this.polyTmp = new Polygon();
  }
  
  public void processVertex(Renderer renderer, Vertex vertex) {
    double zInv = 1.0D / vertex.p.z;
    vertex.vars[0] = zInv;
    vertex.vars[1] = vertex.st.x * zInv;
    vertex.vars[2] = vertex.st.y * zInv;
    Light light = renderer.getLights().get(0);
    this.vertexLightDirection.set(light.position);
    renderer.getMvp().multiply(this.vertexLightDirection);
    this.vertexLightDirection.sub(vertex.p);
    double p = vertex.normal.getRelativeCosBetween(this.vertexLightDirection);
    if (p < 0.75D) {
      p = 0.75D;
    } else if (p > 1.0D) {
      p = 1.0D;
    } 
    vertex.vars[3] = p * light.diffuse.x;
    vertex.vars[4] = p * light.diffuse.y;
    vertex.vars[5] = p * light.diffuse.z;
  }
  
  public void processPixel(Renderer renderer, int xMin, int xMax, int x, int y, double[] vars) {
    double depth = vars[0];
    double z = 1.0D / depth;
    double s = vars[1] * z;
    double t = vars[2] * z;
    s = (s > 1.0D) ? (s - (int)s) : ((s < 0.0D) ? ((int)s - s) : Util.clamp(s, 0.0D, 1.0D));
    t = (t > 1.0D) ? (t - (int)t) : ((t < 0.0D) ? ((int)t - t) : Util.clamp(t, 0.0D, 1.0D));
    double colorp1 = vars[3];
    double colorp2 = vars[4];
    double colorp3 = vars[5];
    Material material = renderer.getMaterial();
    Image texture = null;
    if (material != null)
      texture = (renderer.getMaterial()).map_kd; 
    if (texture != null) {
      int tx = (int)(s * (texture.getWidth() - 1));
      int textureHeight = texture.getHeight() - 1;
      int ty = textureHeight - (int)(t * textureHeight);
      texture.getPixel(tx, ty, this.color);
    } else {
      this.color[0] = 0;
      this.color[1] = 0;
      this.color[2] = 0;
      this.color[3] = 0;
    } 
    this.color[0] = 255;
    this.color[1] = (int)(this.color[1] * colorp1);
    this.color[2] = (int)(this.color[2] * colorp2);
    this.color[3] = (int)(this.color[3] * colorp3);
    renderer.setPixel(x, y, this.color, depth);
  }
  
  public void postDraw2(Renderer renderer, Vertex va, Vertex vb, Vertex vc) {
    this.polyTmp.reset();
    this.polyTmp.addPoint((int)va.p.x + 200, 150 - (int)va.p.y);
    this.polyTmp.addPoint((int)vb.p.x + 200, 150 - (int)vb.p.y);
    this.polyTmp.addPoint((int)vc.p.x + 200, 150 - (int)vc.p.y);
    Graphics g = renderer.getColorBuffer().getColorBuffer().getGraphics();
    g.setColor(Color.WHITE);
    g.drawPolygon(this.polyTmp);
  }
}

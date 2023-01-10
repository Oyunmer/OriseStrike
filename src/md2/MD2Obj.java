package md2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MD2Obj {
  private double scaleFactor;
  
  private DataStream dataStream;
  
  private Header header;
  
  private String[] skinNames;
  
  private TextureCoordinates[] textureCoordinates;
  
  private Triangle[] triangles;
  
  private Frame[] frames;
  
  private String currentAnimation;
  
  private int startFrame;
  
  private int endFrame;
  
  private int currentFrame;
  
  private double tweening;
  
  private Map<String, AnimationFramesInfo> animationFramesInfoMap = new HashMap<>();
  
  private double[] vtmp;
  
  private double[] vtmp2;
  
  private class AnimationFramesInfo {
    public String name;
    
    public int startFrame;
    
    public int endFrame;
    
    private AnimationFramesInfo() {}
  }
  
  private void fetchAllSkinNames() {
    this.skinNames = new String[this.header.getNum_skins()];
    this.dataStream.setPosition(this.header.getOfs_skins());
    for (int s = 0; s < this.skinNames.length; s++)
      this.skinNames[s] = this.dataStream.getString(64); 
  }
  
  private void fetchAllTextureCoordinates() {
    this.textureCoordinates = new TextureCoordinates[this.header.getNum_st()];
    for (int t = 0; t < this.textureCoordinates.length; t++)
      this.textureCoordinates[t] = new TextureCoordinates(this.header, t); 
  }
  
  private void fetchAllTriangles() {
    this.triangles = new Triangle[this.header.getNum_tris()];
    for (int t = 0; t < this.triangles.length; t++)
      this.triangles[t] = new Triangle(this.header, t); 
  }
  
  private void fetchAllFrames() {
    AnimationFramesInfo animationFramesInfo = null;
    this.frames = new Frame[this.header.getNum_frames()];
    for (int f = 0; f < this.frames.length; f++) {
      this.frames[f] = new Frame(this.header, f);
      if (animationFramesInfo == null) {
        animationFramesInfo = new AnimationFramesInfo();
        animationFramesInfo.name = this.frames[f].getBaseName();
      } else if (!this.frames[f].getBaseName().equals(animationFramesInfo.name) || f == this.frames.length - 1) {
        animationFramesInfo.endFrame = (f == this.frames.length - 1) ? f : (f - 1);
        this.animationFramesInfoMap.put(animationFramesInfo.name, animationFramesInfo);
        animationFramesInfo = new AnimationFramesInfo();
        animationFramesInfo.name = this.frames[f].getBaseName();
        animationFramesInfo.startFrame = f;
      } 
    } 
  }
  
  public void createAnimation(String name, int startFrame, int endFrame) {
    AnimationFramesInfo animationFramesInfo = new AnimationFramesInfo();
    animationFramesInfo.name = name;
    animationFramesInfo.startFrame = startFrame;
    animationFramesInfo.endFrame = endFrame;
    this.animationFramesInfoMap.put(name, animationFramesInfo);
  }
  
  public DataStream getDataStream() {
    return this.dataStream;
  }
  
  public Header getHeader() {
    return this.header;
  }
  
  public String[] getSkinNames() {
    return this.skinNames;
  }
  
  public Triangle[] getTriangles() {
    return this.triangles;
  }
  
  public Frame[] getFrames() {
    return this.frames;
  }
  
  public Set<String> getAnimationNames() {
    return this.animationFramesInfoMap.keySet();
  }
  
  public int getStartFrame() {
    return this.startFrame;
  }
  
  public void setStartFrame(int startFrame) {
    this.startFrame = startFrame;
  }
  
  public int getEndFrame() {
    return this.endFrame;
  }
  
  public void setEndFrame(int endFrame) {
    this.endFrame = endFrame;
  }
  
  public int getCurrentFrame() {
    return this.currentFrame;
  }
  
  public void setCurrentFrame(int currentFrame) {
    this.currentFrame = currentFrame;
  }
  
  public void nextFrame() {
    this.currentFrame++;
    if (this.currentFrame > this.endFrame)
      this.currentFrame = this.startFrame; 
  }
  
  public void nextFrame(double t) {
    this.tweening += t;
    if (this.tweening >= 1.0D) {
      nextFrame();
      this.tweening = 0.0D;
    } 
  }
  
  public void setAnimation(String name) {
    AnimationFramesInfo animationFramesInfo = this.animationFramesInfoMap.get(name);
    if (animationFramesInfo == null)
      throw new RuntimeException("Invalid animation name !"); 
    this.currentAnimation = animationFramesInfo.name;
    this.startFrame = animationFramesInfo.startFrame;
    this.endFrame = animationFramesInfo.endFrame;
    this.currentFrame = this.startFrame;
  }
  
  public String getCurrentAnimation() {
    return this.currentAnimation;
  }
  
  private Frame getNextFrameObj() {
    int cf = this.currentFrame + 1;
    if (cf > this.endFrame)
      cf = this.startFrame; 
    return this.frames[cf];
  }
  
  public MD2Obj(String resource, double scaleFactor) throws Exception {
    this.vtmp = new double[8];
    this.vtmp2 = new double[8];
    this.scaleFactor = scaleFactor;
    this.dataStream = new DataStream(resource);
    this.header = new Header(this.dataStream);
    fetchAllSkinNames();
    fetchAllTextureCoordinates();
    fetchAllTriangles();
    fetchAllFrames();
    this.startFrame = 0;
    this.endFrame = this.header.getNum_frames() - 1;
    this.dataStream.dispose();
  }
  
  public double[] getTriangleVertex(int triangleIndex, int p) {
    Triangle triangle = this.triangles[triangleIndex];
    int vertexIndex = triangle.index_xyz[p];
    int stIndex = triangle.index_st[p];
    TextureCoordinates st = this.textureCoordinates[stIndex];
    setVertexInfo(vertexIndex, this.vtmp, this.frames[this.currentFrame], st);
    if (this.tweening > 0.0D) {
      setVertexInfo(vertexIndex, this.vtmp2, getNextFrameObj(), st);
      for (int l = 0; l < 6; l++)
        this.vtmp[l] = lerp(this.vtmp[l], this.vtmp2[l], this.tweening); 
    } 
    return this.vtmp;
  }
  
  private void setVertexInfo(int vertexIndex, double[] v, Frame frame, TextureCoordinates st) {
    v[0] = (frame.scale[0] * frame.vertices[vertexIndex][0] + frame.translate[0]) * this.scaleFactor;
    v[1] = (frame.scale[1] * frame.vertices[vertexIndex][1] + frame.translate[1]) * this.scaleFactor;
    v[2] = (frame.scale[2] * frame.vertices[vertexIndex][2] + frame.translate[2]) * this.scaleFactor;
    int normalIndex = frame.vertices[vertexIndex][3];
    double[] normal = NormalTable.MD2_NORMAL_TABLE[normalIndex];
    v[3] = normal[0];
    v[4] = normal[1];
    v[5] = normal[2];
    v[6] = st.getS();
    v[7] = st.getT();
  }
  
  private double lerp(double start, double end, double p) {
    return start + (end - start) * p;
  }
}

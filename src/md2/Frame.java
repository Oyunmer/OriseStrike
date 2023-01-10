package md2;

public class Frame {
  public double[] scale;
  
  public double[] translate;
  
  public String name;
  
  public int[][] vertices;
  
  public Frame(Header h, int index) {
    DataStream ds = h.getDataStream();
    ds.setPosition(h.getOfs_frames() + h.getFramesize() * index);
    this.scale = new double[3];
    this.scale[0] = ds.getNextFloat();
    this.scale[1] = ds.getNextFloat();
    this.scale[2] = ds.getNextFloat();
    this.translate = new double[3];
    this.translate[0] = ds.getNextFloat();
    this.translate[1] = ds.getNextFloat();
    this.translate[2] = ds.getNextFloat();
    this.name = ds.getString(16);
    this.vertices = new int[h.getNum_xyz()][4];
    for (int i = 0; i < h.getNum_xyz(); i++) {
      this.vertices[i][0] = ds.getNextByte();
      this.vertices[i][1] = ds.getNextByte();
      this.vertices[i][2] = ds.getNextByte();
      this.vertices[i][3] = ds.getNextByte();
    } 
  }
  
  public double[] getScale() {
    return this.scale;
  }
  
  public double[] getTranslate() {
    return this.translate;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getBaseName() {
    String baseName = "";
    for (int c = 0; c < this.name.length() && 
      Character.isLetter(this.name.charAt(c)); c++)
      baseName = baseName + this.name.charAt(c); 
    return baseName;
  }
  
  public int[][] getVertices() {
    return this.vertices;
  }
  
  public String toString() {
    return "Frame{scale=" + this.scale + ", translate=" + this.translate + ", name=" + this.name + ", vertices=" + this.vertices + '}';
  }
}

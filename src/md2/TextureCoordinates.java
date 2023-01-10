package md2;

public class TextureCoordinates {
  public double s;
  
  public double t;
  
  public TextureCoordinates(Header header, int index) {
    DataStream ds = header.getDataStream();
    ds.setPosition(header.getOfs_st() + 4 * index);
    this.s = ds.getNextShort() / header.getSkinwidth();
    this.t = ds.getNextShort() / header.getSkinheight();
  }
  
  public double getS() {
    return this.s;
  }
  
  public double getT() {
    return this.t;
  }
}

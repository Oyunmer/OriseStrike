package md2;

public class Triangle {
  public int[] index_xyz;
  
  public int[] index_st;
  
  public Triangle(Header h, int i) {
    DataStream ds = h.getDataStream();
    ds.setPosition(h.getOfs_tris() + 12 * i);
    this.index_xyz = new int[3];
    this.index_xyz[0] = ds.getNextShort();
    this.index_xyz[1] = ds.getNextShort();
    this.index_xyz[2] = ds.getNextShort();
    this.index_st = new int[3];
    this.index_st[0] = ds.getNextShort();
    this.index_st[1] = ds.getNextShort();
    this.index_st[2] = ds.getNextShort();
  }
  
  public String toString() {
    return "Triangle{index_xyz=" + this.index_xyz + ", index_st=" + this.index_st + '}';
  }
}

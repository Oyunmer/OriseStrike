package md2;

public class Header {
  private int ident;
  
  private int version;
  
  private int skinwidth;
  
  private int skinheight;
  
  private int framesize;
  
  private int num_skins;
  
  private int num_xyz;
  
  private int num_st;
  
  private int num_tris;
  
  private int num_glcmds;
  
  private int num_frames;
  
  private int ofs_skins;
  
  private int ofs_st;
  
  private int ofs_tris;
  
  private int ofs_frames;
  
  private int ofs_glcmds;
  
  private int ofs_end;
  
  private DataStream dataStream;
  
  public Header(DataStream dataStream) {
    this.dataStream = dataStream;
    dataStream.setPosition(0);
    this.ident = dataStream.getNextInt();
    this.version = dataStream.getNextInt();
    this.skinwidth = dataStream.getNextInt();
    this.skinheight = dataStream.getNextInt();
    this.framesize = dataStream.getNextInt();
    this.num_skins = dataStream.getNextInt();
    this.num_xyz = dataStream.getNextInt();
    this.num_st = dataStream.getNextInt();
    this.num_tris = dataStream.getNextInt();
    this.num_glcmds = dataStream.getNextInt();
    this.num_frames = dataStream.getNextInt();
    this.ofs_skins = dataStream.getNextInt();
    this.ofs_st = dataStream.getNextInt();
    this.ofs_tris = dataStream.getNextInt();
    this.ofs_frames = dataStream.getNextInt();
    this.ofs_glcmds = dataStream.getNextInt();
    this.ofs_end = dataStream.getNextInt();
  }
  
  public int getIdent() {
    return this.ident;
  }
  
  public int getVersion() {
    return this.version;
  }
  
  public int getSkinwidth() {
    return this.skinwidth;
  }
  
  public int getSkinheight() {
    return this.skinheight;
  }
  
  public int getFramesize() {
    return this.framesize;
  }
  
  public int getNum_skins() {
    return this.num_skins;
  }
  
  public int getNum_xyz() {
    return this.num_xyz;
  }
  
  public int getNum_st() {
    return this.num_st;
  }
  
  public int getNum_tris() {
    return this.num_tris;
  }
  
  public int getNum_glcmds() {
    return this.num_glcmds;
  }
  
  public int getNum_frames() {
    return this.num_frames;
  }
  
  public int getOfs_skins() {
    return this.ofs_skins;
  }
  
  public int getOfs_st() {
    return this.ofs_st;
  }
  
  public int getOfs_tris() {
    return this.ofs_tris;
  }
  
  public int getOfs_frames() {
    return this.ofs_frames;
  }
  
  public int getOfs_glcmds() {
    return this.ofs_glcmds;
  }
  
  public int getOfs_end() {
    return this.ofs_end;
  }
  
  public DataStream getDataStream() {
    return this.dataStream;
  }
  
  public String toString() {
    return "Header{ident=" + this.ident + ", version=" + this.version + ", skinwidth=" + this.skinwidth + ", skinheight=" + this.skinheight + ", framesize=" + this.framesize + ", num_skins=" + this.num_skins + ", num_xyz=" + this.num_xyz + ", num_st=" + this.num_st + ", num_tris=" + this.num_tris + ", num_glcmds=" + this.num_glcmds + ", num_frames=" + this.num_frames + ", ofs_skins=" + this.ofs_skins + ", ofs_st=" + this.ofs_st + ", ofs_tris=" + this.ofs_tris + ", ofs_frames=" + this.ofs_frames + ", ofs_glcmds=" + this.ofs_glcmds + ", ofs_end=" + this.ofs_end + '}';
  }
}

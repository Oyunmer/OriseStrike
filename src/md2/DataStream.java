package md2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataStream {
  private byte[] data;
  
  private int position;
  
  public DataStream(String resource) throws Exception {
    InputStream is = getClass().getResourceAsStream(resource);
    List<Byte> dataTmp = new ArrayList<>();
    int c;
    while ((c = is.read()) >= 0)
      dataTmp.add(Byte.valueOf((byte)c)); 
    is.close();
    this.data = new byte[dataTmp.size()];
    for (int i = 0; i < dataTmp.size(); i++)
      this.data[i] = ((Byte)dataTmp.get(i)).byteValue(); 
  }
  
  public int getPosition() {
    return this.position;
  }
  
  public void setPosition(int position) {
    this.position = position;
  }
  
  private int getNextData() {
    return this.data[this.position++] & 0xFF;
  }
  
  public int getNextByte() {
    return getNextData();
  }
  
  public int getNextShort() {
    int a = getNextData();
    int b = getNextData();
    int r = a + (b << 8);
    return r;
  }
  
  public int getNextInt() {
    int a = getNextData();
    int b = getNextData();
    int c = getNextData();
    int d = getNextData();
    int r = a + (b << 8) + (c << 16) + (d << 24);
    return r;
  }
  
  public float getNextFloat() {
    return Float.intBitsToFloat(getNextInt());
  }
  
  public String getString(int length) {
    String r = "";
    while (length > 0) {
      if (this.data[this.position] > 0)
        r = r + (char)this.data[this.position]; 
      this.position++;
      length--;
    } 
    return r;
  }
  
  public void dispose() {
    this.data = null;
  }
}

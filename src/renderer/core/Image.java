package renderer.core;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Image {
  private BufferedImage bi;
  
  private WritableRaster raster;
  
  private int[] data;
  
  private int width;
  
  private int height;
  
  public Image(String resource) {
    try {
      InputStream is = getClass().getResourceAsStream(resource);
      if (is == null)
        throw new RuntimeException("Could not load resource " + resource + " !"); 
      BufferedImage biProv = ImageIO.read(is);
      this.bi = new BufferedImage(biProv.getWidth(), biProv.getHeight(), 2);
      this.bi.getGraphics().drawImage(biProv, 0, 0, null);
    } catch (IOException ex) {
      System.err.println("Could not load resource " + resource + " !");
      Logger.getLogger(Image.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    this.width = this.bi.getWidth();
    this.height = this.bi.getHeight();
    this.raster = this.bi.getRaster();
    this.data = ((DataBufferInt)this.raster.getDataBuffer()).getData();
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public BufferedImage getColorBuffer() {
    return this.bi;
  }
  
  public void setPixel(int x, int y, int[] c) {
    this.data[x + y * this.width] = c[3] + (c[2] << 8) + (c[1] << 16) + (c[0] << 24);
  }
  
  public int getPixel(int x, int y) {
    return this.data[x + y * this.width];
  }
  
  public void getPixel(int tx, int ty, int[] color) {
    int c = this.data[tx + ty * this.width];
    color[0] = (c & 0xFF000000) >> 24;
    color[1] = (c & 0xFF0000) >> 16;
    color[2] = (c & 0xFF00) >> 8;
    color[3] = c & 0xFF;
  }
}

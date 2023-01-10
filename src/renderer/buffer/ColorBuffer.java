package renderer.buffer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

public class ColorBuffer {
  private BufferedImage colorBuffer;
  
  private Graphics2D g2d;
  
  private WritableRaster raster;
  
  private int[] data;
  
  private int width;
  
  private int height;
  
  private int halfWidth;
  
  private int halfHeight;
  
  public ColorBuffer(int width, int height) {
    this.width = width;
    this.height = height;
    this.halfWidth = width / 2;
    this.halfHeight = height / 2;
    this.colorBuffer = new BufferedImage(width, height, 1);
    this.g2d = (Graphics2D)this.colorBuffer.getGraphics();
    this.raster = this.colorBuffer.getRaster();
    this.data = ((DataBufferInt)this.raster.getDataBuffer()).getData();
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public BufferedImage getColorBuffer() {
    return this.colorBuffer;
  }
  
  public void setBackgroundColor(Color color) {
    this.g2d.setBackground(color);
  }
  
  public void clear() {
    this.g2d.clearRect(0, 0, this.colorBuffer.getWidth(), this.colorBuffer.getHeight());
  }
  
  public void setPixel(int x, int y, int[] c) {
    x += this.halfWidth;
    y = this.halfHeight - y;
    this.data[x + y * this.width] = c[3] + (c[2] << 8) + (c[1] << 16) + (c[0] << 24);
  }
  
  public int getPixel(int x, int y) {
    x += this.halfWidth;
    y = this.halfHeight - y;
    return this.data[x + y * this.width];
  }
  
  public void getPixel(int x, int y, int[] color) {
    x += this.halfWidth;
    y = this.halfHeight - y;
    int c = this.data[x + y * this.width];
    color[0] = (c & 0xFF000000) >> 24;
    color[1] = (c & 0xFF0000) >> 16;
    color[2] = (c & 0xFF00) >> 8;
    color[3] = c & 0xFF;
  }
}

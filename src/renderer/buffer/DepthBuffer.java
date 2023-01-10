package renderer.buffer;

import java.util.Arrays;

public class DepthBuffer {
  private double[] data;
  
  private int width;
  
  private int height;
  
  private int halfWidth;
  
  private int halfHeight;
  
  private double clearValue = Double.POSITIVE_INFINITY;
  
  public DepthBuffer(int width, int height) {
    this.width = width;
    this.height = height;
    this.halfWidth = width / 2;
    this.halfHeight = height / 2;
    this.data = new double[width * height];
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public double getClearValue() {
    return this.clearValue;
  }
  
  public void setClearValue(double clearValue) {
    this.clearValue = clearValue;
  }
  
  public void clear() {
    Arrays.fill(this.data, this.clearValue);
  }
  
  public void set(int x, int y, double depth) {
    x += this.halfWidth;
    y = this.halfHeight - y;
    this.data[x + y * this.width] = depth;
  }
  
  public double get(int x, int y) {
    x += this.halfWidth;
    y = this.halfHeight - y;
    double r = 0.0D;
    r = this.data[x + y * this.width];
    return r;
  }
}

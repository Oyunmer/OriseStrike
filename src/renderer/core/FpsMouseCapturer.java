package renderer.core;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FpsMouseCapturer extends MouseAdapter implements KeyListener {
  private Robot robot;
  
  private boolean captureMouse = false;
  
  private Component component;
  
  private Point2D componentHalfSize = new Point2D.Double();
  
  private final Point2D sensibility = new Point2D.Double(0.0075D, 0.0075D);
  
  private final Point2D value = new Point2D.Double();
  
  private final Point2D minValue = new Point2D.Double(-1.0D, -0.2D);
  
  private final Point2D maxValue = new Point2D.Double(1.0D, 1.0D);
  
  private boolean limitX = false;
  
  private boolean limitY = true;
  
  public FpsMouseCapturer() {
    try {
      this.robot = new Robot();
    } catch (AWTException ex) {
      Logger.getLogger(FpsMouseCapturer.class.getName())
        .log(Level.SEVERE, (String)null, ex);
      System.exit(1);
    } 
  }
  
  public Point2D getSensibility() {
    return this.sensibility;
  }
  
  public Point2D getMaxValue() {
    return this.maxValue;
  }
  
  public Point2D getMinValue() {
    return this.minValue;
  }
  
  public Point2D getValue() {
    return this.value;
  }
  
  public boolean isCaptureMouse() {
    return this.captureMouse;
  }
  
  public boolean isLimitX() {
    return this.limitX;
  }
  
  public void setLimitX(boolean limitX) {
    this.limitX = limitX;
  }
  
  public boolean isLimitY() {
    return this.limitY;
  }
  
  public void setLimitY(boolean limitY) {
    this.limitY = limitY;
  }
  
  public void install(Component component) {
    this.component = component;
    this.componentHalfSize.setLocation((component
        .getWidth() / 2), (component.getHeight() / 2));
    component.addKeyListener(this);
    component.addMouseListener(this);
  }
  
  public void update() {
    if (this.captureMouse) {
      Point globalMouse = MouseInfo.getPointerInfo().getLocation();
      double dx = (this.component.getLocationOnScreen()).x + this.componentHalfSize.getX() - globalMouse.x;
      double dy = (this.component.getLocationOnScreen()).y + this.componentHalfSize.getY() - globalMouse.y;
      double lx = this.value.getX();
      double ly = this.value.getY();
      double vx = lx - dx * this.sensibility.getX();
      double vy = ly + dy * this.sensibility.getY();
      if (this.limitX)
        vx = clamp(vx, this.minValue.getX(), this.maxValue.getX()); 
      if (this.limitY)
        vy = clamp(vy, this.minValue.getY(), this.maxValue.getY()); 
      this.value.setLocation(vx, vy);
      this.robot.mouseMove(
          
          (int)((this.component.getLocationOnScreen()).x + this.componentHalfSize.getX()), 
          
          (int)((this.component.getLocationOnScreen()).y + this.componentHalfSize.getY()));
    } 
  }
  
  private double clamp(double value, double min, double max) {
    return Math.min(Math.max(value, min), max);
  }
  
  public void keyTyped(KeyEvent e) {}
  
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 27) {
      this.captureMouse = false;
      showMouseCursor();
    } 
  }
  
  public void keyReleased(KeyEvent e) {}
  
  public void mousePressed(MouseEvent e) {
    this.captureMouse = true;
    hideMouseCursor();
  }
  
  private void hideMouseCursor() {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    this.component.setCursor(toolkit.createCustomCursor(new BufferedImage(3, 3, 2), new Point(0, 0), "null"));
  }
  
  private void showMouseCursor() {
    this.component.setCursor(Cursor.getDefaultCursor());
  }
}

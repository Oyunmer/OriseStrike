package renderer.core;

import renderer.math.Vec4;

public class Light {
  public Vec4 position = new Vec4();
  
  public Vec4 ambient = new Vec4();
  
  public Vec4 diffuse = new Vec4();
  
  public Vec4 specular = new Vec4();
  
  public boolean enabled = true;
  
  public boolean isEnabled() {
    return this.enabled;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}

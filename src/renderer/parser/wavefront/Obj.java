package renderer.parser.wavefront;

import java.util.ArrayList;
import java.util.List;
import renderer.core.Material;

public class Obj {
  public List<WavefrontParser.Face> faces = new ArrayList<>();
  
  public Material material;
}

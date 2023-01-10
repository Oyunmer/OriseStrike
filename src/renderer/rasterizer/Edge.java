package renderer.rasterizer;

public class Edge {
  public Vertex a;
  
  public Vertex b;
  
  public double x;
  
  public double dx;
  
  public double[] dVars;
  
  public double[] vars;
  
  public void set(Vertex a, Vertex b, double[] vars, double[] dVars) {
    this.dVars = dVars;
    this.vars = vars;
    this.a = a;
    this.b = b;
    this.x = a.p.x;
    double den = ((int)b.p.y - (int)a.p.y);
    double dyInv = (den == 0.0D) ? 0.0D : (1.0D / den);
    this.dx = (b.p.x - a.p.x) * dyInv;
    for (int k = 0; k < a.vars.length; k++) {
      vars[k] = a.vars[k];
      dVars[k] = dyInv * (b.vars[k] - a.vars[k]);
    } 
  }
  
  public void nextYTop() {
    this.x += this.dx;
    for (int k = 0; k < this.a.vars.length; k++)
      this.vars[k] = this.vars[k] + this.dVars[k]; 
  }
  
  public void nextYBottom() {
    this.x -= this.dx;
    for (int k = 0; k < this.a.vars.length; k++)
      this.vars[k] = this.vars[k] - this.dVars[k]; 
  }
}

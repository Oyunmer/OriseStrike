package main;

import audio.Music;
import entity.Player;
import input.Mouse;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import physics.Input;
import physics.World;
import renderer.core.FpsMouseCapturer;
import renderer.core.Light;
import renderer.core.Renderer;
import renderer.core.Shader;
import renderer.core.Time;
import renderer.math.Vec2;
import renderer.math.Vec4;
import renderer.parser.wavefront.Obj;
import renderer.parser.wavefront.WavefrontParser;
import renderer.shader.GouraudShaderWithTexture;
import renderer.shader.GouraudShaderWithTexture2;

public class ViewCanvas extends Canvas {
  private boolean running = false;
  
  private BufferStrategy bs;
  
  private Renderer renderer;
  
  private Thread thread;
  
  private final Shader gouraudShader = (Shader)new GouraudShaderWithTexture();
  
  private final Shader gouraudShader2 = (Shader)new GouraudShaderWithTexture2();
  
  private final Light light = new Light();
  
  private List<Obj> objs;
  
  private List<Obj> objsPlayer;
  
  private World world;
  
  private Player player;
  
  private FpsMouseCapturer fpsMouseCapturer;
  
  private JFrame parentFrame;
  
  private double cameraTargetAngleX;
  
  private double cameraTargetAngleY;
  
  private double cameraAngleX;
  
  private double cameraAngleY;
  
  private final Vec4 cameraPosition;
  
  int[] color;
  
  int count;
  
  double crazySize;
  
  double crazySizeY;
  
  public void init() {
    this.parentFrame = (JFrame)getParent().getParent().getParent().getParent();
    this.fpsMouseCapturer = new FpsMouseCapturer();
    this.fpsMouseCapturer.install(this);
    this.world = new World(this.fpsMouseCapturer);
    createBufferStrategy(1);
    this.bs = getBufferStrategy();
    try {
      this.objs = new ArrayList<>(WavefrontParser.load("/res/cs.obj", 100.0D));
      this.objsPlayer = new ArrayList<>(WavefrontParser.load("/res/sphere.obj", 10.0D));
    } catch (Exception ex) {
      Logger.getLogger(ViewCanvas.class.getName()).log(Level.SEVERE, (String)null, ex);
      System.exit(-1);
    } 
    this.player = new Player(this.world.getPlayer(), this.objsPlayer);
    this.renderer = new Renderer(440, 330);
    this.light.diffuse.set(1.0D, 1.0D, 1.0D, 1.0D);
    this.renderer.addLight(this.light);
    this.renderer.setMatrixMode(Renderer.MatrixMode.PROJECTION);
    this.renderer.setPerspectiveProjection(Math.toRadians(60.0D));
    this.renderer.setClipZNear(-0.01D);
    this.running = true;
    this.thread = new Thread(new MainLoop());
    this.thread.start();
    Mouse mouseHandler2 = new Mouse();
    addMouseListener((MouseListener)mouseHandler2);
    Music.initialize();
    Music.play("doom7e1.mid");
  }
  
  public ViewCanvas() {
    this.cameraTargetAngleX = 0.0D;
    this.cameraTargetAngleY = Math.toRadians(30.0D);
    this.cameraAngleX = 0.0D;
    this.cameraAngleY = Math.toRadians(30.0D);
    this.cameraPosition = new Vec4(100.0D, 150.0D, 0.0D, 50.0D);
    this.color = new int[] { 255, 255, 0, 255 };
    this.count = 0;
    this.crazySize = 200.0D;
    this.crazySizeY = 0.0D;
    addKeyListener((KeyListener)new Input());
  }
  
  public void update() {
    Time.update();
    Time.updateFPS2();
    this.fpsMouseCapturer.update();
    if (this.fpsMouseCapturer.isCaptureMouse()) {
      this.parentFrame.setTitle("Press ESC to release mouse capture.");
    } else {
      this.parentFrame.setTitle("Click anywhere inside window to capture mouse.");
    } 
    while (Time.needsFixedUpdate()) {
      this.world.update();
      this.player.update();
    } 
    this.cameraTargetAngleX = this.fpsMouseCapturer.getValue().getY() * 200.0D;
    this.cameraTargetAngleY = this.world.getPlayer().getAngle() + Math.toRadians(90.0D);
    this.cameraAngleX += (Math.toRadians(this.cameraTargetAngleX * 0.5D) - this.cameraAngleX) * 0.1D;
    this.cameraAngleY += (this.cameraTargetAngleY - this.cameraAngleY) * 0.5D;
    this.cameraPosition.x = (this.world.getPlayer().getCollider().getPosition()).x + 0.0D * Math.cos(this.world.getPlayer().getAngle() + Math.toRadians(90.0D));
    this.cameraPosition.y = (this.world.getPlayer().getCollider().getPosition()).y + 8.0D + 60.0D;
    this.cameraPosition.z = (this.world.getPlayer().getCollider().getPosition()).z + 0.0D * Math.sin(this.world.getPlayer().getAngle() + Math.toRadians(90.0D));
    this.renderer.setBackfaceCullingEnabled(true);
    this.renderer.clearAllBuffers();
    drawLevel();
    drawPlayer();
  }
  
  private void drawLevel() {
    this.renderer.setShader(this.gouraudShader2);
    this.renderer.setMatrixMode(Renderer.MatrixMode.VIEW);
    this.renderer.setIdentity();
    this.renderer.rotateX(this.cameraAngleX + 6.283185307179586D);
    this.renderer.rotateY(-this.cameraAngleY + 1.5707963267948966D);
    this.renderer.translate(-this.cameraPosition.x, -this.cameraPosition.y, -this.cameraPosition.z);
    this.renderer.setMatrixMode(Renderer.MatrixMode.MODEL);
    this.renderer.setIdentity();
    for (Obj obj : this.objs) {
      this.renderer.setMaterial(obj.material);
      this.renderer.begin();
      for (WavefrontParser.Face face : obj.faces) {
        for (int f = 0; f < 3; f++) {
          Vec4 v = face.vertex[f];
          Vec4 n = face.normal[f];
          Vec2 t = face.texture[f];
          this.renderer.setTextureCoordinates(t.x, t.y);
          this.renderer.setNormal(n.x, n.y, n.z);
          this.renderer.setVertex(v.x, v.y, v.z);
        } 
      } 
      this.renderer.end();
    } 
  }
  
  private void drawPlayer() {
    this.renderer.setShader(this.gouraudShader);
    this.renderer.setMatrixMode(Renderer.MatrixMode.VIEW);
    this.renderer.setIdentity();
    this.renderer.rotateX(this.cameraAngleX + 6.283185307179586D);
    this.renderer.rotateY(-this.cameraAngleY + 1.5707963267948966D);
    this.renderer.translate(-this.cameraPosition.x, -this.cameraPosition.y, -this.cameraPosition.z);
    this.renderer.setMatrixMode(Renderer.MatrixMode.MODEL);
    this.renderer.setIdentity();
    this.player.draw(this.renderer);
  }
  
  public void draw(Graphics2D g) {
    Graphics2D g2d = (Graphics2D)this.renderer.getColorBuffer().getColorBuffer().getGraphics();
    g2d.setColor(Color.WHITE);
    g2d.drawString("FPS: " + Time.fps2, 10, 20);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.drawImage(this.renderer.getColorBuffer().getColorBuffer(), 0, 0, 400, 300, 0, 0, 440, 330, null);
    g.drawImage(this.renderer.getColorBuffer().getColorBuffer(), 0, 0, 800, 600, 0, 0, 400, 300, null);
  }
  
  private class MainLoop implements Runnable {
    private MainLoop() {}
    
    public void run() {
      while (ViewCanvas.this.running) {
        Time.update();
        ViewCanvas.this.update();
        Graphics2D g = (Graphics2D)ViewCanvas.this.bs.getDrawGraphics();
        ViewCanvas.this.draw(g);
        g.dispose();
        ViewCanvas.this.bs.show();
      } 
    }
  }
}

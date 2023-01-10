package main;

import javax.swing.GroupLayout;
import javax.swing.JFrame;

public class View extends JFrame {
  private ViewCanvas viewCanvas;
  
  public View() {
    initComponents();
    setTitle("First Person Shooter Game Test.");
    setSize(800, 600);
    setLocationRelativeTo(null);
    this.viewCanvas.requestFocus();
    this.viewCanvas.init();
  }
  
  private void initComponents() {
    this.viewCanvas = new ViewCanvas();
    setDefaultCloseOperation(3);
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.viewCanvas, -1, 420, 32767));
    layout.setVerticalGroup(layout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(this.viewCanvas, -1, 320, 32767));
    pack();
  }
}

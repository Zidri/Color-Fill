/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colorfill;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author joannaahannigan
 */
public class gamePlayer extends JComponent {
    
    int overlappedBlock = 0;

    public int getOverlappedBlock() {
        return overlappedBlock;
    }

    public void setOverlappedBlock(int overlappedBlock) {
        this.overlappedBlock = overlappedBlock;
    }
    
    Color col = Color.yellow;

    public Color getCol() {
        return col;
    }

    public void setCol(Color col) {
        this.col = col;
    }

    //Moving Dot
    int xpos = 0;
    int ypos = 0;
    
    int radius = 12;
    
    public gamePlayer(){        
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);        
       
        g.setColor(col);
        g.fillOval(xpos, ypos , radius, radius);
        
        this.repaint();
    }
    
}

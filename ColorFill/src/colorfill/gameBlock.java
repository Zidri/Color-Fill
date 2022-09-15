/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colorfill;

import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;

/**
 *
 * @author joannaahannigan
 */
public class gameBlock extends JComponent {

    Color pathColor = Color.white;
    int width = 15;
    int height = 15;
    int xpos = 0;
    int ypos = 0;
    int xloc = 0;
    int yloc = 0;
    int num = 0;
    

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getXloc() {
        return xloc;
    }

    public void setXloc(int xloc) {
        this.xloc = xloc;
    }

    public int getYloc() {
        return yloc;
    }

    public void setYloc(int yloc) {
        this.yloc = yloc;
    }

    boolean isColored = false;
    boolean isPath = false;

    public boolean isIsPath() {
        return isPath;
    }

    public void setIsPath(boolean isPath) {
        this.isPath = isPath;
    }

    public boolean isIsColored() {
        return isColored;
    }

    public void setIsColored(boolean isColored) {
        this.isColored = isColored;
    }

    public gameBlock() {
    }

    public Color getPathColor() {
        return pathColor;
    }

    public void setPathColor(Color pathColor) {
        this.pathColor = pathColor;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

//draw rectangle
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(pathColor);
        g.fillRect(xpos, ypos, width, height);

        this.repaint();
    }

}

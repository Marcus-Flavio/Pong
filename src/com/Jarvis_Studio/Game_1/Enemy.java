package com.Jarvis_Studio.Game_1;

import java.awt.*;

public class Enemy {
    public int ponts = 0;

    public int W,H;
    public double x,y;
    public double speed;

    public Enemy(int x, int y, int W, int H){
       this.y = y;
       this.x = x;
       this.H = H;
       this.W = W;

    }
    public void tick(){
        if(y < 0){
            y = 0;
        }
        else if(y+H > Game.H){
            y = Game.H-H;
        }
        y += (Game.bola.y-y - 6) * speed;


    }
    public void setEnemy(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect((int)x, (int)y, W, H);
    }
}

package com.Jarvis_Studio.Game_1;

import java.awt.*;

public class Player{
    public int ponts = 0;

    public boolean up, down;
    public int y,x;
    public int H,W;

    public Player(int x, int y, int W, int H){
        this.x = x;
        this.y = y;
        this.H = H ;
        this.W = W;
    }

    public void tick(){
        if(up){
            y--;
        }
        else if(down){
            y++;
            }
        if(y < 0){
            y = 0;
        }
        else if(y+H > Game.H){
            y = Game.H-H;
        }

    }
    public void setPlayer(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, W, H);
    }

}

package com.Jarvis_Studio.Game_1;

import java.awt.*;
import java.util.Random;

public class Bola {

    public int x,y;
    public int W,H;

    public double dx, dy;
    public double speed = 4.0;
    public int angulo = new Random().nextInt(359);

    public Bola(int x, int y, int W, int H){
        this.x = x;
        this.y = y;
        this.H = H;
        this.W = W;

        dx = Math.cos(Math.toRadians(angulo));
        dy = Math.sin(Math.toRadians(angulo));
    }
    public void tick(){

        if (y + (dy * speed) + H >= Game.H) {
            dy *= -1;

        } else if (y + (dy * speed) < 0) {
            dy *= -1;
        }
        if (x < 0) {
            Game.enemy.ponts += 1;
            restart();
        }
        else if(x > Game.W){
            Game.player.ponts += 1;
            restart();
        }
        Rectangle bounds = new Rectangle((int)(x + dx*speed), (int)(y + dy*speed), H, W);
        Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.W, Game.player.H);
        Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.W, Game.enemy.H);

        if(bounds.intersects(boundsPlayer)){
            dx *= -1;
        }
        else if(bounds.intersects(boundsEnemy)){
            dx *= -1;
        }

        x += dx*speed;
        y += dy*speed;


    }
    public void setBola(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(x, y, W, H);

    }
    public void restart(){
        angulo = new Random().nextInt(359);
        x = Game.W/2;
        y = Game.H/2;
        dx = Math.cos(Math.toRadians(angulo));
        dy = Math.sin(Math.toRadians(angulo));
    }
}


package com.Jarvis_Studio.Game_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, KeyListener {

    private static final long serialVersionUID = 1l;
    public static int H = 160;
    public static int W = 240;
    public static int scale = 4;

    private Thread thread;
    private boolean rodando;
    public int level = 0;
    private String estado = "JOGANDO";

    public BufferedImage image;
    public static Player player;
    public static Enemy enemy;
    public static Bola bola;

    public JFrame frame;

    public Game(){
        setPreferredSize(new Dimension(W*scale, H*scale));
        addKeyListener(this);

        player = new Player(0, 50, 5, 50);
        enemy = new Enemy(Game.W-5, player.y, player.W, player.H);
        bola = new Bola(W/2-5, H/2-5, 5, 5);
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        enemy.speed = 0.02;

        Janela();
    }
    public void Janela(){
        frame = new JFrame("Pong Bedrock edition");
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void main(String[] args){
        Game game = new Game();
        game.start();
    }

    public synchronized void start(){
        thread = new Thread(this, "Game");
        thread.start();
        rodando = true;
    }
    public synchronized void stop(){
        rodando = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick(){
        player.tick();
        enemy.tick();
        bola.tick();
        enemy.speed += level/10;
        bola.speed += level;
        if(enemy.ponts == 10){
            estado = "GAME_OVER";
            player.ponts = 0;
            enemy.ponts = 0;
        }
        else if(player.ponts == 10){
            level++;
            player.ponts = 0;
            enemy.ponts = 0;
        }


    }
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, W, H);
        if(estado.equals("JOGANDO")) {
            g.setFont(new Font("arial", Font.BOLD, 16));
            g.setColor(Color.WHITE);
            g.drawString("Pong", 96, 14);
            g.drawString("" + player.ponts, 50, 14);
            g.drawString("" + enemy.ponts, 182, 14);

            player.setPlayer(g);
            enemy.setEnemy(g);
            bola.setBola(g);
        }
        else if(estado.equals("GAME_OVER")){
            g.setFont(new Font("arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString("Game Over", W/2-55, H/2);
            g.setFont(new Font("arial", Font.BOLD, 10));
            g.drawString("Press enter to continue", W/2-56, H/2+15);
        }
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, W*scale, H*scale, null);

        bs.show();
    }


    @Override
    public void run() {

        long lasTime = System.nanoTime();
        double amountofTicks = 60.0;
        double ns = 1000000000/ amountofTicks;
        double timer = System.currentTimeMillis();
        double delta = 0;
        int frames = 0;
        while(rodando){
            requestFocus();
            long now = System.nanoTime();
            delta += (now-lasTime)/ns;
            lasTime = now;
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS:"+frames);
                frames = 0;
                timer += 1000;
            }

        }
        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            player.up = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            player.down = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            bola.restart();
        }
        if(estado.equals("GAME_OVER")) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                estado = "JOGANDO";
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            player.up = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            player.down = false;
        }
    }
}

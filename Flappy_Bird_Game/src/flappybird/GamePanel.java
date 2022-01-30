package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private Bird bird;
    private ArrayList<Rectangle> rectangles;
    private FlappyBirdFrame fb;

    public static final int PIPE_W = 78, PIPE_H = 27;
    private Image background, pipeHead, pipePart;

    public GamePanel(FlappyBirdFrame fb, Bird bird,
            ArrayList<Rectangle> rectangles) {
        try {
            this.fb = fb;
            this.bird = bird;
            this.rectangles = rectangles;

            pipeHead = ImageIO.read(getClass().getResource("/images/pipe_head.png"));
            pipePart = ImageIO.read(getClass().getResource("/images/pipe_part.png"));
            background = ImageIO.read(getClass().getResource("/images/background.png"));
            //pipeHead = ImageIO.read(new File("pipe_head.png"));
            //pipePart = ImageIO.read(new File("pipe_part.png"));
            //background = ImageIO.read(new File("background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.fillRect(0, 0, fb.getWidth(), fb.getHeight());
        g.drawImage(background, 0, 0, null);
        bird.update(g);
        g.setColor(Color.RED);
        for (Rectangle rectangle : rectangles) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            AffineTransform old = g2d.getTransform();
            g2d.translate(rectangle.x + PIPE_W / 2, rectangle.y + PIPE_H / 2);
            if (rectangle.y < fb.getHeight() / 2) {
                g2d.translate(0, rectangle.height);
                g2d.rotate(Math.PI);
            }
            g2d.drawImage(pipeHead, -PIPE_W / 2, -PIPE_H / 2, GamePanel.PIPE_W,
                    GamePanel.PIPE_H, null);
            g2d.drawImage(pipePart, -PIPE_W / 2, PIPE_H / 2,
                    GamePanel.PIPE_W, rectangle.height, null);
            g2d.setTransform(old);
        }
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + fb.getScore(), 10, 20);

        if (fb.paused()) {
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(new Color(0, 0, 0, 170));
            g.drawString("PAUSED", fb.getWidth() / 2 - 100,
                    fb.getHeight() / 2 - 100);
            g.drawString("PRESS SPACE TO BEGIN", fb.getWidth() / 2 - 300,
                    fb.getHeight() / 2 + 50);
        }
    }
}

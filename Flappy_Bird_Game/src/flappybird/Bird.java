package flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird {

    private float x, y, vy;
    private Image img;
    private FlappyBirdFrame fb;

    public Bird(FlappyBirdFrame fb) {
        try {
            this.fb = fb;
            reset();
            img = ImageIO.read(getClass().getResource("/images/bird.png"));
            //img = ImageIO.read(new File("bird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void physics() {
        y += vy;
        vy += 0.5f;
    }

    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(img, (int) x, (int) y, null);
    }

    public void jump() {
        vy = -8;
    }

    public void reset() {
        x = (fb.getWidth() / 2) - 40;
        y = (fb.getHeight() / 2) - 60;
        vy = 0;
    }
}

package flappybird;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlappyBirdFrame extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;

    private Bird bird;
    private JPanel gamePanel;
    private ArrayList<Rectangle> rectangles;
    private int time, scroll;
    private Timer timer;
    private boolean paused;
    boolean gameFinished = true;

    public FlappyBirdFrame() {
        setTitle("Flappy Bird");
        setSize(640, 480);

        paused = true;

        timer = new Timer(1000/60, this);
        timer.start();

        bird = new Bird(this);
        rectangles = new ArrayList<Rectangle>();
        gamePanel = new GamePanel(this, bird, rectangles);
        add(gamePanel);
        addKeyListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //gamePanel.repaint();
        if (!paused) {
            gamePanel.repaint();
            bird.physics();
            if (scroll % 90 == 0) {
                Rectangle topPipe = new Rectangle(getWidth(), 0, GamePanel.PIPE_W,
                        (int) ((Math.random() * getHeight()) / 5f + (0.2f) * getHeight()));
                int h2 = (int) ((Math.random() * getHeight()) / 5f + (0.2f) * getHeight());
                Rectangle bottomPipe = new Rectangle(getWidth(), getHeight() - h2,
                        GamePanel.PIPE_W, h2);
                rectangles.add(topPipe);
                rectangles.add(bottomPipe);
            }
            ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
            gameFinished = true;
            for (Rectangle rectangle : rectangles) {
                rectangle.x -= 3;
                if (rectangle.x + rectangle.width <= 0) {
                    toRemove.add(rectangle);
                }
                if (rectangle.contains(bird.getX(), bird.getY())) {
                    lostGame();
                }
            }
            rectangles.removeAll(toRemove);
            time++;
            scroll++;

            if (bird.getY() > getHeight() || bird.getY() < 0) {
                lostGame();
            }

            if (!gameFinished) {
                rectangles.clear();
                bird.reset();
                time = 0;
                scroll = 0;
                paused = true;
            }
        }
    }

    private void lostGame() {
        JOptionPane.showMessageDialog(this, "You lose!\n"
                + "Your score was: " + time + ".");
        gameFinished = false;
        gamePanel.repaint();
    }

    public int getScore() {
        return time;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            bird.jump();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            paused = false;
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public boolean paused() {
        return paused;
    }

    public static void main(String[] args) {
        new FlappyBirdFrame();
    }

}

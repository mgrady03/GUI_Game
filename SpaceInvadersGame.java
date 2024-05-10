import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame extends JFrame implements KeyListener, ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_WIDTH = 50;
    private static final int PLAYER_HEIGHT = 30;
    private static final int ENEMY_WIDTH = 30;
    private static final int ENEMY_HEIGHT = 30;
    private static final int PLAYER_SPEED = 5;
    private static int ENEMY_SPEED = 3;
    private static final int ENEMY_ROWS = 3;
    private static final int ENEMY_COLS = 10;
    private static final int ENEMY_START_Y = 50;
    private static final int ENEMY_GAP = 10;
    private static final int BULLET_WIDTH = 5;
    private static final int BULLET_HEIGHT = 10;
    private static final int BULLET_SPEED = 8;

    private boolean leftKeyPressed;
    private boolean rightKeyPressed;
    private boolean spaceKeyPressed;
    private int playerX;
    private int playerY;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Timer timer;

    public SpaceInvadersGame() {
        setTitle("Space Invaders");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        addKeyListener(this);

        playerX = WIDTH / 2 - PLAYER_WIDTH / 2;
        playerY = HEIGHT - 100;

        enemies = new ArrayList<>();
        createEnemies();

        bullets = new ArrayList<>();

        timer = new Timer(10, this);
        timer.start();

        setVisible(true);
    }

    private void createEnemies() {
        for (int row = 0; row < ENEMY_ROWS; row++) {
            for (int col = 0; col < ENEMY_COLS; col++) {
                int x = col * (ENEMY_WIDTH + ENEMY_GAP) + 50;
                int y = row * (ENEMY_HEIGHT + ENEMY_GAP) + ENEMY_START_Y;
                enemies.add(new Enemy(x, y));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw player
        g.setColor(Color.BLUE);
        g.fillRect(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);

        // Draw enemies
        for (Enemy enemy : enemies) {
            g.setColor(Color.RED);
            g.fillRect(enemy.getX(), enemy.getY(), ENEMY_WIDTH, ENEMY_HEIGHT);
        }

        // Draw bullets
        g.setColor(Color.WHITE);
        for (Bullet bullet : bullets) {
            g.fillRect(bullet.getX(), bullet.getY(), BULLET_WIDTH, BULLET_HEIGHT);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (leftKeyPressed && playerX > 0) {
            playerX -= PLAYER_SPEED;
        }
        if (rightKeyPressed && playerX < WIDTH - PLAYER_WIDTH) {
            playerX += PLAYER_SPEED;
        }
        if (spaceKeyPressed) {
            fireBullet();
        }

        // Move bullets
        for (Bullet bullet : bullets) {
            bullet.move();
        }

        // Move enemies
        for (Enemy enemy : enemies) {
            enemy.move();
        }

        // Check for collisions
        checkCollisions();

        repaint();
    }

    private void fireBullet() {
        int bulletX = playerX + PLAYER_WIDTH / 2 - BULLET_WIDTH / 2;
        int bulletY = playerY;
        bullets.add(new Bullet(bulletX, bulletY));
    }

    private void checkCollisions() {
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.intersects(enemy)) {
                    bullets.remove(bullet);
                    enemies.remove(enemy);
                    break;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            leftKeyPressed = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightKeyPressed = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            spaceKeyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            leftKeyPressed = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightKeyPressed = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            spaceKeyPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private class Enemy {
        private int x;
        private int y;

        public Enemy(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move() {
            x += ENEMY_SPEED;
            if (x >= WIDTH - ENEMY_WIDTH || x <= 0) {
                ENEMY_SPEED *= -1;
                y += ENEMY_HEIGHT;
            }
        }
    }

    private class Bullet {
        private int x;
        private int y;

        public Bullet(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move() {
            y -= BULLET_SPEED;
            if (y < 0) {
                bullets.remove(this);
            }
        }

        public boolean intersects(Enemy enemy) {
            return x < enemy.getX() + ENEMY_WIDTH &&
                    x + BULLET_WIDTH > enemy.getX() &&
                    y < enemy.getY() + ENEMY_HEIGHT &&
                    y + BULLET_HEIGHT > enemy.getY();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SpaceInvadersGame());
    }
}

import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.add(new GamePanel());
            frame.setVisible(true);
        });
    }

 static class GamePanel extends JPanel {
    private final int gridSize = 20;
    private final int cellSize = 30;
    private java.util.List<Point> snake;
    private final Color backgroundColor = new Color(30, 33, 40);
    private final Color gridColor = new Color(50, 53, 60);
    private final Color snakeColor = Color.GREEN;
    private final Color foodColor = Color.RED;
    private int dx = 1, dy = 0; // initial direction: right
    private int pendingDx = 1, pendingDy = 0; // for key input
    private Point food;
    private int score = 0;
    private boolean gameOver = false;
    private Timer timer;
    private final Font scoreFont = new Font("Arial", Font.BOLD, 20);
    private final Font gameOverFont = new Font("Arial", Font.BOLD, 40);
    private final java.util.Random rand = new java.util.Random();

    public GamePanel() {
        setPreferredSize(new Dimension(gridSize * cellSize, gridSize * cellSize));
        setBackground(backgroundColor);
        setFocusable(true);
        resetGame();
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (!gameOver) {
                    if (key == java.awt.event.KeyEvent.VK_UP && dy != 1) {
                        pendingDx = 0; pendingDy = -1;
                    } else if (key == java.awt.event.KeyEvent.VK_DOWN && dy != -1) {
                        pendingDx = 0; pendingDy = 1;
                    } else if (key == java.awt.event.KeyEvent.VK_LEFT && dx != 1) {
                        pendingDx = -1; pendingDy = 0;
                    } else if (key == java.awt.event.KeyEvent.VK_RIGHT && dx != -1) {
                        pendingDx = 1; pendingDy = 0;
                    }
                } else if (key == java.awt.event.KeyEvent.VK_R) {
                    resetGame();
                }
            }
        });
    }

    private void resetGame() {
        // Snake facing right, centered
        int cx = gridSize / 2;
        int cy = gridSize / 2;
        snake = new java.util.ArrayList<>();
        snake.add(new Point(cx - 1, cy));
        snake.add(new Point(cx, cy));
        snake.add(new Point(cx + 1, cy));
        dx = 1; dy = 0;
        pendingDx = 1; pendingDy = 0;
        score = 0;
        gameOver = false;
        spawnFood();
        if (timer != null) timer.stop();
        timer = new Timer(150, e -> moveSnake());
        timer.start();
        requestFocusInWindow();
        repaint();
    }

    private void spawnFood() {
        java.util.Set<Point> occupied = new java.util.HashSet<>(snake);
        java.util.List<Point> empty = new java.util.ArrayList<>();
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Point p = new Point(x, y);
                if (!occupied.contains(p)) empty.add(p);
            }
        }
        if (empty.isEmpty()) {
            food = null;
            return;
        }
        food = empty.get(rand.nextInt(empty.size()));
    }

    private void moveSnake() {
        if (gameOver) return;
        // Update direction from pending (after move, so no double turns in one tick)
        if (Math.abs(dx) != Math.abs(pendingDx) || Math.abs(dy) != Math.abs(pendingDy)) {
            dx = pendingDx;
            dy = pendingDy;
        }
        // Compute new head position
        Point head = snake.get(snake.size() - 1);
        int newX = head.x + dx;
        int newY = head.y + dy;
        // Wall collision
        if (newX < 0 || newX >= gridSize || newY < 0 || newY >= gridSize) {
            endGame();
            return;
        }
        Point newHead = new Point(newX, newY);
        // Self collision
        if (snake.contains(newHead)) {
            endGame();
            return;
        }
        // Move snake
        snake.add(newHead);
        // Food collision
        if (food != null && newHead.equals(food)) {
            score++;
            spawnFood();
        } else {
            snake.remove(0); // move forward (no growth)
        }
        repaint();
    }

    private void endGame() {
        gameOver = true;
        timer.stop();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, gridSize * cellSize, gridSize * cellSize);
        // Draw grid
        g.setColor(gridColor);
        for (int i = 0; i <= gridSize; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, gridSize * cellSize);
            g.drawLine(0, i * cellSize, gridSize * cellSize, i * cellSize);
        }
        // Draw food
        if (food != null) {
            g.setColor(foodColor);
            g.fillOval(food.x * cellSize, food.y * cellSize, cellSize, cellSize);
        }
        // Draw snake
        g.setColor(snakeColor);
        for (Point p : snake) {
            g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
        }
        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(scoreFont);
        g.drawString("Score: " + score, 10, 25);
        // Draw game over
        if (gameOver) {
            g.setFont(gameOverFont);
            g.setColor(Color.RED);
            String msg = "Game Over";
            int msgWidth = g.getFontMetrics().stringWidth(msg);
            g.drawString(msg, (getWidth() - msgWidth) / 2, getHeight() / 2);
            g.setFont(scoreFont);
            String restart = "Press R to restart";
            int restartWidth = g.getFontMetrics().stringWidth(restart);
            g.setColor(Color.WHITE);
            g.drawString(restart, (getWidth() - restartWidth) / 2, getHeight() / 2 + 40);
        }
    }
}
}
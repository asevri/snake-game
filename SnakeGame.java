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
    private final Point[] snake;
    private final Color backgroundColor = new Color(30, 33, 40);
    private final Color gridColor = new Color(50, 53, 60);
    private final Color snakeColor = Color.GREEN;
    private int dx = 1, dy = 0; // initial direction: right
    private int pendingDx = 1, pendingDy = 0; // for key input
    private final Timer timer;

    public GamePanel() {
        setPreferredSize(new Dimension(gridSize * cellSize, gridSize * cellSize));
        setBackground(backgroundColor);
        setFocusable(true);
        requestFocusInWindow();
        // Snake facing right, centered
        int cx = gridSize / 2;
        int cy = gridSize / 2;
        snake = new Point[] {
            new Point(cx - 1, cy),
            new Point(cx, cy),
            new Point(cx + 1, cy)
        };
        // Key controls
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                if (key == java.awt.event.KeyEvent.VK_UP && dy != 1) {
                    pendingDx = 0; pendingDy = -1;
                } else if (key == java.awt.event.KeyEvent.VK_DOWN && dy != -1) {
                    pendingDx = 0; pendingDy = 1;
                } else if (key == java.awt.event.KeyEvent.VK_LEFT && dx != 1) {
                    pendingDx = -1; pendingDy = 0;
                } else if (key == java.awt.event.KeyEvent.VK_RIGHT && dx != -1) {
                    pendingDx = 1; pendingDy = 0;
                }
            }
        });
        // Timer for movement
        timer = new Timer(150, e -> moveSnake());
        timer.start();
    }

    private void moveSnake() {
        // Update direction from pending (after move, so no double turns in one tick)
        if (Math.abs(dx) != Math.abs(pendingDx) || Math.abs(dy) != Math.abs(pendingDy)) {
            dx = pendingDx;
            dy = pendingDy;
        }
        // Move snake: shift body
        for (int i = 0; i < snake.length - 1; i++) {
            snake[i].setLocation(snake[i + 1]);
        }
        // Move head
        Point head = snake[snake.length - 1];
        int newX = (head.x + dx + gridSize) % gridSize;
        int newY = (head.y + dy + gridSize) % gridSize;
        snake[snake.length - 1] = new Point(newX, newY);
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
        // Draw snake
        g.setColor(snakeColor);
        for (Point p : snake) {
            g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
        }
    }
}
}
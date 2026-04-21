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

    public GamePanel() {
        setPreferredSize(new Dimension(gridSize * cellSize, gridSize * cellSize));
        setBackground(backgroundColor);
        // Snake facing right, centered
        int cx = gridSize / 2;
        int cy = gridSize / 2;
        snake = new Point[] {
            new Point(cx - 1, cy),
            new Point(cx, cy),
            new Point(cx + 1, cy)
        };
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

import java.awt.*;
import javax.swing.*;

public class SnakeStarter extends JPanel {
    private final int gridSize = 20;
    private final int cellSize = 30; // 20 * 30 = 600

    private final Point[] snake;

    public SnakeStarter() {
        // initialize a 3-segment snake near the center (horizontal)
        snake = new Point[3];
        int cx = gridSize / 2;
        int cy = gridSize / 2;
        snake[0] = new Point(cx - 1, cy);
        snake[1] = new Point(cx, cy);
        snake[2] = new Point(cx + 1, cy);
        setBackground(new Color(22, 28, 39));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(gridSize * cellSize, gridSize * cellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw background first
        g.setColor(new Color(22, 28, 39));
        g.fillRect(0, 0, gridSize * cellSize, gridSize * cellSize);

        // draw a green 3-segment snake
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake Starter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SnakeStarter());
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
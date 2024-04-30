package view;

import enums.Direction;
import model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MapDisplay extends JFrame {

    private static final int RECTANGLE_SIZE = 50; // Adjust as needed
    private static final Color NODE_COLOR = Color.LIGHT_GRAY;
    private static final Color START_COLOR = Color.GREEN;
    private static final Color END_COLOR = Color.RED;
    private static final Color CROSSROAD_EXIT_COLOR = Color.BLUE;
    private static final Color CROSSROAD_START_COLOR = Color.YELLOW;
    private static final Color CAR_COLOR = Color.ORANGE;

    private static final int INITIAL_WIDTH = 800;
    private static final int INITIAL_HEIGHT = 800;
    private static final int MARGIN = 50;

    private Node[][] nodes;

    public MapDisplay(Node[][] nodes) {
        this.nodes = nodes;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Map Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMap(g);
            }
        };
        add(panel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                panel.repaint();
            }
        });

        setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        setLocationRelativeTo(null);
    }

    private void drawMap(Graphics g) {
        int numRows = nodes.length;
        int numCols = nodes[0].length;

        int rectWidth = (getWidth() - 2 * MARGIN) / numCols;
        int rectHeight = (getHeight() - 2 * MARGIN) / numRows;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int x = MARGIN + j * rectWidth;
                int y = MARGIN + i * rectHeight;

                // Draw border
                g.setColor(Color.BLACK);
                g.drawRect(x, y, rectWidth, rectHeight);

                // Fill rectangle with node color
                Node node = nodes[i][j];
                Color color = getNodeColor(node);
                g.setColor(color);
                g.fillRect(x + 1, y + 1, rectWidth - 1, rectHeight - 1);

                // Draw arrow if not crossroad
                if (!node.isCrossRoad()) {
                    drawArrow(g, node.getDirection(), x, y, rectWidth, rectHeight);
                }
            }
        }
    }

    private void drawArrow(Graphics g, Direction direction, int x, int y, int width, int height) {
        int arrowSize = 10;
        int arrowHalfSize = arrowSize / 2;

        int centerX = x + width / 2;
        int centerY = y + height / 2;

        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        switch (direction) {
            case UP -> {
                xPoints[0] = centerX;
                yPoints[0] = centerY - arrowHalfSize;
                xPoints[1] = centerX - arrowHalfSize;
                yPoints[1] = centerY + arrowHalfSize;
                xPoints[2] = centerX + arrowHalfSize;
                yPoints[2] = centerY + arrowHalfSize;
            }
            case RIGHT -> {
                xPoints[0] = centerX + arrowHalfSize;
                yPoints[0] = centerY;
                xPoints[1] = centerX - arrowHalfSize;
                yPoints[1] = centerY - arrowHalfSize;
                xPoints[2] = centerX - arrowHalfSize;
                yPoints[2] = centerY + arrowHalfSize;
            }
            case DOWN -> {
                xPoints[0] = centerX;
                yPoints[0] = centerY + arrowHalfSize;
                xPoints[1] = centerX - arrowHalfSize;
                yPoints[1] = centerY - arrowHalfSize;
                xPoints[2] = centerX + arrowHalfSize;
                yPoints[2] = centerY - arrowHalfSize;
            }
            case LEFT -> {
                xPoints[0] = centerX - arrowHalfSize;
                yPoints[0] = centerY;
                xPoints[1] = centerX + arrowHalfSize;
                yPoints[1] = centerY - arrowHalfSize;
                xPoints[2] = centerX + arrowHalfSize;
                yPoints[2] = centerY + arrowHalfSize;
            }
            default -> {
                // Do nothing for other directions
            }
        }

        g.setColor(Color.BLACK);
        g.fillPolygon(xPoints, yPoints, 3);
    }


    private Color getNodeColor(Node node) {
        return switch (node.getDirection()) {
            case UP -> Color.BLUE;
            case RIGHT -> Color.RED;
            case DOWN -> Color.GREEN;
            case LEFT -> Color.YELLOW;
            case CROSS_UP_RIGHT, CROSS_UP_LEFT, CROSS_RIGHT_DOWN, CROSS_DOWN_LEFT, CROSS_UP, CROSS_DOWN, CROSS_LEFT,
                 CROSS_RIGHT -> Color.PINK;
            default -> Color.LIGHT_GRAY;
        };
    }


    public static void main(String[] args) {
        // Initialize your nodes here
        // Node[][] nodes = ...;
        //
    }
}

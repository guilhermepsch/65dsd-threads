package view;

import enums.Direction;
import factories.PathFactory;
import model.Car;
import model.Map;
import model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapDisplay {

    private static final int RECTANGLE_SIZE = 50; // Adjust as needed
    private static final Color NODE_COLOR = Color.LIGHT_GRAY;
    private static final Color START_COLOR = Color.GREEN;
    private static final Color END_COLOR = Color.RED;
    private static final Color CROSSROAD_EXIT_COLOR = Color.BLUE;
    private static final Color CROSSROAD_START_COLOR = Color.YELLOW;
    private static final Color CAR_COLOR = Color.WHITE;

    private static final int INITIAL_WIDTH = 800;
    private static final int INITIAL_HEIGHT = 800;
    private static final int MARGIN = 50;

    private JPanel panel;
    private Map map;
    private Node[][] nodes;
    private List<Car> cars;
    private PathFactory pathFactory;
    private volatile boolean simulationRunning;

    public MapDisplay(Map map, PathFactory pathFactory) {
        this.map = map;
        this.nodes = map.getNodes();
        this.pathFactory = pathFactory;
        this.cars = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMap(g);
                drawCars(g);
            }
        };
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                panel.repaint();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    public void repaint() {
        this.panel.repaint();
    }

    private void drawMap(Graphics g) {
        int numRows = nodes.length;
        int numCols = nodes[0].length;

        int rectWidth = (this.panel.getWidth() - 2 * MARGIN) / numCols;
        int rectHeight = (this.panel.getHeight() - 2 * MARGIN) / numRows;

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

            }
        }

        g.setColor(Color.BLACK);
        g.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawCars(Graphics g) {
        for (Car car : cars) {
            Node currentNode = car.getCurrentNode();
            if (currentNode != null) {
                int rectWidth = (this.panel.getWidth() - 2 * MARGIN) / nodes[0].length;
                int rectHeight = (this.panel.getHeight() - 2 * MARGIN) / nodes.length;

                int x = MARGIN + map.getColumn(currentNode) * rectWidth;
                int y = MARGIN + map.getRow(currentNode) * rectHeight;

                int carSize = Math.min(rectWidth, rectHeight) - 10;

                int xOffset = (rectWidth - carSize) / 2;
                int yOffset = (rectHeight - carSize) / 2;

                g.setColor(CAR_COLOR);
                g.fillOval(x + xOffset, y + yOffset, carSize, carSize);
            }
        }
    }


    public void addCar(Car car) {
        cars.add(car);
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

    public void startSimulation(int carQuantity) {
        simulationRunning = true;

        for (int i = 0; i < carQuantity && simulationRunning; i++) {
            if (insertCarIntoSimulation()) break;
        }

        new Thread(() -> {
            while (simulationRunning) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<Car> completedCars = new ArrayList<>();
                for (Car car : cars) {
                    if (!car.isAlive()) {
                        completedCars.add(car);
                    }
                }
                cars.removeAll(completedCars);

                int currentCarQuantity = cars.size();
                if (currentCarQuantity < carQuantity && simulationRunning) {
                    for (int i = 0; i < carQuantity - currentCarQuantity && simulationRunning; i++) {
                        if (insertCarIntoSimulation()) break;
                    }
                }
            }
        }).start();
    }

    private boolean insertCarIntoSimulation() {
        Node startingNode = getFirstAvailableEntrance();
        if (startingNode != null) {
            Random random = new Random();
            Car car = new Car(startingNode, map, pathFactory, random.nextInt(400, 1000), this);
            addCar(car);
            car.start();
        } else {
            System.out.println("No available entrance nodes!");
            return true;
        }
        return false;
    }

    public void stopSimulation() {
        simulationRunning = false;
        for (Car car : cars) {
            car.interrupt();
        }
        cars.clear();
        cleanNodes();
        this.panel.repaint();
    }


    private void cleanNodes() {
        for (Node[] row : nodes) {
            for (Node node : row) {
                node.removeCar();
            }
        }
    }

    private Node getFirstAvailableEntrance() {
        Random random = new Random();
        while (true) {
            int index = random.nextInt(0, map.getEntrances().length);
            if (this.map.getEntrances()[index].getCar() != null) {
                continue;
            }
            return this.map.getEntrances()[index];
        }
    }
}
package service;

import enums.Direction;
import factories.MapFactory;
import factories.NodeFactory;
import model.Map;
import model.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapParser {

    private final NodeFactory nodeFactory;
    private final MapFactory mapFactory;

    public MapParser(NodeFactory nodeFactory, MapFactory mapFactory) {
        this.nodeFactory = nodeFactory;
        this.mapFactory = mapFactory;
    }

    public Map createMapFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int numRows = Integer.parseInt((reader.readLine()).trim());
            int numCols = Integer.parseInt(reader.readLine().trim());

            Node[][] map = new Node[numRows][numCols];
            List<Node> entrances = new ArrayList<>();
            List<Node> exits = new ArrayList<>();

            int row = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+"); // Trim leading and trailing whitespace
                for (int col = 0; col < numCols; col++) {
                    int directionValue = Integer.parseInt(parts[col].trim()); // Trim whitespace from each part
                    boolean isStart = isStartNode(directionValue, row, col, numRows, numCols);
                    boolean isEnd = isEndNode(directionValue, row, col, numRows, numCols);
                    Direction direction = Direction.fromValue(directionValue);
                    map[row][col] = nodeFactory.createNode(direction, isStart, isEnd);
                    if (isStart) {
                        entrances.add(map[row][col]);
                    }
                    if (isEnd) {
                        exits.add(map[row][col]);
                    }
                }
                row++;
            }
            return mapFactory.createMap(map, entrances, exits);
        }
    }

    private boolean isStartNode(int direction, int row, int col, int numRows, int numCols) {
        return (direction == Direction.UP.getValue() && row == numRows - 1)
                || (direction == Direction.DOWN.getValue() && row == 0)
                || (direction == Direction.RIGHT.getValue() && col == 0)
                || (direction == Direction.LEFT.getValue() && col == numCols - 1);
    }

    private boolean isEndNode(int direction, int row, int col, int numRows, int numCols) {
        return (direction == Direction.UP.getValue() && row == 0)
                || (direction == Direction.DOWN.getValue() && row == numRows - 1)
                || (direction == Direction.LEFT.getValue() && col == 0)
                || (direction == Direction.RIGHT.getValue() && col == numCols - 1);
    }

}

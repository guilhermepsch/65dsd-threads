package model;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private Node[][] map;
    private Node[] entrances;
    private Node[] exits;

    public Map(Node[][] map, Node[] entrances, Node[] exits) {
        this.map = map;
        this.entrances = entrances;
        this.exits = exits;
        this.mapCrossroadsEntrancesAndExits();
    }

    public Node[][] getMap() {
        return map;
    }

    public Node[] getEntrances() {
        return entrances;
    }

    public Node[] getExits() {
        return exits;
    }

    private void mapCrossroadsEntrancesAndExits() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Node currentNode = map[i][j];
                switch (currentNode.getDirection()) {
                    case Node.UP:
                        if (i + 1 < map.length && map[i + 1][j].isCrossRoad()) {
                            currentNode.setCrossroadExit(true);
                        }
                        if (i - 1 >= 0 && map[i - 1][j].isCrossRoad()) {
                            currentNode.setCrossRoadStart(true);
                        }
                        break;
                    case Node.LEFT:
                        if (j + 1 < map[i].length && map[i][j + 1].isCrossRoad()) {
                            currentNode.setCrossroadExit(true);
                        }
                        if (j - 1 >= 0 && map[i][j - 1].isCrossRoad()) {
                            currentNode.setCrossRoadStart(true);
                        }
                        break;
                    case Node.RIGHT:
                        if (j - 1 >= 0 && map[i][j - 1].isCrossRoad()) {
                            currentNode.setCrossroadExit(true);
                        }
                        if (j + 1 < map[i].length && map[i][j + 1].isCrossRoad()) {
                            currentNode.setCrossRoadStart(true);
                        }
                        break;
                    case Node.DOWN:
                        if (i - 1 >= 0 && map[i - 1][j].isCrossRoad()) {
                            currentNode.setCrossroadExit(true);
                        }
                        if (i + 1 < map.length && map[i + 1][j].isCrossRoad()) {
                            currentNode.setCrossRoadStart(true);
                        }
                        break;
                    default:
                }
            }
        }
    }

    public Node getNodeAbove(Node currentNode) {
        int row = getRow(currentNode);
        if (row > 0)
            return map[row - 1][getColumn(currentNode)];
        return null;
    }

    public Node getNodeBelow(Node currentNode) {
        int row = getRow(currentNode);
        if (row < map.length - 1)
            return map[row + 1][getColumn(currentNode)];
        return null;
    }

    public Node getNodeOnLeft(Node currentNode) {
        int col = getColumn(currentNode);
        if (col > 0)
            return map[getRow(currentNode)][col - 1];
        return null;
    }

    public Node getNodeOnRight(Node currentNode) {
        int col = getColumn(currentNode);
        if (col < map[0].length - 1)
            return map[getRow(currentNode)][col + 1];
        return null;
    }

    public int getRow(Node node) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == node) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getColumn(Node node) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == node) {
                    return j;
                }
            }
        }
        return -1;
    }

    public Node getNextNode(int direction, Node currentNode) {
        switch (direction) {
            case Node.UP:
                return this.getNodeAbove(currentNode);
            case Node.RIGHT:
                return this.getNodeOnRight(currentNode);
            case Node.DOWN:
                return this.getNodeBelow(currentNode);
            case Node.LEFT:
                return this.getNodeOnLeft(currentNode);
            default:
                System.out.println("Invalid direction!");
                return null;
        }
    }

    public Path createPathForCrossroad(Node currentNode) {
        Path path = new Path();
        Random random = new Random();
        Node crossRoadNode = getNextNode(currentNode.getDirection(), currentNode);
        path.getNodes().add(crossRoadNode);
        currentNode = crossRoadNode;
        while (!currentNode.isCrossroadExit()) {
            switch (currentNode.getDirection()) {
                case Node.CROSS_UP:
                    currentNode = this.getNodeAbove(currentNode);
                    path.getNodes().add(currentNode);
                    break;
                case Node.CROSS_RIGHT:
                    currentNode = this.getNodeOnRight(currentNode);
                    path.getNodes().add(currentNode);
                    break;
                case Node.CROSS_DOWN:
                    currentNode = this.getNodeBelow(currentNode);
                    path.getNodes().add(currentNode);
                    break;
                case Node.CROSS_LEFT:
                    currentNode = this.getNodeOnLeft(currentNode);
                    path.getNodes().add(currentNode);
                    break;
                case Node.CROSS_UP_RIGHT, Node.CROSS_DOWN_LEFT, Node.CROSS_RIGHT_DOWN, Node.CROSS_UP_LEFT:
                    if (path.getNodes().size() == 3) {
                        for (Node n : this.getPossibleNodes(currentNode)) {
                            if (n.isCrossroadExit()) {
                                currentNode = n;
                                break;
                            }
                        }
                    } else {
                        Node[] possibleNodes = this.getPossibleNodes(currentNode);
                        currentNode = possibleNodes[random.nextInt(2)];
                    }
                    path.getNodes().add(currentNode);
            }
        }
        return path;
    }

    private Node[] getPossibleNodes(Node currentNode) {
        return switch (currentNode.getDirection()) {
            case Node.CROSS_UP_RIGHT -> new Node[]{this.getNodeAbove(currentNode), this.getNodeOnRight(currentNode)};
            case Node.CROSS_UP_LEFT -> new Node[]{this.getNodeAbove(currentNode), this.getNodeOnLeft(currentNode)};
            case Node.CROSS_RIGHT_DOWN -> new Node[]{this.getNodeOnRight(currentNode), this.getNodeBelow(currentNode)};
            case Node.CROSS_DOWN_LEFT -> new Node[]{this.getNodeBelow(currentNode), this.getNodeOnLeft(currentNode)};
            default -> new Node[]{};
        };
    }

}

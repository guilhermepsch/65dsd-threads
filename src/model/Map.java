package model;

import enums.Direction;

import java.util.Random;

public record Map(Node[][] map, Node[] entrances, Node[] exits) {

    public Map(Node[][] map, Node[] entrances, Node[] exits) {
        this.map = map;
        this.entrances = entrances;
        this.exits = exits;
        mapCrossroadsEntrancesAndExits();
    }

    private void mapCrossroadsEntrancesAndExits() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Node currentNode = map[i][j];
                switch (currentNode.getDirection()) {
                    case Direction.UP:
                        setCrossroadExitAndStart(i, j, i + 1, j, i - 1, j);
                        break;
                    case Direction.LEFT:
                        setCrossroadExitAndStart(i, j, i, j + 1, i, j - 1);
                        break;
                    case Direction.RIGHT:
                        setCrossroadExitAndStart(i, j, i, j - 1, i, j + 1);
                        break;
                    case Direction.DOWN:
                        setCrossroadExitAndStart(i, j, i - 1, j, i + 1, j);
                        break;
                    default:
                }
            }
        }
    }

    private void setCrossroadExitAndStart(int i, int j, int exitI, int exitJ, int startI, int startJ) {
        if (isValidPosition(exitI, exitJ) && map[exitI][exitJ].isCrossRoad()) {
            map[i][j].setCrossroadExit(true);
        }
        if (isValidPosition(startI, startJ) && map[startI][startJ].isCrossRoad()) {
            map[i][j].setCrossRoadStart(true);
        }
    }

    private boolean isValidPosition(int i, int j) {
        return i >= 0 && i < map.length && j >= 0 && j < map[i].length;
    }


    public Node getNextNode(Direction direction, Node currentNode) {
        int row = getRow(currentNode);
        int col = getColumn(currentNode);

        return switch (direction) {
            case UP -> getNode(row - 1, col);
            case DOWN -> getNode(row + 1, col);
            case LEFT -> getNode(row, col - 1);
            case RIGHT -> getNode(row, col + 1);
            default -> {
                System.out.println("Invalid direction!");
                yield null;
            }
        };
    }


    private Node getNode(int row, int col) {
        if (row >= 0 && row < map.length && col >= 0 && col < map[0].length) {
            return map[row][col];
        }
        return null;
    }

    public Path createPathForCrossroad(Node currentNode) {
        Path path = new Path();
        Random random = new Random();
        Node crossRoadNode = getNextNode(currentNode.getDirection(), currentNode);
        path.getNodes().add(crossRoadNode);
        currentNode = crossRoadNode;

        while (true) {
            assert currentNode != null;
            if (currentNode.isCrossroadExit()) break;
            switch (currentNode.getDirection()) {
                case Direction.CROSS_UP, Direction.CROSS_DOWN, Direction.CROSS_LEFT, Direction.CROSS_RIGHT:
                    currentNode = getNextNode(currentNode.getDirection(), currentNode);
                    path.getNodes().add(currentNode);
                    break;
                case Direction.CROSS_UP_RIGHT, Direction.CROSS_UP_LEFT, Direction.CROSS_RIGHT_DOWN,
                     Direction.CROSS_DOWN_LEFT:
                    Node[] possibleNodes = getPossibleNodes(currentNode);

                    if (path.getNodes().size() == 3) {
                        for (Node possibleNode : possibleNodes) {
                            if (!possibleNode.isCrossroadExit()) {
                                continue;
                            }
                            currentNode = possibleNode;
                        }
                    } else {
                        currentNode = possibleNodes[random.nextInt(2)];
                    }
                    path.getNodes().add(currentNode);
                    break;
            }
        }
        return path;
    }

    private Node[] getPossibleNodes(Node currentNode) {
        int row = getRow(currentNode);
        int col = getColumn(currentNode);

        return switch (currentNode.getDirection()) {
            case Direction.CROSS_UP_RIGHT -> new Node[]{getNode(row - 1, col), getNode(row, col + 1)};
            case Direction.CROSS_UP_LEFT -> new Node[]{getNode(row - 1, col), getNode(row, col - 1)};
            case Direction.CROSS_RIGHT_DOWN -> new Node[]{getNode(row, col + 1), getNode(row + 1, col)};
            case Direction.CROSS_DOWN_LEFT -> new Node[]{getNode(row + 1, col), getNode(row, col - 1)};
            default -> new Node[]{};
        };
    }

    private int getIndex(Node node, boolean isRow) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == node) {
                    return isRow ? i : j;
                }
            }
        }
        return -1;
    }

    private int getRow(Node node) {
        return getIndex(node, true);
    }

    private int getColumn(Node node) {
        return getIndex(node, false);
    }

}

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
                    case UP:
                        setCrossroadExitAndStart(i, j, i + 1, j, i - 1, j);
                        break;
                    case LEFT:
                        setCrossroadExitAndStart(i, j, i, j + 1, i, j - 1);
                        break;
                    case RIGHT:
                        setCrossroadExitAndStart(i, j, i, j - 1, i, j + 1);
                        break;
                    case DOWN:
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
            case UP,CROSS_UP -> getNode(row - 1, col);
            case DOWN,CROSS_DOWN -> getNode(row + 1, col);
            case LEFT, CROSS_LEFT -> getNode(row, col - 1);
            case RIGHT, CROSS_RIGHT -> getNode(row, col + 1);
            default -> {
                System.out.println("Invalid direction!");
                yield null;
            }
        };
    }


    public Node getNode(int row, int col) {
        if (row >= 0 && row < map.length && col >= 0 && col < map[0].length) {
            return map[row][col];
        }
        return null;
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

    public int getRow(Node node) {
        return getIndex(node, true);
    }

    public int getColumn(Node node) {
        return getIndex(node, false);
    }

    public Node[][] getNodes(){
        return this.map;
    }

    public Node[] getEntrances(){
        return this.entrances;
    }

}

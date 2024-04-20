package model;

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
}

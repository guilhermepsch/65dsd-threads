package factories;

import enums.Direction;
import model.Map;
import model.Node;
import model.Path;

import java.util.Random;

public class PathFactory {

    private Map map;

    public PathFactory (Map map){
        this.map = map;
    }

    public Path createPath(Node currentNode) {
        Path path = new Path();
        Random random = new Random();
        Node crossRoadNode = map.getNextNode(currentNode.getDirection(), currentNode);
        path.addNode(crossRoadNode);
        currentNode = crossRoadNode;

        while (true) {
            assert currentNode != null;
            if (currentNode.isCrossroadExit()) break;
            switch (currentNode.getDirection()) {
                case CROSS_UP, CROSS_DOWN, CROSS_LEFT, CROSS_RIGHT:
                    currentNode = map.getNextNode(currentNode.getDirection(), currentNode);
                    path.addNode(currentNode);
                    break;
                case CROSS_UP_RIGHT, CROSS_UP_LEFT, CROSS_RIGHT_DOWN,
                     CROSS_DOWN_LEFT:
                    Node[] possibleNodes = getPossibleNodes(currentNode);

                    if (path.getNodes().size() >= 3) {
                        for (Node possibleNode : possibleNodes) {
                            if (!possibleNode.isCrossroadExit()) {
                                continue;
                            }
                            currentNode = possibleNode;
                        }
                    } else {
                        currentNode = possibleNodes[random.nextInt(2)];
                    }
                    path.addNode(currentNode);
                    break;
			default:
				break;
            }
        }
        return path;
    }

    private Node[] getPossibleNodes(Node currentNode) {
        int row = map.getRow(currentNode);
        int col = map.getColumn(currentNode);

        return switch (currentNode.getDirection()) {
            case CROSS_UP_RIGHT -> new Node[]{map.getNode(row - 1, col), map.getNode(row, col + 1)};
            case CROSS_UP_LEFT -> new Node[]{map.getNode(row - 1, col), map.getNode(row, col - 1)};
            case CROSS_RIGHT_DOWN -> new Node[]{map.getNode(row, col + 1), map.getNode(row + 1, col)};
            case CROSS_DOWN_LEFT -> new Node[]{map.getNode(row + 1, col), map.getNode(row, col - 1)};
            default -> new Node[]{};
        };
    }

}

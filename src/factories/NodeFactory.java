package factories;

import enums.Direction;
import model.Node;

public class NodeFactory {

    public Node createNode(Direction direction, boolean isStart, boolean isEnd) {
        return new Node(direction, isStart, isEnd);
    }
}

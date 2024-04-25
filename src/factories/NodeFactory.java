package factories;

import enums.Direction;
import model.Node;
import strategies.ExclusionStrategy;

public class NodeFactory {

    public Node createNode(Direction direction, boolean isStart, boolean isEnd, ExclusionStrategy exclusionStrategy) {
        return new Node(direction, isStart, isEnd, exclusionStrategy);
    }
}

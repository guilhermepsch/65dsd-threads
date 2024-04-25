package model;

import java.util.LinkedList;
import java.util.List;

public class Path {

    private final LinkedList<Node> nodes;

    public Path() {
        this.nodes = new LinkedList<>();
    }

    public List<Node> getNodes() {
        return new LinkedList<Node>(this.nodes);
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public Node getNextNode() {
        return nodes.pollFirst();
    }
}

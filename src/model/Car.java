package model;

import factories.PathFactory;

public class Car extends Thread {

    private Node currentNode;
    private final Map map;
    private Path currentCrossroadPath;
    private PathFactory pathFactory;
    private int sleep;

    public Car(Node currentNode, Map map, PathFactory pathFactory, int sleep) {
        this.map = map;
        this.currentNode = currentNode;
        this.pathFactory = pathFactory;
        this.sleep = sleep;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    public void run() {
        while (true) {
            Node nextNode = getNextNode();
            if (nextNode == null) {
                System.out.println(getName() + " reached the end of the road.");
                break;
            }
            moveCar(nextNode);
            try {
                Thread.sleep(this.sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Node getNextNode() {
        if (currentNode.isCrossRoadStart()) {
            currentCrossroadPath = pathFactory.createPath(currentNode);
        }

        if (currentCrossroadPath != null && !currentCrossroadPath.getNodes().isEmpty()) {
            Node nextNode = currentCrossroadPath.getNextNode();
            if (currentCrossroadPath.getNodes().isEmpty()) {
                currentCrossroadPath = null;
            }
            return nextNode;
        } else {
            return map.getNextNode(currentNode.getDirection(), currentNode);
        }
    }

    private  void moveCar(Node nextNode) {
        if (nextNode.getCar() == null) {
            currentNode.removeCar();
            nextNode.setCar(this);
            setCurrentNode(nextNode);
            System.out.println(getName() + " moved to (" + nextNode.getDirection().getValue() + ")");
        } else {
            System.out.println(getName() + " encountered another car at (" + nextNode + ")");
        }
    }
}

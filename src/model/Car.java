package model;

public class Car extends Thread {

    private Node currentNode;
    private final Map map;
    private Path currentCrossroadPath;

    public Car(Node currentNode, Map map) {
        this.map = map;
        this.currentNode = currentNode;
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
            synchronized (currentNode) {
                Node nextNode = getNextNode();

                if (nextNode == null) {
                    System.out.println(getName() + " reached the end of the road.");
                    break;
                }

                synchronized (nextNode) {
                    moveCar(nextNode);
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Node getNextNode() {
        if (currentNode.isCrossRoadStart()) {
            currentCrossroadPath = map.createPathForCrossroad(currentNode);
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

    private void moveCar(Node nextNode) {
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

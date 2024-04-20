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
                int direction = currentNode.getDirection();
                Node nextNode;

                if (currentNode.isCrossRoadStart()){
                    currentCrossroadPath = map.createPathForCrossroad(currentNode);
                }

                if (currentCrossroadPath != null && !currentCrossroadPath.getNodes().isEmpty()) {
                    nextNode = currentCrossroadPath.getNodes().getFirst();
                    currentCrossroadPath.getNodes().removeFirst();
                } else {
                    nextNode = map.getNextNode(direction, currentNode);
                }

                if (nextNode == null) {
                    System.out.println(getName() + " reached the end of the road.");
                    break;
                }

                synchronized (nextNode) {
                    if (nextNode.getCar() == null) {
                        currentNode.removeCar();
                        nextNode.setCar(this);
                        setCurrentNode(nextNode);
                        System.out.println(getName() + " moved to (" + nextNode.getDirection() + ")");
                    } else {
                        System.out.println(getName() + " encountered another car at (" + nextNode + ")");
                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
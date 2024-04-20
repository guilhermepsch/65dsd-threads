package model;

public class Car extends Thread{

    private Node currentNode;
    private final Map map;

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

                Node nextNode = getNextNode(direction);

                if (nextNode != null) {
                    synchronized (nextNode) {
                        if (nextNode.getCar() == null) {
                            currentNode.removeCar();
                            nextNode.setCar(this);
                            setCurrentNode(nextNode);
                            System.out.println(getName() + " moved to (" + nextNode + ")");
                        } else {
                            System.out.println(getName() + " encountered another car at (" + nextNode + ")");
                        }
                    }
                } else {
                    System.out.println(getName() + " reached the end of the road.");
                    break;
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private Node getNextNode(int direction) {
        switch (direction) {
            case Node.UP:
                return map.getNodeAbove(currentNode);
            case Node.RIGHT:
                return map.getNodeOnRight(currentNode);
            case Node.DOWN:
                return map.getNodeBelow(currentNode);
            case Node.LEFT:
                return map.getNodeOnLeft(currentNode);
            default:
                System.out.println("Invalid direction!");
                return null;
        }
    }


}
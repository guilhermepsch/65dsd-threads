package model;

import factories.PathFactory;
import view.MapDisplay;

import java.util.ArrayList;
import java.util.Random;

public class Car extends Thread {

    private Node currentNode;
    private final Map map;
    private Path currentCrossroadPath;
    private final PathFactory pathFactory;
    private final int sleep;
    private final MapDisplay mapDisplay;

    public Car(Node currentNode, Map map, PathFactory pathFactory, int sleep, MapDisplay mapDisplay) {
        this.currentNode = currentNode;
        this.map = map;
        this.pathFactory = pathFactory;
        this.sleep = sleep;
        this.mapDisplay = mapDisplay;
    }

    @Override
    public void run() {
        while (true) {
             try {
                Node nextNode = getNextNode();
                if (nextNode == null) {
                    System.out.println(this.getName() + " reached the end of the road.");
                    currentNode.removeCar();
                    mapDisplay.removeCar(this);
                    mapDisplay.repaint();
                    break;
                }
                Node previousnode = currentNode;
                moveCar(nextNode);
                if ((currentCrossroadPath != null || currentNode.isCrossroadExit() || previousnode.isCrossroadExit()) && !previousnode.isCrossRoadStart()) {
                    System.out.println(this.getName() + "released node " + previousnode.getDirection().getValue());
                    previousnode.exitCriticalRegion();
                }
                Thread.sleep(this.sleep);
            } catch (InterruptedException e) {
                System.err.println("Car " + this.getName() + " thread interrupted: " + e.getMessage());
                break;
            }
        }
    }

    private Node getNextNode() {
        if (currentNode.isCrossRoadStart()) {
            currentCrossroadPath = pathFactory.createPath(currentNode);
            while (true) {
                boolean acquisitionResult = this.attemptPathAcquisition();
                if (acquisitionResult) {
                    break;
                } else {
                    try {
                        Thread.sleep(new Random().nextInt(0,1500));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(this.getName() + ": Interrupted while waiting to acquire path", e);
                    }
                }
            }
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
            System.out.println(this.getName() + " moved to (" + currentNode.getDirection().getValue() + ")");
            mapDisplay.repaint();
        } else {
            System.out.println(this.getName() + " encountered another car at (" + nextNode.getDirection().getValue() + ")");
        }
    }

    private boolean attemptPathAcquisition() {
        System.out.println(this.getName() + " attempted to acquire path");
        ArrayList<Node> acquiredNodes = new ArrayList<>();
        for (Node node : currentCrossroadPath.getNodes()) {
            boolean acquired = node.tryEnterCriticalRegion();
            if (acquired) {
                System.out.println(this.getName() + "acquired node " + node.getDirection().getValue());
                acquiredNodes.add(node);
            } else {
                System.out.println(this.getName() + " failed to acquire path, releasing acquired nodes...");
                releasePathAcquisition(acquiredNodes);
                return false;
            }
        }
        System.out.println(this.getName() + " acquired path successfully");
        return true;
    }

    private void releasePathAcquisition(ArrayList<Node> acquiredNodes) {
        for (Node node : acquiredNodes) {
            node.exitCriticalRegion();
        }
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    private void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

}

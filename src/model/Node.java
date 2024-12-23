package model;

import enums.Direction;
import strategies.ExclusionStrategy;

public class Node {
    private final Direction direction;
    private final boolean isStart;
    private final boolean isEnd;
    private boolean isCrossroadExit;
    private boolean isCrossRoadStart;
    private Car car;
    private final ExclusionStrategy exclusionStrategy;

    public Node(Direction direction, boolean isStart, boolean isEnd, ExclusionStrategy exclusionStrategy) {
        this.direction = direction;
        this.isStart = isStart;
        this.isEnd = isEnd;
        this.car = null;
        this.exclusionStrategy = exclusionStrategy;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isCrossRoad() {
        return direction.isCrossRoad();
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean isCrossroadExit() {
        return isCrossroadExit;
    }

    public void setCrossroadExit(boolean crossroadExit) {
        isCrossroadExit = crossroadExit;
    }

    public boolean isCrossRoadStart() {
        return isCrossRoadStart;
    }

    public void setCrossRoadStart(boolean crossRoadStart) {
        isCrossRoadStart = crossRoadStart;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void removeCar() {
        this.car = null;
    }

    public void enterCriticalRegion() {
        exclusionStrategy.acquire();
    }

    public void exitCriticalRegion() {
        exclusionStrategy.release();
    }

    public boolean tryEnterCriticalRegion() {
        return exclusionStrategy.tryAcquire();
    }
}
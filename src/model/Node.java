package model;

import java.util.List;

public class Node {
    public static final int NONE = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;
    public static final int CROSS_UP = 5;
    public static final int CROSS_RIGHT = 6;
    public static final int CROSS_DOWN = 7;
    public static final int CROSS_LEFT = 8;
    public static final int CROSS_UP_RIGHT = 9;
    public static final int CROSS_UP_LEFT = 10;
    public static final int CROSS_RIGHT_DOWN = 11;
    public static final int CROSS_DOWN_LEFT = 12;

    private final int direction;
    private final boolean isStart;
    private final boolean isEnd;
    private boolean isCrossroadExit;
    private boolean isCrossRoadStart;
    private Car car;

    public Node(int direction, boolean isStart, boolean isEnd) {
        this.direction = direction;
        this.isStart = isStart;
        this.isEnd = isEnd;
        this.car = null;
    }

    public synchronized int getDirection() {
        return direction;
    }

    public synchronized boolean isCrossRoad() {
        return direction >= CROSS_UP && direction <= CROSS_DOWN_LEFT;
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

    public synchronized Car getCar() {
        return car;
    }

    public synchronized void setCar(Car car) {
        this.car = car;
    }

    public synchronized void removeCar() {
        this.car = null;
    }
}

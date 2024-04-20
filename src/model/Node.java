package model;

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
    private Car car; // Attribute to represent the car occupying the node

    public Node(int direction, boolean isStart, boolean isEnd) {
        this.direction = direction;
        this.isStart = isStart;
        this.isEnd = isEnd;
        this.car = null; // Initially, no car occupies the node
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

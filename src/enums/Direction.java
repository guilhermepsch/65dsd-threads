package enums;

public enum Direction {
    NONE(0),
    UP(1),
    RIGHT(2),
    DOWN(3),
    LEFT(4),
    CROSS_UP(5),
    CROSS_RIGHT(6),
    CROSS_DOWN(7),
    CROSS_LEFT(8),
    CROSS_UP_RIGHT(9),
    CROSS_UP_LEFT(10),
    CROSS_RIGHT_DOWN(11),
    CROSS_DOWN_LEFT(12);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Direction fromValue(int value) {
        for (Direction direction : Direction.values()) {
            if (direction.value == value) {
                return direction;
            }
        }
        throw new IllegalArgumentException("Invalid direction value: " + value);
    }

    public boolean isCrossRoad() {
        return this == CROSS_UP || this == CROSS_RIGHT || this == CROSS_DOWN || this == CROSS_LEFT ||
                this == CROSS_UP_RIGHT || this == CROSS_UP_LEFT || this == CROSS_RIGHT_DOWN || this == CROSS_DOWN_LEFT;
    }
}

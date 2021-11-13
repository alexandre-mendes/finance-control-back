package br.com.financecontrol.enums;

import java.util.Arrays;

public enum Month {
    NO_MONTH(0),
    JANUARY(1),
    FEBRUARY(2),
    MARCH(3),
    APRIL(4),
    MAY(5),
    JUNE(6),
    JULY(7),
    AUGUST(8),
    SEPTEMBER(9),
    OCTOBER(10),
    NOVEMBER(11),
    DECEMBER(12);

    private int value;

    Month(int value) {}

    public int getValue() {
        return value;
    }

    public static Month valueToEnum(int value) {
       return Arrays.stream(Month.values()).filter(m -> m.getValue() == value).findFirst().orElse(null);
    }
}

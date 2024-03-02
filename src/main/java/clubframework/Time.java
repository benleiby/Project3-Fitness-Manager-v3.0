package clubframework;

/**
 * Class times cannot be altered.
 * This constant contains the possible HOUR and MINUTE of fitness classes.
 * @author Matteus Coste
 */
public enum Time {

    MORNING (9, 30),
    AFTERNOON (14, 0),
    EVENING (18, 30);

    private final int HOUR;
    private final int MINUTE;

    /**
     * Overloaded constructor. Assigns additional constant values.
     * @param hour Class start hour.
     * @param minute Class start minute.
     */
    Time(int hour, int minute) {

        this.HOUR = hour;
        this.MINUTE = minute;

    }

    public int getHour() {
        return HOUR;
    }

    public int getMinute() {
        return MINUTE;
    }
}
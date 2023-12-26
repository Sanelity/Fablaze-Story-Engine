package lv.sanelite.initium.util;

/**
 * <h3>Tick to Time converter enumeration class<h3/>
 * <p>Uses known time units and contains tick value for each unit.</p>
 * <li>Tick = 1 tick</li>
 * <li>Second = 20 ticks</li>
 * <li>Minute = 1200 ticks</li>
 * <li>Hour = 72000 ticks</li>
 */

public enum SaneTick {
    TICK(1),
    SECOND(20),
    MINUTE(1200),
    HOUR(72000);

    private final int time;

    SaneTick(int ticks){
        this.time = ticks;
    }
    private int getTick(){
        return this.time;
    }

    /**
     * @param unit Enum, which presents time value in ticks
     * @param multiplier float variable, that multiplies unit
     * @return Actual time in ticks. For example: SECOND * 15
     */
    public static int setTime(SaneTick unit, float multiplier){
        return (int)(unit.getTick() * multiplier);
    }


}

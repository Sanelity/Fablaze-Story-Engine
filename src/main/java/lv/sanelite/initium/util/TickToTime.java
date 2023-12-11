package lv.sanelite.initium.util;

public enum TickToTime {
    TICK(1),
    SECOND(20),
    MINUTE(1200),
    HOUR(72000);

    public final int time;

    TickToTime(int ticks){
        this.time = ticks;
    }
}

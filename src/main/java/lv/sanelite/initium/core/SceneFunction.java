package lv.sanelite.initium.core;

import lv.sanelite.initium.event.ClientEvents;
import lv.sanelite.initium.external.sequencer.TimerThread;

public class SceneFunction {


    public static void delay(int time){
        new TimerThread((long)time * 50).start();
    }

    public static boolean delaySpecial(int time){
        ///TODO - Delay logic that can't prevent actions
        return false;
    }
    public enum Time {
        TICK(1),
        SECOND(20),
        MINUTE(1200),
        HOUR(72000);

        private final int time;

        Time(int ticks){
            this.time = ticks;
        }
        private int getTick(){
            return this.time;
        }

        public static int set(Time unit, float multiplier){
            return (int)(unit.getTick() * multiplier);
        }
    }

}

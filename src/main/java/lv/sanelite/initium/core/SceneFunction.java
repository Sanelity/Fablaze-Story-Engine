package lv.sanelite.initium.core;

import lv.sanelite.initium.external.sequencer.TimerThread;
import net.minecraft.server.level.ServerLevel;

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
    public static void command(String minecraft_command, ServerLevel server){
        server.getServer().getCommands().performPrefixedCommand(
                server.getServer().createCommandSourceStack(),
                minecraft_command
        );
    }


}

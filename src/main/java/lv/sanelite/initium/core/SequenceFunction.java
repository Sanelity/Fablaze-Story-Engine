package lv.sanelite.initium.core;

import lv.sanelite.initium.event.ClientEvents;

public class SequenceFunction {

    public static boolean delay(int time){
        int start = ClientEvents.ClientForgeEvents.tick;

        ///TODO - Delay logic that prevents any script action
        return ClientEvents.ClientForgeEvents.tick == start + time;
    }
    public static boolean delaySpecial(int time){
        ///TODO - Delay logic that can't prevent actions
        return false;
    }
}

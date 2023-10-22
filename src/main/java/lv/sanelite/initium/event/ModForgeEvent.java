package lv.sanelite.initium.event;

import lv.sanelite.initium.entity.custom.NPCMapper;
import net.minecraftforge.event.level.LevelEvent;

public class ModForgeEvent {
    public static void onUnload(LevelEvent.Unload e){
        NPCMapper.actorMap.clear();
    }
}

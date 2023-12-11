package lv.sanelite.initium.event;

import lv.sanelite.initium.entity.dataset.NPCMapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;

public class ModForgeEvent {
    public static void onUnload(LevelEvent.Unload e){
        NPCMapper.actorMap.clear();
    }

}

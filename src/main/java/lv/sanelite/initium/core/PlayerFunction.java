package lv.sanelite.initium.core;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PlayerFunction {
    private final ServerPlayer player;
    public PlayerFunction(ServerPlayer player){
        this.player = player;
    }

    public Entity getPlayer(){
        return (Entity) this.player;
    }

}

package lv.sanelite.initium.entity.goal;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class MoveToGoal extends Goal {
    private final PathfinderMob mob;
    private final double x;
    private final double y;
    private final double z;
    private final double speed;
    private int ticking;

    public MoveToGoal(PathfinderMob mob, Vec3 direction, double speed){
        this.mob = mob;
        this.x = direction.x();
        this.y = direction.y();
        this.z = direction.z();
        this.speed = speed;
        this.ticking = 0;
    }

    private void move(){
        this.mob.getNavigation().moveTo(this.x, this.y, this.z, this.speed);
    }

    @Override
    public void tick() {
        move();
        if(ticking % 20 == 0 && Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendSystemMessage(Component.literal
                    ("Current cords.: " + mob.getBlockX() + " " + mob.getBlockY() + " " + mob.getBlockZ())
                    .withStyle(ChatFormatting.RED));
        }
        ticking++;
    }

    @Override
    public boolean canUse() {
        return true;
    }
}

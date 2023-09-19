package lv.sanelite.initium.entity.goal;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class MoveToGoal extends Goal {
    private final PathfinderMob mob;
    public static String name;
    private static double x;
    private static double y;
    private static double z;
    private static double speed;
    private int ticking;
    private boolean spawncheck = true;

    public MoveToGoal(PathfinderMob mob, Vec3 direction, double movespeed, String mobName){
        this.mob = mob;
        name = mobName;
        speed = movespeed;
        x = direction.x();
        y = direction.y();
        z = direction.z();
        this.ticking = 0;
    }

    public static void changeDirection(Vec3 direction, String mobName){
        if(name.equals(mobName)){
            x = direction.x;
            y = direction.y;
            z = direction.z;
        }

    }

    private void spawnpoint(Vec3 direction){                                 //Spawnpoint coordinates
        if (direction.equals(Vec3.ZERO)){
            this.x = mob.xOld;
            this.y = mob.yOld;
            this.z = mob.zOld;
        }
        spawncheck = false;
    };

    private void move(){
        if(mob.tickCount < 20 && mob.xo == 0 && mob.yo == 0 && mob.zo == 0){
            return;
        }
        if(spawncheck) spawnpoint(new Vec3(x,y,z));
        this.mob.getNavigation().moveTo(this.x, this.y, this.z, this.speed);
    }

    @Override
    public void tick() {
        move();
        if(mob.tickCount % 20 == 0 && Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendSystemMessage(Component.literal
                    ("Current cords.: " + mob.getBlockX() + " " + mob.getBlockY() + " " + mob.getBlockZ())
                    .withStyle(ChatFormatting.RED));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal
                            ("Goal target: " + x + " " + y + " " + z)
                    .withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public boolean canUse() {
        return true;
    }
}

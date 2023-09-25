package lv.sanelite.initium.entity.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class MoveToGoal extends Goal {
    private final PathfinderMob mob;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double speed = 0.5d;
    private boolean spawncheck = true;
    private boolean active = false;

    public MoveToGoal(PathfinderMob mob, Vec3 direction, Double speed){
        this.mob = mob;
        this.speed = speed;
        start(direction);
    }

    @Override
    public void stop() {
        super.stop();
    }

    public void start(Vec3 direction){
        super.start();
        this.x = direction.x;
        this.y = direction.y;
        this.z = direction.z;
        this.active = true;
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
        super.tick();
        if(active) move();
    }

    @Override
    public boolean canUse() {
        return true;
    }
}

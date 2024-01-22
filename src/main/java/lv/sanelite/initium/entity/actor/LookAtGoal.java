package lv.sanelite.initium.entity.actor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.Vec3;

public class LookAtGoal extends Goal {

    private Entity lookAt = null;
    private Vec3 lookPos = null;
    private Class<? extends LivingEntity> lookEntityType = null;
    private String typeResource = null;
    private final AzureNPC actor;
    private final TargetingConditions lookAtContext;

    private String savedUUID = null;
    private boolean initSearch = false;

    public LookAtGoal(AzureNPC actor){

        this.actor = actor;
        this.lookAtContext = TargetingConditions.forNonCombat().range(7.5D);
    }

    @Override
    public boolean canUse() {
        if(initSearch){
            searchByUUID();
        }if(lookEntityType != null){
            this.lookAt = this.actor.level.getNearestEntity(this.actor.level.getEntitiesOfClass(this.lookEntityType, this.actor.getBoundingBox().inflate(7.5D, 3.0D, 7.5D), (func) -> true)
                    ,lookAtContext,this.actor, this.actor.getX(), this.actor.getEyeY(), this.actor.getZ());
        } else if (this.typeResource != null) {
            setLookType(new ResourceLocation(this.typeResource));
        }
        return lookAt != null || lookPos != null;
    }
    public void tick() {
        if (this.lookAt != null && lookAt.isAlive() ) {
            this.actor.getLookControl().setLookAt(lookAt);
        } else if (this.lookPos != null) {
            this.actor.getLookControl().setLookAt(lookPos);
        }
    }
    public void reset(){
        this.lookAt = null;
        this.lookPos = null;
        this.lookEntityType = null;
        this.typeResource = null;
    }

    public void setLookAt(Entity target){
        this.lookPos = null;
        this.lookEntityType = null;
        this.typeResource = null;
        this.lookAt = target;
    }
    public Entity getLookAt(){
        return this.lookAt;
    }

    public void setLookPos(Vec3 pos){
        this.lookEntityType = null;
        this.typeResource = null;
        this.lookAt = null;
        this.lookPos = pos;
    }
    public Vec3 getLookPos(){
        return this.lookPos;
    }

    public void setLookType(ResourceLocation target) {
        this.lookAt = null;
        this.lookPos = null;

        CompoundTag tag = new CompoundTag();
        tag.putString("id",target.toString());
        Entity entity = EntityType.loadEntityRecursive(tag,this.actor.getLevel(), summoned -> {
            summoned.discard();
            return summoned;
        });

        if(entity != null && entity.getType().getCategory() != MobCategory.MISC){
            this.typeResource = target.toString();
            this.lookEntityType = (Class<? extends LivingEntity>) entity.getClass();
        };

    }
    public String getLookType(){
        return this.typeResource;
    }

    public void searchByUUID(){
        for(Entity entity : this.actor.level.getEntitiesOfClass(LivingEntity.class, this.actor.getBoundingBox().inflate(10D,3D, 10D))){
            if (entity.getStringUUID().equals(this.savedUUID)){
                setLookAt(entity);
                initSearch = false;
            }
        }
    }

    public void loadEntityUUID(String UUID){
        this.initSearch = true;
        this.savedUUID = UUID;
    }


}

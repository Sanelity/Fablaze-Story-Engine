package lv.sanelite.initium.entity.custom;

import lv.sanelite.initium.entity.goal.MoveToGoal;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings({ "unchecked", "rawtypes"})
public class SentryNPC extends Monster implements IAnimatable {
    //TODO - Abstract entity actor type
    //TODO - Target Memory, Spawnpoint memory??
    //TODO - Skin & Model selector
    //TODO - Universal goals and behavior
    //TODO - NBT tagger and changable properties (Invulnerable, Damagable (With lower health limit))

    public static double x = 0.0d;
    public static double y = 0.0d;
    public static double z = 0.0d;
    public static String name = "none";
    private final AnimationFactory factory = new AnimationFactory(this);

    public SentryNPC(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }


    @Override
    protected void registerGoals(){
        this.goalSelector.addGoal(1, new MoveToGoal(this, new Vec3(x,y,z), 0.5d, name));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this,1f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, LivingEntity.class, 5f, 1f, false));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }


    public void changeName(String rename){
        name = new String(rename);
    }

    public void changeMovePoint(Vec3 argument){
        x = argument.x;
        y = argument.y;
        z = argument.z;
    }


    public static AttributeSupplier setAttributes (){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.JUMP_STRENGTH, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.65f).build();
    }
    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event){
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("sentry.swipe.animation", false));
            this.swinging = false;
        }

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("sentry.walk.animation", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("sentry.idle.animation", true));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent event){

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this,"controller",0,this::predicate));
        data.addAnimationController(new AnimationController(this,"attackController",0,this::attackPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected void playStepSound(BlockPos pos, BlockState state){
        this.playSound(SoundEvents.AXOLOTL_ATTACK, 0.15f, 1.0f);
    }
    protected SoundEvent getAmbientSound(){ return SoundEvents.CAT_STRAY_AMBIENT;}
    protected SoundEvent getHurtSound(DamageSource damageSourceIn){ return SoundEvents.CAT_HURT;}
    protected SoundEvent getDeathSound(){return SoundEvents.CAT_DEATH;}
    protected float getSoundVolume(){return 0.25f;}

}

package lv.sanelite.initium.entity.custom;

import lv.sanelite.initium.entity.goal.MoveToGoal;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
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
public class ActorNPC extends Monster implements IAnimatable {
    //Abstract entity actor type - DONE
    //MAPPING - CurrentTarget - DONE
    //Change everything to Non-static - DONE

    //TODO - Target Memory, Spawnpoint memory?? MEMORY!!!
    //TODO - Skin & Model selector
    //TODO - Universal goals and behavior - PROBLEMATIC!!!
    //TODO - NBT tagger and changable properties (Invulnerable, Damagable (With lower health limit))

    //Init variables
    public Vec3 TARGET = new Vec3(0d,0d,0d);
    public double SPEED;

    public boolean ACTIVE = true;

    public String key;
    public boolean tracking = false;
    public boolean spawncheck = true;


    private final AnimationFactory factory = new AnimationFactory(this);
    //Constructor
    public ActorNPC(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        if(!level.isClientSide()){
            NPCMapper.addListed(String.valueOf(super.getId()),this);
        }
        this.key = String.valueOf(super.getId());
    }

    //Moving logic
    public void nextTarget(Vec3 direction, double speed, boolean bypass){
        if(bypass){
            TARGET = direction; this.SPEED = speed;
        }else if(direction.equals(Vec3.ZERO)){
            TARGET = new Vec3(xOld, yOld, zOld);
            spawncheck = false;
        }else{
            TARGET = direction; this.SPEED = speed;
        }
    }
    public void moveToTarget(){
        this.getNavigation().moveTo(TARGET.x, TARGET.y, TARGET.z, SPEED);
    }

    public void reachLogic(){
        if(ACTIVE && (  new Vec3(xo,yo,zo).subtract(TARGET).length() <=  1
                    ||  new Vec3(xo,yo,zo).subtract(TARGET).length() >= -1  )){
            ACTIVE = false;
        }else if(   new Vec3(xo,yo,zo).subtract(TARGET).length() >=  5
                ||  new Vec3(xo,yo,zo).subtract(TARGET).length() <= -5  ){
            ACTIVE = true;
        }
    }



    //Behavior
    @Override
    protected void registerGoals(){
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this,1f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 5f, 1f, false));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    //Animation
    public static AttributeSupplier setAttributes (){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.JUMP_STRENGTH, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.28f).build();
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

    //Interaction
    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(!tracking && !level.isClientSide() && hand == InteractionHand.MAIN_HAND){

            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Hello! My key is: " + super.getId()));

            tracking = true;
        }else{
            if(tracking && !level.isClientSide() && hand == InteractionHand.MAIN_HAND){
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Tracking turned off"));
                tracking = false;
            }
        }

        return InteractionResult.PASS;
    }

    //Tick logic
    @Override
    public void tick(){
        super.tick();
        if(this.tickCount % 20 == 0 && Minecraft.getInstance().player != null && tracking){

            tracker();
        }
        if(!isAlive() && !level.isClientSide()){
            NPCMapper.delListed(this.key);
        }
        if(spawncheck && tickCount <= 5){
            nextTarget(this.TARGET, 1d, false);
        }
        if(tickCount % 5 == 0)reachLogic();
        if(ACTIVE && tickCount % 5 == 0) moveToTarget();



    }

    //Debugging
    public void tracker(){
        Minecraft.getInstance().player.sendSystemMessage(Component.literal
                        ("Current cords.: " + this.getBlockX() + " " + this.getBlockY() + " " + this.getBlockZ() + " | Key: " + super.getId() + " | " + this.key)
                .withStyle(ChatFormatting.RED));
        Minecraft.getInstance().player.sendSystemMessage(Component.literal
                        ("Goal target: " + TARGET.x + " " + TARGET.y + " " + TARGET.z)
                .withStyle(ChatFormatting.GOLD));
    }

    //Sounds
    protected void playStepSound(BlockPos pos, BlockState state){
        this.playSound(SoundEvents.AXOLOTL_ATTACK, 0.15f, 1.0f);
    }
    protected SoundEvent getAmbientSound(){ return SoundEvents.CAT_STRAY_AMBIENT;}
    protected SoundEvent getHurtSound(DamageSource damageSourceIn){ return SoundEvents.CAT_HURT;}
    protected SoundEvent getDeathSound(){return SoundEvents.CAT_DEATH;}
    protected float getSoundVolume(){return 0.25f;}
}

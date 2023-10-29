//package lv.sanelite.initium.entity.custom;
//
//import net.minecraft.ChatFormatting;
//import net.minecraft.client.Minecraft;
//import net.minecraft.core.BlockPos;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.Style;
//import net.minecraft.network.chat.TextColor;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.PathfinderMob;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.goal.*;
//import net.minecraft.world.entity.monster.Monster;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.Vec3;
//import net.minecraftforge.registries.ForgeRegistries;
//import software.bernie.geckolib3.core.AnimationState;
//import software.bernie.geckolib3.core.IAnimatable;
//import software.bernie.geckolib3.core.PlayState;
//import software.bernie.geckolib3.core.builder.AnimationBuilder;
//import software.bernie.geckolib3.core.controller.AnimationController;
//import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
//import software.bernie.geckolib3.core.manager.AnimationData;
//import software.bernie.geckolib3.core.manager.AnimationFactory;
//import software.bernie.geckolib3.util.GeckoLibUtil;
//
//import java.util.Objects;
//
////Abstract entity actor type - DONE
////MAPPING - CurrentTarget - DONE
////Change everything to Non-static - DONE
////Memory - DONE
////Complete Data Saving/Loading - DONE
//
////TODO - Skin & Model selector
////TODO - Universal goals and behavior - PROBLEMATIC!!!
////TODO - NBT tagger and changable properties (Invulnerable, Damagable (With lower health limit))
//
//@SuppressWarnings({ "unchecked", "rawtypes"})
//public class ActorNPC extends PathfinderMob implements IAnimatable {
//    //Debug - Indetermined
//    public boolean tracking = false;
//
//    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
//
//    //Constructor
//    public ActorNPC(EntityType<? extends ActorNPC> type, Level level) {
//        super(type, level);
//        if(!level.isClientSide()){
//                NPCMapper.addListed(KEY,this);
//        }
//    }
//    public static AttributeSupplier setAttributes (){
//        return Monster.createMonsterAttributes()
//                .add(Attributes.MAX_HEALTH, 20)
//                .add(Attributes.ATTACK_DAMAGE, 2.0f)
//                .add(Attributes.ATTACK_SPEED, 1.0f)
//                .add(Attributes.JUMP_STRENGTH, 1.0f)
//                .add(Attributes.MOVEMENT_SPEED, 0.28f).build();
//    }
//
//    //Animation
//    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event){
//        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
//            event.getController().markNeedsReload();
//
//
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("sentry.swipe.animation"));
//            this.swinging = false;
//        }
//
//        if(event.isMoving()) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("sentry.walk.animation"));
//            return PlayState.CONTINUE;
//        }
//        event.getController().setAnimation(new AnimationBuilder().addAnimation("sentry.idle.animation"));
//        return PlayState.CONTINUE;
//    }
//    private PlayState attackPredicate(AnimationEvent event){
//
//        return PlayState.CONTINUE;
//    }
//
//    @Override
//    public void registerControllers(AnimationData data) {
//        data.addAnimationController(new AnimationController(this,"controller",0,this::predicate));
//        data.addAnimationController(new AnimationController(this,"attackController",0,this::attackPredicate));
//    }
//
//    @Override
//    public AnimationFactory getFactory() {
//        return factory;
//    }
//
//    //Data
//
//    String KEY = String.valueOf(this.getId());
//    Vec3 TARGET = new Vec3(0,0,0);
//    Double SPEED = 1.0d;
//    Double ENTER = 0.5d;
//    Double LEAVE = 1.5d;
//
//    Boolean ACTIVE = true;
//    Boolean BYPASS = false;
//    Boolean INITIALIZED = false;
//
//    Style COLOR = Style.EMPTY.withColor(16711842).withInsertion("st");
//
//    @Override
//    public void load(CompoundTag compoundTag) {
//        super.load(compoundTag);
//        this.KEY = compoundTag.getString("key");
//        this.SPEED = compoundTag.getDouble("speed");
//        this.ENTER = compoundTag.getDouble("enter");
//        this.LEAVE = compoundTag.getDouble("leave");
//
//        this.INITIALIZED = compoundTag.getBoolean("initialized");
//        this.BYPASS = compoundTag.getBoolean("bypass");
//
//        this.TARGET = new Vec3(
//                compoundTag.getDouble("x"),
//                compoundTag.getDouble("y"),
//                compoundTag.getDouble("z")
//        );
//    }
//
//    @Override
//    public boolean save(CompoundTag compoundTag) {
//        compoundTag.putBoolean("initialized", this.INITIALIZED);
//        compoundTag.putBoolean("bypass", this.BYPASS);
//
//        compoundTag.putString("key", this.KEY);
//        compoundTag.putDouble("speed", this.SPEED);
//        compoundTag.putDouble("enter", this.ENTER);
//        compoundTag.putDouble("leave", this.LEAVE);
//
//        compoundTag.putDouble("x",this.TARGET.x);
//        compoundTag.putDouble("y",this.TARGET.y);
//        compoundTag.putDouble("z",this.TARGET.z);
//
//        return super.save(compoundTag);
//    }
//        //Complex
//
//        //Single //TODO rewrite
//    public void setKey(String name){
//        this.KEY = name;
//    }
//
//    public void setActivity(boolean state){
//        this.ACTIVE = state;
//    }
//
//    public void setLeaveRadius(double rad){
//        this.LEAVE = rad;
//    }
//    public void setEnterRadius(double rad) {
//        this.ENTER = rad;
//    }
//
//    public boolean isInitialized(){
//
//
//        return this.getPersistentData().getBoolean("initialized");
//
//    }
//
//    //Interaction
//    @Override
//    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
//        if(!tracking && !level.isClientSide() && hand == InteractionHand.MAIN_HAND){
//
//            Minecraft.getInstance().player.sendSystemMessage(Component.literal("[???]").setStyle(COLOR)
//                    .append(Component.literal(" Hello! My name is " + this.KEY).withStyle(ChatFormatting.WHITE)));
//            Minecraft.getInstance().player.sendSystemMessage(Component.literal( "[" + this.KEY + "]").setStyle(COLOR)
//                    .append(Component.literal(" I am an Actor, and waiting for my script!").withStyle(ChatFormatting.WHITE)));
//
//            tracking = true;
//        }else{
//            if(tracking && !level.isClientSide() && hand == InteractionHand.MAIN_HAND){
//                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Tracking turned off"));
//                tracking = false;
//            }
//        }
//        return InteractionResult.PASS;
//    }
//
//    //Moving logic
//    public void reachLogic(){
//        if(ACTIVE && (Math.abs(new Vec3(xo,yo,zo).subtract(TARGET).length()) <= ENTER)){
//            setActivity(false);
//        }else if(Math.abs(new Vec3(xo,yo,zo).subtract(TARGET).length()) >= LEAVE){
//            setActivity(true);
//            moveToTarget();
//        }
//    }
//    public void setCurrentAsTarget(){
//        TARGET = new Vec3(xOld, yOld, zOld);
//    }
//    public void newTarget(Vec3 direction, double speed, boolean bypass){
//        setActivity(true);
//        TARGET = direction; this.SPEED = speed; this.BYPASS = bypass;
//    }
//    public void moveToTarget(){
//        this.getNavigation().moveTo(TARGET.x, TARGET.y, TARGET.z, SPEED);
//    }
//
//    private int initCounter = 0;
//    //Tick logic
//    @Override
//    public void tick(){
//        super.tick();
//    }
//
//    //Debugging
//    public void tracker(){
//        Minecraft.getInstance().player.sendSystemMessage(Component.literal
//                        ("Current: " + this.getBlockX() + " " + this.getBlockY() + " " + this.getBlockZ() + " | " + this.KEY)
//                .withStyle(ChatFormatting.RED));
//        Minecraft.getInstance().player.sendSystemMessage(Component.literal
//                        ("Target: " + Math.round(TARGET.x) + " " + Math.round(TARGET.y) + " " + Math.round(TARGET.z))
//                .withStyle(ChatFormatting.GOLD));
//    }
//
//    //Behavior
//    @Override
//    protected void registerGoals(){
//        removeFreeWill();
//        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 5f, 1f, false));
//        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
//    }
//
//
//
//
//    @Override
//    public void aiStep() {
//        super.aiStep();
//
//        if(initCounter < 10) initCounter++;
//        else{
//            if(!isInitialized()){
//                if(!level.isClientSide() && !String.valueOf(this.getId()).equals(this.KEY)){
//                    NPCMapper.rename(this.getId(), this.KEY);
//                }else INITIALIZED = true;
//
//            }
//            if(!BYPASS && TARGET.equals(Vec3.ZERO)) setCurrentAsTarget();
//            if(!isAlive()){
//                NPCMapper.delListed(this.KEY);
//            }
//            initCounter = 0;
//        }
//
//        if(this.tickCount % 20 == 0 ){
//            if(Minecraft.getInstance().player != null && tracking){
//                tracker();
//            }
//            reachLogic();
//        }
//    }
//
//    //Sounds
//    protected void playStepSound(BlockPos pos, BlockState state){
//        this.playSound(SoundEvents.AXOLOTL_ATTACK, 0.15f, 1.0f);
//    }
//    protected SoundEvent getAmbientSound(){ return SoundEvents.CAT_STRAY_AMBIENT;}
//    protected SoundEvent getHurtSound(DamageSource damageSourceIn){ return SoundEvents.CAT_HURT;}
//    protected SoundEvent getDeathSound(){return SoundEvents.CAT_DEATH;}
//    protected float getSoundVolume(){return 0.25f;}
//}

package lv.sanelite.initium.entity.actor;

import com.mojang.brigadier.arguments.StringArgumentType;
import lv.sanelite.initium.core.ActorFunction;
import lv.sanelite.initium.entity.dataset.Character;
import lv.sanelite.initium.entity.dataset.NPCMapper;
import lv.sanelite.initium.item.ModItems;
import lv.sanelite.initium.util.RGB;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AzureNPC extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public AzureNPC(EntityType<? extends AzureNPC> entityType, Level level) {
        super(entityType, level);
        if(!level.isClientSide()){
            NPCMapper.addActorToList(KEY,this);
        }
        this.setPersistenceRequired();
    }

                                ///  -   -   -   ANIMATIONS  -   -   -   ///

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public void setAnimations(@Nullable String action, @Nullable String emote, @Nullable String look, @Nullable String additional){
        if(action != null){
            this.entityData.set(ANIMATED_ACTION, action);
        }if(emote != null){
            this.entityData.set(ANIMATED_EMOTE, emote);
        }if(look != null){
            this.entityData.set(ANIMATED_LOOK, look);
        }if(additional != null){
            this.entityData.set(ANIMATED_ADDITIONAL, additional);
        }
    }

    AnimationController<AzureNPC> action_animator = new AnimationController<>(this, "actions", 4, event ->
            event.setAndContinue(
                    // If moving, play the walking animation
                    event.isMoving() ? RawAnimation.begin().thenLoop("walk.action"):
                            // If not moving, play the idle animation
                            RawAnimation.begin().thenLoop(this.entityData.get(ANIMATED_ACTION))
            ));
    AnimationController<AzureNPC> emote_animator = new AnimationController<>(this, "emotes", 4, event ->
            event.setAndContinue(RawAnimation.begin().thenLoop(this.entityData.get(ANIMATED_EMOTE))));
    AnimationController<AzureNPC> look_animator = new AnimationController<>(this, "look", 4, event ->
            event.setAndContinue(RawAnimation.begin().thenLoop(this.entityData.get(ANIMATED_LOOK))));
    AnimationController<AzureNPC> additional_animator = new AnimationController<>(this, "additionals", 4, event ->
            event.setAndContinue(RawAnimation.begin().thenLoop(this.entityData.get(ANIMATED_ADDITIONAL))));


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(additional_animator);
        controllers.add(action_animator);
        controllers.add(emote_animator);
        controllers.add(look_animator);
    }

        public static AttributeSupplier setAttributes (){
        return AmbientCreature.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.JUMP_STRENGTH, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.28f).build();
    }
    protected LookAtGoal look;


        @Override
    protected void registerGoals(){
        removeFreeWill();
        look = new LookAtGoal(this);
        this.goalSelector.addGoal(4, look);
//        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 5f, 1f, false));
//        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag initData) {
        if(spawnType == MobSpawnType.COMMAND){
            setCharacter(initData.getString("Character"));
            setKey(initData.getString("Key"));
            setColor(Character.getCharacter(initData.getString("Character")).getColor());


        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, spawnType, spawnGroupData, initData);
    }

    public static final EntityDataAccessor<String> ANIMATED_ADDITIONAL =
            SynchedEntityData.defineId(AzureNPC.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> ANIMATED_LOOK =
            SynchedEntityData.defineId(AzureNPC.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> ANIMATED_EMOTE =
            SynchedEntityData.defineId(AzureNPC.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> ANIMATED_ACTION =
            SynchedEntityData.defineId(AzureNPC.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<String> DATA_CHARACTER =
            SynchedEntityData.defineId(AzureNPC.class, EntityDataSerializers.STRING);

    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(ANIMATED_ADDITIONAL, "empty.additional");
        this.entityData.define(ANIMATED_LOOK, "idle.look");
        this.entityData.define(ANIMATED_EMOTE, "idle.emote");
        this.entityData.define(ANIMATED_ACTION, "idle.action");
        this.entityData.define(DATA_CHARACTER, getPersistentData().getString("character"));
    }

    ///  -   -   -   LOGICS  -   -   -   ///

        //Moving logic
    public void reachLogic(){
        if(ACTIVE && (Math.abs(new Vec3(xo,yo,zo).subtract(TARGET).length()) <= ENTER)){
            setActivity(false);
        }else if(Math.abs(new Vec3(xo,yo,zo).subtract(TARGET).length()) >= LEAVE){
            setActivity(true);
            moveToTarget();
        }
    }
    public void setCurrentAsTarget(){
        TARGET = new Vec3(xOld, yOld, zOld);
    }
    public void newTarget(Vec3 direction, double speed, boolean bypass){
        setActivity(true);
        TARGET = direction; this.SPEED = speed; this.BYPASS = bypass;
    }
    public void moveToTarget(){
        PathNavigation navigator = this.getNavigation();

        if(navigator.isInProgress() && navigator.isStuck()){
            navigator.recomputePath();
        }else navigator.moveTo(TARGET.x, TARGET.y, TARGET.z, SPEED);

    }

        //Interaction

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {

        if(player.isHolding(Items.STICK)){
            if(!tracking && !level.isClientSide() && hand == InteractionHand.MAIN_HAND){

                player.sendSystemMessage(Component.literal("[???]").setStyle(COLOR)
                        .append(Component.literal(" Hello! My name is " + this.KEY).withStyle(ChatFormatting.WHITE)));
                player.sendSystemMessage(Component.literal( "[" + this.KEY + "]").setStyle(COLOR)
                        .append(Component.literal(" I am an Actor, and waiting for my script!").withStyle(ChatFormatting.WHITE)));

                tracking = true;

            }else{
                if(tracking && !level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
                    player.sendSystemMessage(Component.literal("Tracking turned off"));
                    tracking = false;
                }
            }
        }else if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND && isMsgReloaded()) {
            talk(Character.getCharacter(getThisCharacter()).getPhraseTalk());
        } else if (player.isHolding(ModItems.DEL_TOOL.get())) {
            //Pososi y Fotoramki
            ActorFunction.eliminate(this);
            //Ne sosi pg
            NPCMapper.deleteActorFromList(this.KEY);
        }

        return InteractionResult.SUCCESS;
    }



    public void talk(String msg){
        if(!level.isClientSide() && Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendSystemMessage(Component.literal( "[" + this.KEY + "]").setStyle(COLOR)
                    .append(Component.literal(" " + msg).withStyle(ChatFormatting.WHITE)));
        }
        setMsgReloaded(false);
    }

        //Data

    String KEY = String.valueOf(this.getId());
    String CHARACTER = "default";
    Vec3 TARGET = new Vec3(0,0,0);

    double SPEED = 1.0d;
    double ENTER = 0.5d;
    double LEAVE = 1.5d;

    boolean ACTIVE = true;
    boolean BYPASS = false;
    boolean INITIALIZED = false;

    boolean tracking = false;

    boolean msgReloaded = true;


    Style COLOR = Style.EMPTY.withColor(RGB.color(255,255,255));

    public void setActivity(boolean state){
        this.ACTIVE = state;
    }
    public void setKey(String name){
        this.KEY = name;
    }
    public void setColor(int textcolor){
        this.COLOR = Style.EMPTY.withColor(textcolor);
    }
    public int getColor(){
        return Character.getCharacter(getThisCharacter()).getColor();
    }

    public void setMsgReloaded(boolean msgReloaded) {
        this.msgReloaded = msgReloaded;
    }
    public boolean isMsgReloaded(){
        return this.msgReloaded;
    }

    public void setLookTarget(Entity target){
        resetLook();
        look.setLookAt(target);
    }
    public void setLookAt(Vec3 pos){
        resetLook();
        look.setLookPos(pos);
    }
    public void setLookType(ResourceLocation target){
        resetLook();
        look.setLookType(target);
    }
    public void resetLook(){
        look.reset();
    }

    public String getThisCharacter(){
        return this.entityData.get(DATA_CHARACTER);
    }
    public void setCharacter(String character){
        this.CHARACTER = character;
        this.entityData.set(DATA_CHARACTER, Character.getCharacter(character).getName());
        setColor(Character.getCharacter(character).getColor());
    }


    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.CHARACTER = compoundTag.getString("character");
        this.entityData.set(DATA_CHARACTER, compoundTag.getString("character"));

        this.entityData.set(ANIMATED_ACTION, compoundTag.getString("animated_action"));
        this.entityData.set(ANIMATED_EMOTE, compoundTag.getString("animated_emote"));
        this.entityData.set(ANIMATED_LOOK, compoundTag.getString("animated_look"));
        this.entityData.set(ANIMATED_ADDITIONAL, compoundTag.getString("animated_additional"));

        this.KEY = compoundTag.getString("key");
        this.SPEED = compoundTag.getDouble("speed");
        this.ENTER = compoundTag.getDouble("enter");
        this.LEAVE = compoundTag.getDouble("leave");

        if(!compoundTag.getString("looktarget").isEmpty()){
            look.loadEntityUUID(compoundTag.getString("looktarget"));
        }

        if(compoundTag.getDouble("xlook") != 0){
            setLookAt(new Vec3(
                    compoundTag.getDouble("xlook"),
                    compoundTag.getDouble("ylook"),
                    compoundTag.getDouble("zlook")
            ));
        }

        if(!compoundTag.getString(compoundTag.getString("looktype")).isEmpty()){
            setLookType(new ResourceLocation(compoundTag.getString("looktype")));
        }



        this.INITIALIZED = compoundTag.getBoolean("initialized");
        this.BYPASS = compoundTag.getBoolean("bypass");

        setColor( compoundTag.getInt("color"));

        this.TARGET = new Vec3(
                compoundTag.getDouble("x"),
                compoundTag.getDouble("y"),
                compoundTag.getDouble("z")
        );


    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("character", this.CHARACTER);

        compoundTag.putString("animated_action", this.entityData.get(ANIMATED_ACTION));
        compoundTag.putString("animated_emote", this.entityData.get(ANIMATED_EMOTE));
        compoundTag.putString("animated_look", this.entityData.get(ANIMATED_LOOK));
        compoundTag.putString("animated_additional", this.entityData.get(ANIMATED_ADDITIONAL));

        compoundTag.putBoolean("initialized", this.INITIALIZED);
        compoundTag.putBoolean("bypass", this.BYPASS);

        compoundTag.putString("key", this.KEY);
        compoundTag.putDouble("speed", this.SPEED);
        compoundTag.putDouble("enter", this.ENTER);
        compoundTag.putDouble("leave", this.LEAVE);

        if(look.getLookType() != null) {
            compoundTag.putString("looktype", look.getLookType());
        }

        if(look.getLookAt() != null){
            compoundTag.putString("looktarget", look.getLookAt().getStringUUID());
        }

        if(look.getLookPos() != null){
            compoundTag.putDouble("xlook", look.getLookPos().x);
            compoundTag.putDouble("ylook", look.getLookPos().y);
            compoundTag.putDouble("zlook", look.getLookPos().z);
        }


        compoundTag.putInt("color", getColor());

        compoundTag.putDouble("x", this.TARGET.x);
        compoundTag.putDouble("y", this.TARGET.y);
        compoundTag.putDouble("z", this.TARGET.z);


    }


    private int initCounter = 0;
        public boolean isInitialized(){
            if(!this.INITIALIZED){
                return this.getPersistentData().getBoolean("initialized");
            }else return true;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(initCounter < 10) initCounter++;
        else{
            if(!isInitialized()){
                if(!level.isClientSide() && !String.valueOf(this.getId()).equals(this.KEY)){
                    NPCMapper.renameActorInList(this.getId(), this.KEY);
                }else INITIALIZED = true;

            }
            if(!BYPASS && TARGET.equals(Vec3.ZERO)) setCurrentAsTarget();
            if(!isAlive()){
                NPCMapper.deleteActorFromList(this.KEY);
            }
            initCounter = 0;
        }

        if(this.tickCount % 20 == 0 ){
            setMsgReloaded(true);
            if(Minecraft.getInstance().player != null && tracking){
                tracker();
            }
            reachLogic();
        }
    }

        //Debugging
    public void tracker(){
        Minecraft.getInstance().player.sendSystemMessage(Component.literal
                        ("View Target: " + look.getLookAt() + " | " + this.KEY + this.getId())
                .withStyle(ChatFormatting.RED));
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

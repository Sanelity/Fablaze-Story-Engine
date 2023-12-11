package lv.sanelite.initium.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import lv.sanelite.initium.Initium;
import lv.sanelite.initium.core.ActorFunction;
import lv.sanelite.initium.entity.ModEntityTypes;
import lv.sanelite.initium.entity.actor.AzureNPC;
import lv.sanelite.initium.event.ClientEvents;
import lv.sanelite.initium.event.ModForgeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;


public class ActorAddToolScreen extends AbstractContainerScreen<ActorAddToolMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Initium.MOD_ID,"textures/gui/example_tile_gui.png");
    //TODO - Phrase pool, Particle visual
    //TODO - Particles, Scale, Full Custom Resource selection
    //TODO - Rotation slider

    private AzureNPC preview;
    private EditBox keyBox;
    private EditBox charBox;
    private PlainTextButton pBtn;

    private Button createButton;

    private float rot;
    private float step = 1;
    private float xMouse;
    private float yMouse;

    public ActorAddToolScreen(ActorAddToolMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.inventoryLabelX = 10000;
        this.passEvents = true;

    }
    private void drawCreateButton(int x, int y, int xl, int yl){
        this.addRenderableWidget(createButton = new Button(x,y,xl,yl,Component.translatable("button.initium.create"), button -> {
            ActorFunction.createActor(charBox.getValue(), keyBox.getValue(),
                    this.minecraft.getSingleplayerServer().overworld().getLevel(), this.minecraft.player);
            this.minecraft.player.sendSystemMessage(Component.translatable("commands.summon.success", "Azure Actor<" + keyBox.getValue() + ">"));
            this.minecraft.player.closeContainer();
        }));
        createButton.active = false;
    }

    private void drawAddTool(){
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);

        preview = new AzureNPC(ModEntityTypes.AZURE.get(),this.getMinecraft().level);
        preview.setCustomNameVisible(true);

        int y = height;
        int x = width;
        minecraft.getWindow().getGuiScale();


        drawCreateButton(x - (x/5) - 10, y - 40, x/5, 20);
        this.addRenderableOnly(new PlainTextButton(10,8,x/5,8, Component.translatable("input.initium.character"),button->{},font));
        this.addRenderableWidget(charBox = new EditBox(font, 10, 20, x/5, 20, Component.translatable("input.initium.character")));
        this.addRenderableOnly(new PlainTextButton(10,48,x/5,8, Component.translatable("input.initium.actorname"),button->{},font));
        this.addRenderableWidget(keyBox = new EditBox(font, 10, 60, x/5, 20, Component.translatable("input.initium.actorname")));
        rotationMenu(0,0);
        this.addRenderableWidget(pBtn = new PlainTextButton(10, height/2,20,100, Component.literal(String.valueOf(ClientEvents.ClientForgeEvents.tick)), button ->{},font));

    }

    private void rotationMenu(int xpos, int ypos){
        int x = width / 2;
        int y = height - 40;


        this.addRenderableWidget(new Button(x + 13, y, 25, 20, Component.literal(">>"),button -> {if(step > -6) step -= 1.20f;}));
        this.addRenderableWidget(new Button(x - 12, y, 25, 20, Component.literal("=="),button -> {step = 0f;}));
        this.addRenderableWidget(new Button(x - 37, y, 25, 20, Component.literal("<<"),button -> {if(step < 6) step += 1.20f;}));
    }

    @Override
    protected void init() {
        super.init();
        drawAddTool();

    }


    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;


        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        preview.setCharacter(charBox.getValue());
        preview.setCustomName(Component.literal(keyBox.getValue()));

        renderEntityInInventory(width / 2, height - (height / 10) - 40, height / 5, (float)(x + 51) - this.xMouse, (float)(y + 75 - 50) - this.yMouse, preview);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);

        this.xMouse = (float)mouseX;
        this.yMouse = (float)mouseY;
    }

    public void renderEntityInInventory(int p_98851_, int p_98852_, int p_98853_, float p_98854_, float p_98855_, LivingEntity p_98856_) {
        float f = (float)Math.atan((double)(p_98854_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_98855_ / 40.0F));
        renderEntityInInventoryRaw(p_98851_, p_98852_, p_98853_, f, f1, p_98856_);
    }
    public void renderEntityInInventoryRaw(int p_98851_, int p_98852_, int p_98853_, float angleXComponent, float angleYComponent, LivingEntity p_98856_) {
        float f = angleXComponent;
        float f1 = angleYComponent;
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)p_98851_, (double)p_98852_, 1050.0D);
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(0.0D, 0.0D, 1000.0D);
        posestack1.scale((float)p_98853_, (float)p_98853_, (float)p_98853_);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);
        float f2 = p_98856_.yBodyRot;
        float f3 = p_98856_.getYRot();
        float f4 = p_98856_.getXRot();
        float f5 = p_98856_.yHeadRotO;
        float f6 = p_98856_.yHeadRot;
        p_98856_.yBodyRot = (180.0F + f * 20.0F)+this.rot;
        p_98856_.setYRot((180.0F + f * 40.0F)+this.rot);
        p_98856_.setXRot(-f1 * 20.0F);
        p_98856_.yHeadRot = p_98856_.getYRot();
        p_98856_.yHeadRotO = p_98856_.getYRot();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(p_98856_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
        });
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(true);
        p_98856_.yBodyRot = f2;
        p_98856_.setYRot(f3);
        p_98856_.setXRot(f4);
        p_98856_.yHeadRotO = f5;
        p_98856_.yHeadRot = f6;
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    public boolean keyPressed(int keyId, int param1, int param2) {
        if (keyId == 256) {
            this.minecraft.player.closeContainer();
        }

        if(charBox.isFocused()){
            return !this.charBox.keyPressed(keyId, param1, param2) && !this.charBox.canConsumeInput() ? super.keyPressed(keyId, param1, param2) : true;
        }else if(keyBox.isFocused()){
            return !this.keyBox.keyPressed(keyId, param1, param2) && !this.keyBox.canConsumeInput() ? super.keyPressed(keyId, param1, param2) : true;
        }
        return true;
    }


    @Override
    protected void containerTick() {
        super.containerTick();

        this.rot += step;
        createButton.active = !keyBox.getValue().equals("");


        GuiEventListener focus = getFocused();
        if(focus == charBox){
            this.charBox.tick();
        } else if (focus == keyBox) {
            this.keyBox.tick();
        }
    }

    private void createActor(){
        Player player = this.minecraft.player;
        ServerLevel serverLevel = minecraft.getSingleplayerServer().overworld().getLevel();

        CompoundTag tag = new CompoundTag();

        tag.putString("Character", charBox.getValue());
        tag.putString("Key", keyBox.getValue());

        EntityType<AzureNPC> entityType = ModEntityTypes.AZURE.get();
        entityType.spawn(serverLevel,tag,null,null, new BlockPos(player.getX(),player.getY(),player.getZ()),MobSpawnType.COMMAND,false,false);

        player.sendSystemMessage(Component.translatable("commands.summon.success", "Azure Actor( " + keyBox.getValue() + " )"));
        player.closeContainer();
    }

}
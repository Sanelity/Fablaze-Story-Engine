package lv.sanelite.initium.external.sequencer;

import lv.sanelite.initium.core.*;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class GlobalContext {

    public ActorFunction actor = new ActorFunction();
    public CameraFunction cam = new CameraFunction();
    public GUIFunction gui = new GUIFunction();
    public ParticleFunction particle = new ParticleFunction();
    public PlayerFunction player;
    public SceneFunction scene = new SceneFunction();
    public SoundFunction sound = new SoundFunction();
    public WorldFunction world = new WorldFunction();
    public Sequence sequence = new Sequence();

    public Minecraft game = Minecraft.getInstance();
    public MinecraftServer server;

    public ServerLevel level;



    public GlobalContext(ServerPlayer player, String scene){
        this.player = new PlayerFunction(player);
        this.server = player.getServer();
        this.level = this.server.overworld().getLevel();


    }
}

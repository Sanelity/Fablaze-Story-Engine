package lv.sanelite.initium.external.sequencer;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Scriptable;

import dev.latvian.mods.rhino.ScriptableObject;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.lang.reflect.Field;

public class Executor {
    static Context ctx = Context.enter();
    static Scriptable scope = ctx.initStandardObjects();
    static GlobalContext coreAPI;


    public static void runScript(ServerPlayer player, String scene) throws IllegalAccessException {
        coreAPI = new GlobalContext(player, scene);
        loadAPI(coreAPI);
        coreAPI.sequence.clear();

        try{
            ctx.evaluateString(scope, ScriptReader.readScript("act1"), null, 1, null);


        }catch (Exception e){
            player.sendSystemMessage(Component.literal(e.getMessage()));
        }finally {
            ServerLevel serverLevel = player.getServer().overworld().getLevel();
            if(serverLevel != null){
                serverLevel.getServer().getCommands().performPrefixedCommand(serverLevel.getServer().createCommandSourceStack(), "Executed successfully");
            }
        }
    }


    private static boolean loadAPI(GlobalContext API) throws IllegalAccessException {
        Field[] fields = API.getClass().getFields();
        for (Field currField : fields) {
            String funcName = currField.getName();
            Object funcValue = currField.get(coreAPI);

            ScriptableObject.putConstProperty(scope, funcName, funcValue, ctx);
        }
        return true;
    }


}

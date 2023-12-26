package lv.sanelite.initium.external.sequencer;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.storage.LevelResource;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ScriptReader {
    public static String readScript(String filename){
        if(isLocal()){
            File script = setFile(filename);
            try {
                FileInputStream reader = new FileInputStream(script);
                FileChannel channel = reader.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate((int)channel.size());
                while (channel.read(buffer) != -1) {
                    buffer.flip();
                    buffer.clear();
                }
                return StandardCharsets.UTF_8.decode(buffer).toString();

            } catch (IOException e) {
                return e.getMessage();
            }

        }
        return null;
    }

    public static void writeString(String charSequence){

    }

    public static boolean initializeWorkspace(){
        if(isLocal()){
            Path gameDir = Minecraft.getInstance().getSingleplayerServer().getWorldPath(LevelResource.ROOT).getParent();
            File location = new File(gameDir.toFile(), "script");

            if(location.exists() && location.canRead()) return true;
            else if(!location.exists()) return dirCreate(location);
        }
        return false;
    }
    private static boolean dirCreate(File location){
        try{
            return location.mkdir();
        }catch (SecurityException e){
            System.out.print(e.getMessage());
        }
        return false;
    }

    private static boolean isLocal(){
        return Minecraft.getInstance().isLocalServer();
    }

    private static File setFile(String filename){
        Path gameDir = Minecraft.getInstance().getSingleplayerServer().getWorldPath(LevelResource.ROOT).getParent();
        File location = new File(gameDir.toFile(), "script");
        File script = new File(location, filename + ".js");
        if(script.exists()) return script;
        else return null;
    }

}

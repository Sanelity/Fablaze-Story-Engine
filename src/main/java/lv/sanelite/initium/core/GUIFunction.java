package lv.sanelite.initium.core;

import net.minecraft.client.Minecraft;

public class GUIFunction {
    ///GRAPHIC USER INTERFACE related functions


    public static void guiHide(Minecraft minecraft, Boolean bool){
        minecraft.options.hideGui = bool;
    }
}

package lv.sanelite.initium.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_INITIUM = "key.category.initium.base";
    public static final String KEY_CHEAT_GAME = "key.initium.cheat";

    public static final KeyMapping CHEAT_KEY = new KeyMapping(KEY_CHEAT_GAME, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F24, KEY_CATEGORY_INITIUM);
}

package lv.sanelite.initium.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_INITIUM = "key.category.initium.base";
    public static final String KEY_DEBUG_GAME = "key.initium.debug";

    public static final KeyMapping DEBUG_KEY = new KeyMapping(KEY_DEBUG_GAME, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F24, KEY_CATEGORY_INITIUM);
}

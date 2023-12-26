package lv.sanelite.initium.core;

import lv.sanelite.initium.gui.BlackScreenHUD;
import lv.sanelite.initium.util.RGB;
import net.minecraft.client.Minecraft;

/**<h3>Graphic User Interface related functions<h3/>
 *
 */

public class GUIFunction {
    /**<h3>Full GUI Hide function<h3/>
     * <div>Hides everything! Any custom GUIs will be affected and hidden by using this function</div>
     * @param bool - sets function active or inactive
     */
    public static void guiHide(Boolean bool){
        Minecraft.getInstance().options.hideGui = bool;
    }
    public static boolean isGuiHidden(){
        return Minecraft.getInstance().options.hideGui;
    }

    public static void transitionScreen(int transition, int offset, boolean polarity, int red, int green, int blue){
        BlackScreenHUD.use(transition, offset, polarity, new RGB(red, green, blue));
    }
    public static void instantBlackScreen(boolean active){
        transitionScreen(2,0,active, 0,0,0);
    }


}

package lv.sanelite.initium.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static  final CreativeModeTab INITIUM_TAB = new CreativeModeTab("initiumtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.URANIUM.get());
        }
    };
}

package lv.sanelite.initium.screen;

import lv.sanelite.initium.Initium;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Initium.MOD_ID);

    public static final RegistryObject<MenuType<SaneliteScreenMenu>> SANELITE_SCREEN_MENU =
            registerMenuType(SaneliteScreenMenu::new, "sanelite_screen_menu");

    public static final RegistryObject<MenuType<ActorAddToolMenu>> ACTOR_ADD_TOOL_MENU =
            registerMenuType(ActorAddToolMenu::new, "actor_add_tool_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}

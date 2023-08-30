package lv.sanelite.initium.item;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.item.advanced.D6DiceItem;
import lv.sanelite.initium.rarity.ModRarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Initium.MOD_ID);

    public static final RegistryObject<Item> URANIUM =
            ITEMS.register("uranium",() -> new Item(new Item.Properties()
                .stacksTo(16)
                .tab(ModCreativeModeTab.INITIUM_TAB)
                .rarity(Rarity.RARE)
        )
    );
    public static final RegistryObject<Item> RAW_URANIUM =
            ITEMS.register("raw_uranium",() -> new Item(new Item.Properties()
                .stacksTo(64)
                .tab(ModCreativeModeTab.INITIUM_TAB)
                .rarity(Rarity.RARE)
            )
    );
    public static final RegistryObject<Item> DICED6 =
            ITEMS.register("diced6",() -> new D6DiceItem(new Item.Properties()
                    .stacksTo(1)
                    .tab(ModCreativeModeTab.INITIUM_TAB)
                    .rarity(ModRarity.MYTHICAL)
            )
    );

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }


}

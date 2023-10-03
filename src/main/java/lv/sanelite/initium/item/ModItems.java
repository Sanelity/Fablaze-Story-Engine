package lv.sanelite.initium.item;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.entity.ModEntityTypes;
import lv.sanelite.initium.item.advanced.AddActorToolItem;
import lv.sanelite.initium.item.advanced.D6DiceItem;
import lv.sanelite.initium.rarity.ModRarity;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
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
    public static final RegistryObject<Item> INFINITUM =
            ITEMS.register("infinitum",() -> new SwordItem(Tiers.NETHERITE, 10, 5f, new Item.Properties()
                    .stacksTo(1)
                    .tab(ModCreativeModeTab.INITIUM_TAB)
                    .rarity(ModRarity.MYTHICAL)
                    .durability(4096)
                    )
            );

    public static final RegistryObject<Item> SENTRY_SPAWN_EGG =
            ITEMS.register("sentry_spawn_egg",
                    () -> new ForgeSpawnEggItem(ModEntityTypes.ACTOR, 0xffffff, 0xc40c40,
                            new Item.Properties().tab(ModCreativeModeTab.INITIUM_TAB)
                                    .rarity(ModRarity.MYTHICAL)
                                    .stacksTo(64)
                    )
            );

    public static final  RegistryObject<Item> ADD_TOOL =
            ITEMS.register("add_tool",
                    () -> new AddActorToolItem(new Item.Properties()
                            .stacksTo(1)
                            .tab(ModCreativeModeTab.INITIUM_TAB)
                            .rarity(ModRarity.MYTHICAL)
                    )
            );


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }


}

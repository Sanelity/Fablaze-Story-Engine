package lv.sanelite.initium.block;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.item.ModCreativeModeTab;
import lv.sanelite.initium.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Initium.MOD_ID);

    public static final RegistryObject<Block> URANIUM_BLOCK =
            registerBlock("uranium_block", () -> new Block(BlockBehaviour
                    .Properties.of(Material.STONE)
                    .strength(6f)
                            .lightLevel(BlockState -> 15)),
                    ModCreativeModeTab.INITIUM_TAB, Rarity.EPIC);

    public static final RegistryObject<Block> URANIUM_ORE =
            registerBlock("uranium_ore", () -> new DropExperienceBlock(BlockBehaviour
                            .Properties.of(Material.STONE)
                            .strength(6f)
                            .requiresCorrectToolForDrops(), UniformInt.of(1,3)),
                    ModCreativeModeTab.INITIUM_TAB, Rarity.RARE);
    public static final RegistryObject<Block> DEEPURANIUM_ORE =
            registerBlock("deepuranium_ore", () -> new DropExperienceBlock(BlockBehaviour
                            .Properties.of(Material.STONE)
                            .strength(6f)
                            .requiresCorrectToolForDrops(), UniformInt.of(1,3)),
                    ModCreativeModeTab.INITIUM_TAB, Rarity.RARE);


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab, Rarity rareness){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab, rareness);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab, Rarity rareness) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab).rarity(rareness)));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}

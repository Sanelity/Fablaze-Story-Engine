package lv.sanelite.initium.world.feature;

import lv.sanelite.initium.Initium;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Initium.MOD_ID);

    public static final RegistryObject<PlacedFeature> URANIUM_ORE_PLACED = PLACED_FEATURES.register("uranium_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.URANIUM_ORE.getHolder().get(),
                    commonOrePlacement(7,HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80),VerticalAnchor.aboveBottom(80)))));

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier secondModifier) {
        return List.of(modifier, InSquarePlacement.spread(), secondModifier, BiomeFilter.biome());
    }
    private static List<PlacementModifier> commonOrePlacement(int i, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(i), modifier);
    }
    private static List<PlacementModifier> rareOrePlacement(int i, PlacementModifier modifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(i), modifier);
    }

    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }
}

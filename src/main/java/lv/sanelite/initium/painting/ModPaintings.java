package lv.sanelite.initium.painting;

import lv.sanelite.initium.Initium;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Initium.MOD_ID);

    public static final RegistryObject<PaintingVariant> LOGO = PAINTING_VARIANTS
            .register("logo",() -> new PaintingVariant(32,16));
    public static final RegistryObject<PaintingVariant> QUANT2 = PAINTING_VARIANTS
            .register("quant2",() -> new PaintingVariant(32,64));


    public static void register(IEventBus eventBus){
        PAINTING_VARIANTS.register(eventBus);
    }
}

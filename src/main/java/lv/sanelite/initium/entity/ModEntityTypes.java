package lv.sanelite.initium.entity;

import lv.sanelite.initium.Initium;
import lv.sanelite.initium.entity.custom.SentryNPC;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Initium.MOD_ID);


    public static final RegistryObject<EntityType<SentryNPC>> SENTRY =
            ENTITY_TYPES.register("sentry",
                    () -> EntityType.Builder.of(SentryNPC::new, MobCategory.MONSTER)
                            .sized(0.4f, 1.8f)
                            .build(new ResourceLocation(Initium.MOD_ID, "sentry").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

}

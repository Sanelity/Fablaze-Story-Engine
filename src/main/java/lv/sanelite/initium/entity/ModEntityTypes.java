package lv.sanelite.initium.entity;

import lv.sanelite.initium.Initium;
//import lv.sanelite.initium.entity.custom.ActorNPC;
import lv.sanelite.initium.entity.actor.AzureNPC;
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


//    public static final RegistryObject<EntityType<ActorNPC>> ACTOR =
//            ENTITY_TYPES.register("actor",
//                    () -> EntityType.Builder.of(ActorNPC::new, MobCategory.AMBIENT)
//                            .sized(0.4f, 1.8f)
//                            .build(new ResourceLocation(Initium.MOD_ID, "sentry").toString()));

    public static final RegistryObject<EntityType<AzureNPC>> AZURE =
            ENTITY_TYPES.register("azure",
                    () -> EntityType.Builder.of(AzureNPC::new, MobCategory.CREATURE)
                            .sized(0.4f, 1.8f)
                            .build(new ResourceLocation(Initium.MOD_ID, "azuricsentry").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

}

package lv.sanelite.initium.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lv.sanelite.initium.Initium;
import lv.sanelite.initium.block.ModBlocks;
import lv.sanelite.initium.entity.ModEntityTypes;
import lv.sanelite.initium.entity.ModVillager;
import lv.sanelite.initium.entity.custom.SentryNPC;
import lv.sanelite.initium.item.ModItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.List;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = Initium.MOD_ID)
    public static class ForgeEvents{


        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event){
            if(event.getType() ==ModVillager.BOUNCE_MASTER.get()) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.DICED6.get(), 1);
                int villagerLevel = 1;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 16),
                        stack, 1, 8, 0.02f));
            }
            if(event.getType() ==ModVillager.BOUNCE_MASTER.get()) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.URANIUM.get(), 5);
                int villagerLevel = 2;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 4),
                        stack, 10, 8, 0.02f));
            }
            if(event.getType() ==ModVillager.BOUNCE_MASTER.get()) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModBlocks.URANIUM_BLOCK.get(), 1);
                int villagerLevel = 3;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 32),
                        stack, 2, 32, 0.02f));
            }
        }
    }
    @Mod.EventBusSubscriber(modid = Initium.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents{
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event){
            event.put(ModEntityTypes.SENTRY.get(), SentryNPC.setAttributes());

        }
    }

}

package com.example.sirenmod;

import com.example.sirenmod.block.SirenBlock;
import com.example.sirenmod.tileentity.SirenTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = SirenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SirenMod {
    public static final String MOD_ID = "sirenmod";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);

    public static final RegistryObject<Block> SIREN_BLOCK = BLOCKS.register("siren_block", SirenBlock::new);
    public static final RegistryObject<Item> SIREN_BLOCK_ITEM = ITEMS.register("siren_block", () -> new BlockItem(SIREN_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<TileEntityType<SirenTileEntity>> SIREN_TILE = TILE_ENTITIES.register("siren_tile", 
        () -> TileEntityType.Builder.create(SirenTileEntity::new, SIREN_BLOCK.get()).build(null));

    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Register a custom renderer if needed
        });
    }

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        // Register common setup logic
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());
    }

    public static void registerModComponents(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
    }
}

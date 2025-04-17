// File: SirenMod.java
package com.example.sirenmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import com.example.sirenmod.network.PacketPlaySirenSound;
import com.example.sirenmod.network.PacketStopSirenSound;

@Mod(modid = SirenMod.MODID, version = SirenMod.VERSION, name = SirenMod.NAME)
public class SirenMod {
    public static final String MODID = "sirenmod";
    public static final String VERSION = "1.0";
    public static final String NAME = "Siren Mod";
    public static Block sirenBlock;
    public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("sirenmod");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        sirenBlock = new BlockSiren()
                .setBlockName("sirenBlock")
                .setBlockTextureName(MODID + ":siren_block")
                .setCreativeTab(CreativeTabs.tabBlock);

        GameRegistry.registerBlock(sirenBlock, "siren_block");
        registerPackets();
    }

    private void registerPackets() {
        int id = 0;
        network.registerMessage(PacketPlaySirenSound.Handler.class, PacketPlaySirenSound.class, id++, Side.CLIENT);
        network.registerMessage(PacketStopSirenSound.Handler.class, PacketStopSirenSound.class, id++, Side.CLIENT);
    }
}
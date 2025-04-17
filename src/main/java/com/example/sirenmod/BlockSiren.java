package com.example.sirenmod;

import com.example.sirenmod.network.PacketPlaySirenSound;
import com.example.sirenmod.network.PacketStopSirenSound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockSiren extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;

    public BlockSiren() {
        super(Material.iron);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.topIcon = iconRegister.registerIcon("sirenmod:siren_block_top");
        this.sideIcon = iconRegister.registerIcon("sirenmod:siren_block_side");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? this.topIcon : this.sideIcon;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (!world.isRemote) {
            System.out.println("SIREN CHECK - Powered: " + powered + ", Meta: " + meta);

            if (powered && meta != 1) {
                System.out.println("SIREN ACTIVATING at [" + x + ", " + y + ", " + z + "]");
                world.setBlockMetadataWithNotify(x, y, z, 1, 2);
                SirenMod.network.sendToAll(new PacketPlaySirenSound(x, y, z));
            } else if (!powered && meta == 1) {
                System.out.println("SIREN DEACTIVATING at [" + x + ", " + y + ", " + z + "]");
                world.setBlockMetadataWithNotify(x, y, z, 0, 2);
                SirenMod.network.sendToAll(new PacketStopSirenSound(x, y, z));
            }
        }
    }

}
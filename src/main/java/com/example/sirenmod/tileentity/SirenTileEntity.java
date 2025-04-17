package com.example.sirenmod.tileentity;

import com.example.sirenmod.client.sound.ProceduralSirenSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SirenTileEntity extends TileEntity {

    public SirenTileEntity() {
        super(TileEntityType.create(SirenTileEntity::new, new BlockPos(0, 0, 0)));
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote) {
            BlockPos pos = getPos();
            if (world.getBlockState(pos).get(SirenBlock.POWERED)) {
                ProceduralSirenSound.play(pos.getX(), pos.getY(), pos.getZ());
            } else {
                ProceduralSirenSound.stop(pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }
}

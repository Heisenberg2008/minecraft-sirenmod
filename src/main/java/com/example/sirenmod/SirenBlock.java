package com.example.sirenmod.block;

import com.example.sirenmod.tileentity.SirenTileEntity;
import com.example.sirenmod.client.sound.ProceduralSirenSound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SirenBlock extends Block {

    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public SirenBlock() {
        super(Properties.create(Material.IRON));
        this.setDefaultState(this.stateContainer.getBaseState().with(POWERED, false));
    }

    @Override
    public void onPlayerClick(World worldIn, BlockPos pos, PlayerEntity player) {
        // Trigger sound playback when the block is activated
        if (!worldIn.isRemote) {
            boolean powered = worldIn.getBlockState(pos).get(POWERED);
            worldIn.setBlockState(pos, this.getDefaultState().with(POWERED, !powered), 3);

            if (!powered) {
                // Start siren sound
                ProceduralSirenSound.play(pos.getX(), pos.getY(), pos.getZ());
            } else {
                // Stop siren sound
                ProceduralSirenSound.stop(pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }

    @Override
    public TileEntity createTileEntity(BlockState state, World world) {
        return new SirenTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}

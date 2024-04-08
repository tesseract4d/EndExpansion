//Adapted from: http://jabelarminecraft.blogspot.com/p/minecraft-modding-custom-fluids.html?m=1
package mods.tesseract.endex.mod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;

public class BlockFluidAcid extends BlockFluidClassic {
    public BlockFluidAcid(Fluid f, Material m) {
        super(f, m);
    }

    @Nonnull
    @Override
    public String getUnlocalizedName() {
        Fluid f = FluidRegistry.getFluid(fluidName);
        if (f != null) {
            return f.getUnlocalizedName();
        }
        return super.getUnlocalizedName();
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        if (entityIn instanceof EntityLivingBase) {
            entityIn.attackEntityFrom(DamageSource.GENERIC, 1);
        }
    }
}
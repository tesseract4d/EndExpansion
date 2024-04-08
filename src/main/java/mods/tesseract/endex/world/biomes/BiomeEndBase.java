package mods.tesseract.endex.world.biomes;

import git.jbredwards.nether_api.api.biome.IEndBiome;
import git.jbredwards.nether_api.api.world.INetherAPIChunkGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeEnd;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nonnull;

public class BiomeEndBase extends BiomeEnd implements IEndBiome {
    public BiomeEndBase(BiomeProperties properties) {
        super(properties);
    }


    @Override
    public void buildSurface(@Nonnull final INetherAPIChunkGenerator chunkGenerator, final int chunkX, final int chunkZ, @Nonnull final ChunkPrimer primer, final int x, final int z, final double terrainNoise) {
        int currDepth = -1;
        for(int y = chunkGenerator.getWorld().getActualHeight() - 1; y >= 0; --y) {
            final IBlockState here = primer.getBlockState(x, y, z);
            if(here.getMaterial() == Material.AIR) currDepth = -1;
            else if(here.getBlock() == Blocks.END_STONE) {
                if(currDepth == -1) {
                    currDepth = 3 + chunkGenerator.getRand().nextInt(2);
                    primer.setBlockState(x, y, z, topBlock);
                }
                else if(currDepth > 0) {
                    --currDepth;
                    primer.setBlockState(x, y, z, fillerBlock);
                }
            }
        }
    }
}

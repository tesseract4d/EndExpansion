package mods.tesseract.endex.world.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenSurfacePatch extends WorldGenerator {
    private final IBlockState surfaceBlockState;
    private final IBlockState replacementBlockState;
    private final int numPatches;

    public WorldGenSurfacePatch(IBlockState generate, IBlockState replace, int numPatches) {
        this.surfaceBlockState = generate;
        this.replacementBlockState = replace;
        this.numPatches = numPatches;
    }

    public boolean generate(World world, Random rand, BlockPos pos) {
        boolean[][] previouslyChecked = new boolean[16][16];

        for (int n = 0; n < numPatches; n++) {
            int size = 4 + rand.nextInt(5);
            int f = 16 - size;
            if (f == 0)
                f = 1;

            int xOffset = rand.nextInt(f);
            int zOffset = rand.nextInt(f);
            double midX = (size / 2.0 + xOffset);
            double midZ = (size / 2.0 + zOffset);
            double maxDist = (size / 2.0) * (size / 2.0);

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    if (!previouslyChecked[z][x]) {
                        //distort the actual distance a bit so we dont generate perfect circles
                        double xWarp = (rand.nextDouble() / 3.0) + 0.833;
                        double zWarp = (rand.nextDouble() / 3.0) + 0.833;
//	    				double xWarp = (randy.nextDouble()/2.0) + 0.75;
//		    			double zWarp = (randy.nextDouble()/2.0) + 0.75;
                        double xDist = (midX - x) * xWarp;
                        double zDist = (midZ - z) * zWarp;
                        if ((xDist * xDist) + (zDist * zDist) < maxDist - rand.nextInt(3)) {
                            //mark this block as already decorated so we dont waste time trying to decorate it again
                            previouslyChecked[z][x] = true;

                            BlockPos current = pos.add(x, 70, z);

                            //find the surface block
                            for (; current.getY() > 49 && world.isAirBlock(current); current = current.down()) {
                                ;
                            }

                            if (world.getBlockState(current) == replacementBlockState) {
                                world.setBlockState(current, surfaceBlockState, 2);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}

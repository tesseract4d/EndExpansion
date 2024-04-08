package mods.tesseract.endex.init;

import mods.tesseract.endex.world.biomes.BiomeStygianGrowth;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class BiomeInit
{
	public static Biome STYGIAN_GROWTH;

    public static void registerBiomes(){
        STYGIAN_GROWTH = registerBiome(new BiomeStygianGrowth(),"stygian_growth", Type.END);
    }

	private static Biome registerBiome(Biome biome, String name, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		return biome;
	}
}

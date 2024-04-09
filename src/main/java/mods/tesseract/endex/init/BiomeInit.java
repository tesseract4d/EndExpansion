package mods.tesseract.endex.init;

import mods.tesseract.endex.world.biomes.BiomeEndBase;
import mods.tesseract.endex.world.biomes.BiomeEndDesert;
import mods.tesseract.endex.world.biomes.BiomeStygianGrowth;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static net.minecraft.entity.EnumCreatureType.MONSTER;


public class BiomeInit {
    public static Biome STYGIAN_GROWTH;
    public static Biome END_DESERT;
    public static Biome OUTER;

    public static void registerBiomes() {
        STYGIAN_GROWTH = registerBiome(new BiomeStygianGrowth(new BiomeProperties("Stygian Growth").setTemperature(Biomes.SKY.getDefaultTemperature()).setRainfall(Biomes.SKY.getRainfall()).setRainDisabled()), "stygian_growth", Type.END);
        END_DESERT = registerBiome(new BiomeEndDesert(new BiomeProperties("End Desert").setTemperature(Biomes.SKY.getDefaultTemperature()).setRainfall(Biomes.SKY.getRainfall()).setRainDisabled()), "end_desert", Type.END);
        OUTER = registerBiome(new BiomeEndBase(new BiomeProperties("Outer").setTemperature(Biomes.SKY.getDefaultTemperature()).setRainfall(Biomes.SKY.getRainfall()).setRainDisabled()), "outer", Type.END);
        OUTER.getSpawnableList(MONSTER).clear();
    }

    private static Biome registerBiome(Biome biome, String name, Type... types) {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, types);
        return biome;
    }
}

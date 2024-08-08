package raccoonman.terrablenderfix.extensions;

import java.util.Set;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface IExtendedSurfaceContext {
	// gets the biomes in a 3x3 chunk area
	Set<ResourceKey<Biome>> getSurroundingBiomes();
}
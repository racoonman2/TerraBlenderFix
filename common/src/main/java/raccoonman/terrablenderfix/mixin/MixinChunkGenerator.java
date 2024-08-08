package raccoonman.terrablenderfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import raccoonman.terrablenderfix.util.SurfaceRegionHolder;

@Mixin(ChunkGenerator.class)
class MixinChunkGenerator {
	
	@Inject(
		method = "buildSurface",	
		at = @At("HEAD")
	)
	public static void buildSurface$HEAD(WorldGenRegion var1, StructureManager var2, RandomState var3, ChunkAccess var4) {
		SurfaceRegionHolder.set(var1);
	}
	
	@Inject(at = @At("buildSurface"))
	public static void buildSurface$TAIL(WorldGenRegion var1, StructureManager var2, RandomState var3, ChunkAccess var4) {
		SurfaceRegionHolder.set(var1);
	}	
}

package raccoonman.terrablenderfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import raccoonman.terrablenderfix.util.SurfaceRegionHolder;

@Mixin(NoiseBasedChunkGenerator.class)
class MixinNoiseBasedChunkGenerator {
	
	@Inject(
		method = "buildSurface(Lnet/minecraft/server/level/WorldGenRegion;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/chunk/ChunkAccess;)V",
		at = @At("HEAD"),
		expect = 1
	)
	private void buildSurface$HEAD(WorldGenRegion var1, StructureManager var2, RandomState var3, ChunkAccess var4, CallbackInfo callback) {
		SurfaceRegionHolder.set(var1);
	}
	
	@Inject(
		method = "buildSurface(Lnet/minecraft/server/level/WorldGenRegion;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/chunk/ChunkAccess;)V",
		at = @At("TAIL"),
		expect = 1
	)
	private void buildSurface$TAIL(WorldGenRegion var1, StructureManager var2, RandomState var3, ChunkAccess var4, CallbackInfo callback) {
		SurfaceRegionHolder.set(null);
	}	
}

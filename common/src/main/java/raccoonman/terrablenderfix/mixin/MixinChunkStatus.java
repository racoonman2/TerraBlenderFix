package raccoonman.terrablenderfix.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import raccoonman.terrablenderfix.util.SurfaceRegionHolder;

@Mixin(ChunkStatus.class)
class MixinChunkStatus {

//	surface $ head
	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/chunk/ChunkGenerator;buildSurface(Lnet/minecraft/server/level/WorldGenRegion;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/chunk/ChunkAccess;)V"
		),
		method = { "method_16569", "m_156246_" },
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	private static void method_16569$HEAD(ChunkStatus status, ServerLevel level, ChunkGenerator generator, List<ChunkAccess> chunks, ChunkAccess centerChunk, CallbackInfo callback, WorldGenRegion region) {
		SurfaceRegionHolder.set(region);
	}
	
//	surface $ tail
	@Inject(
		at = @At("TAIL"),
		method = { "method_16569", "m_156246_" }
	)
	private static void method_16569$TAIL(ChunkStatus status, ServerLevel level, ChunkGenerator generator, List<ChunkAccess> chunks, ChunkAccess centerChunk, CallbackInfo callback) {
		SurfaceRegionHolder.set(null);
	}
}

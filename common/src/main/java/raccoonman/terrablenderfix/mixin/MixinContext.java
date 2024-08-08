package raccoonman.terrablenderfix.mixin;

import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;
import raccoonman.terrablenderfix.extensions.IExtendedSurfaceContext;
import raccoonman.terrablenderfix.util.SurfaceRegionHolder;

@Mixin(Context.class)
abstract class MixinContext implements IExtendedSurfaceContext {
	@Shadow
	@Final
    public ChunkAccess chunk;

	@Unique
	@Nullable
	private Set<ResourceKey<Biome>> surroundingBiomes;
	
	@Inject(at = @At("TAIL"), method = "<init>")
	private void Context(CallbackInfo callback) {
		WorldGenRegion region = SurfaceRegionHolder.get();
		
		if(region != null) {
	    	this.surroundingBiomes = new HashSet<>();

			ChunkPos centerPos = this.chunk.getPos();
			for(int x = -1; x <= 1; x++) {
	    		for(int z = -1; z <= 1; z++) {
	    			ChunkAccess chunk = region.getChunk(centerPos.x + x, centerPos.z + z);
	    			
	    			for(LevelChunkSection section : chunk.getSections()) {
	    				section.getBiomes().getAll((biome) -> {
	    					biome.unwrapKey().ifPresent(this.surroundingBiomes::add);
	    				});
	    			}
	        	}
	    	}
		}
	}
	
	@Override
	public Set<ResourceKey<Biome>> getSurroundingBiomes() {
		return this.surroundingBiomes;
	}
}

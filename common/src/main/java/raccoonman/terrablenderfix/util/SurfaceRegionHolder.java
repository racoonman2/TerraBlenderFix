package raccoonman.terrablenderfix.util;

import net.minecraft.server.level.WorldGenRegion;

public class SurfaceRegionHolder {
	private static final ThreadLocal<WorldGenRegion> SURFACE_REGION = new ThreadLocal<>();

	public static void set(WorldGenRegion region) {
		SURFACE_REGION.set(region);
	}

	public static WorldGenRegion get() {
		return SURFACE_REGION.get();
	}
}
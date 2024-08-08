package raccoonman.terrablenderfix.mixin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.level.levelgen.SurfaceRules;
import raccoonman.terrablenderfix.TBFixCommon;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.SurfaceRuleManager.RuleCategory;
import terrablender.api.SurfaceRuleManager.RuleStage;

@Mixin(SurfaceRuleManager.class)
class MixinSurfaceRuleManager {
	@Shadow(remap = false)
	private static Map<RuleCategory, Map<String, SurfaceRules.RuleSource>> surfaceRules; 
	@Shadow(remap = false)
	private static Map<RuleCategory, SurfaceRules.RuleSource> defaultSurfaceRules;
	@Shadow(remap = false)
	private static Map<RuleCategory, Map<RuleStage, List<Pair<Integer, SurfaceRules.RuleSource>>>> defaultSurfaceRuleInjections;

	@Overwrite
	public static SurfaceRules.RuleSource getNamespacedRules(RuleCategory category, SurfaceRules.RuleSource datapackRules) {
		TBFixCommon.LOGGER.info("Optimizing surface rules");
		
    	// store surface rules in a list instead of by namespace. This removes the need to do a biome lookup to get the namespace, which is the main source of the lag.
        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder.addAll(surfaceRules.get(category).values());
        builder.add(datapackRules);

//		These are essentially a copy of the vanilla surface rules, and I can't find any way to have them be in the same sequence as the datapack rules without one overriding the other.
//		doesn't seem to break anything yet but who knows
        
//      builder.add(getDefaultSurfaceRules(category));
        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }
	
	@Inject(
		at = @At("TAIL"),
		method = "<clinit>"
	)
	private static void clinit(CallbackInfo callback) {
		// surface rule order actually matters now so use a map type that preserves that
		surfaceRules = new LinkedHashMap<>(surfaceRules);
		defaultSurfaceRules = new LinkedHashMap<>(defaultSurfaceRules);
		defaultSurfaceRuleInjections = new LinkedHashMap<>(defaultSurfaceRuleInjections);
		
		TBFixCommon.LOGGER.info("Changed SurfaceRuleManager map type");
	}
}

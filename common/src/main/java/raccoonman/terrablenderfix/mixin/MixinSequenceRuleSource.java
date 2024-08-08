package raccoonman.terrablenderfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.collect.ImmutableList;

import net.minecraft.world.level.levelgen.SurfaceRules;
import raccoonman.terrablenderfix.util.StaticCondition;

// prevents any sequences or test rules that were disabled in MixinBiomeConditionSource from being evaluated
@Mixin(value = SurfaceRules.SequenceRuleSource.class, priority = 1101)
public class MixinSequenceRuleSource {

	// FIXME this conflicts with c2me
	// https://github.com/RelativityMC/C2ME-fabric/blob/368487ccb5a4a5224a6305c020e0e7f1df104136/c2me-opts-allocs/src/main/java/com/ishland/c2me/opts/allocs/mixin/surfacebuilder/MixinMaterialRulesSequenceMaterialRule.java
    @Redirect(
    	at = @At(
    		value = "INVOKE",
    		target = "Lcom/google/common/collect/ImmutableList$Builder;add(Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList$Builder;",
    		remap = false
    	), 
    	method = "apply"
    )
    public ImmutableList.Builder<Object> add(ImmutableList.Builder<Object> builder, Object value) {
    	if(
    		(value instanceof SurfaceRules.TestRule test && test.condition().equals(StaticCondition.FALSE)) ||
    		(value instanceof SurfaceRules.SequenceRule sequence && sequence.rules().isEmpty())
    	) {
    		return builder;
    	}
    	return builder.add(value);
    }
}
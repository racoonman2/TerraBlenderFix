package raccoonman.terrablenderfix.util;

import net.minecraft.world.level.levelgen.SurfaceRules;

// a surface condition that only returns true or false
public record StaticCondition(boolean value) implements SurfaceRules.Condition {
	public static final StaticCondition FALSE = new StaticCondition(false);
	public static final StaticCondition TRUE = new StaticCondition(true);

	@Override
	public boolean test() {
		return this.value;
	}
}
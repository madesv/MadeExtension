package kr.madesv.extension.skript.mcmmo.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.nossr50.datatypes.skills.SuperAbilityType;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityDeactivateEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprEvtSuperAbilityType extends SimpleExpression<SuperAbilityType> {

	@Override
	public Class<? extends SuperAbilityType> getReturnType() {
		return SuperAbilityType.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "event-abilitytype";
	}

	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		if (!ScriptLoader.isCurrentEvent(McMMOPlayerAbilityActivateEvent.class)
				&& !ScriptLoader.isCurrentEvent(McMMOPlayerAbilityDeactivateEvent.class)) {
			return false;
		}
		return true;
	}

	@Override
	@Nullable
	protected SuperAbilityType[] get(Event e) {
		if (e.getEventName().equals("McMMOPlayerAbilityActivateEvent")) {
			return new SuperAbilityType[] { ((McMMOPlayerAbilityActivateEvent) e).getAbility() };
		} else if (e.getEventName().equals("McMMOPlayerAbilityDeactivateEvent")) {
			return new SuperAbilityType[] { ((McMMOPlayerAbilityDeactivateEvent) e).getAbility() };
		}
		return null;
	}

}

package kr.madesv.extension.skript.mcmmo.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprEvtPrimarySkillType extends SimpleExpression<PrimarySkillType> {

	@Override
	public Class<? extends PrimarySkillType> getReturnType() {
		return PrimarySkillType.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "event-skilltype";
	}

	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		if (!ScriptLoader.isCurrentEvent(McMMOPlayerLevelUpEvent.class)
				&& !ScriptLoader.isCurrentEvent(McMMOPlayerXpGainEvent.class)) {
			return false;
		}
		return true;
	}

	@Override
	@Nullable
	protected PrimarySkillType[] get(Event e) {
		if (e.getEventName().equals("McMMOPlayerLevelUpEvent")) {
			return new PrimarySkillType[] { ((McMMOPlayerLevelUpEvent) e).getSkill() };
		} else if (e.getEventName().equals("McMMOPlayerXpGainEvent")) {
			return new PrimarySkillType[] { ((McMMOPlayerXpGainEvent) e).getSkill() };
		}
		return null;
	}

}

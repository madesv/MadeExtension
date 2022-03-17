package kr.madesv.extension.skript.mcmmo.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.nossr50.party.PartyManager;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprmcMMOPartyEXP extends SimpleExpression<Number> {
	private Expression<String> s;

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		s = (Expression<String>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[sharpsk] [mcmmo] exp[erience] of party %string%";
	}

	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[] { PartyManager.getParty(s.getSingle(e)).getXp() };
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {

			Number n = (Number) delta[0];
			PartyManager.getParty(s.getSingle(e)).setXp(n.floatValue());
		}
		if (mode == Changer.ChangeMode.ADD) {
			Number n = (Number) delta[0];
			PartyManager.getParty(s.getSingle(e)).applyXpGain(n.floatValue());
		}
		if (mode == Changer.ChangeMode.REMOVE) {

			Number n = (Number) delta[0];

			float exp = PartyManager.getParty(s.getSingle(e)).getXp() - n.floatValue();
			PartyManager.getParty(s.getSingle(e)).setXp(exp);

		}
		if (mode == Changer.ChangeMode.RESET) {
			PartyManager.getParty(s.getSingle(e)).setXp(0);
		}

	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(new Class[] { Number.class });
		if (mode == Changer.ChangeMode.ADD)
			return CollectionUtils.array(new Class[] { Number.class });
		if (mode == Changer.ChangeMode.REMOVE)
			return CollectionUtils.array(new Class[] { Number.class });
		return null;
	}
}

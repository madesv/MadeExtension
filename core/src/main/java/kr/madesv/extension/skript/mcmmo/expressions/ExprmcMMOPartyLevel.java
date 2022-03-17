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

public class ExprmcMMOPartyLevel extends SimpleExpression<Number> {
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
		return "[sharpsk] [mcmmo] level of party %string%";
	}

	@Override
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[] { PartyManager.getParty(s.getSingle(e)).getLevel() };
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			Number n = (Number) delta[0];
			Integer n2 = n.intValue();
			PartyManager.getParty(s.getSingle(e)).setLevel(n2.intValue());
		}
		if (mode == Changer.ChangeMode.ADD) {
			Number n = (Number) delta[0];
			Integer n2 = n.intValue();
			PartyManager.getParty(s.getSingle(e))
					.setLevel(PartyManager.getParty(s.getSingle(e)).getLevel() + n2.intValue());
		}
		if (mode == Changer.ChangeMode.REMOVE) {

			Number n = (Number) delta[0];
			Integer n2 = n.intValue();
			if (n2.intValue() < PartyManager.getParty(s.getSingle(e)).getLevel()) {
				PartyManager.getParty(s.getSingle(e))
						.setLevel(PartyManager.getParty(s.getSingle(e)).getLevel() - n2.intValue());
			} else {
				PartyManager.getParty(s.getSingle(e)).setLevel(0);
			}
		}
		if (mode == Changer.ChangeMode.RESET) {
			PartyManager.getParty(s.getSingle(e)).setLevel(0);
		}
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(new Class[] { Number.class });
		if (mode == Changer.ChangeMode.ADD)
			return CollectionUtils.array(new Class[] { Number.class });
		if (mode == Changer.ChangeMode.RESET)
			return CollectionUtils.array(new Class[] { Number.class });
		if (mode == Changer.ChangeMode.REMOVE)
			return CollectionUtils.array(new Class[] { Number.class });
		return null;
	}
}

package kr.madesv.extension.skript.mcmmo.expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.PartyAPI;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprmcMMOParty extends SimpleExpression<String> {
	private Expression<Player> p;

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		p = (Expression<Player>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "%player%'s [mcmmo] party";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[] { PartyAPI.getPartyName(p.getSingle(e)) };
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		try {
			if (mode == Changer.ChangeMode.SET) {
				PartyAPI.addToParty(p.getSingle(e), (String) delta[0]);
			}
		} catch (NullPointerException ex) {

		}
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(new Class[] { String.class });
		return null;
	}
}

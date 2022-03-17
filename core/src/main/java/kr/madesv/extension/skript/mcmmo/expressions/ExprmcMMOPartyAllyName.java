package kr.madesv.extension.skript.mcmmo.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.nossr50.api.PartyAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprmcMMOPartyAllyName extends SimpleExpression<String> {
	private Expression<String> s;

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
		s = (Expression<String>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[mcmmo] [party] ally of party %string%";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[] { PartyAPI.getAllyName(s.getSingle(e)) };
	}

}

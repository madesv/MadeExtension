package kr.madesv.extension.skript.mcmmo.conditions;

import javax.annotation.Nullable;

import com.gmail.nossr50.api.ChatAPI;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.PartyAPI;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class CondmcMMOPartyHasAlly extends Condition {
	private Expression<String> s;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, SkriptParser.ParseResult arg3) {
		s = (Expression<String>) expr[0];

		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "[mcmmo] party %string% has [a] ally";
	}

	@Override
	public boolean check(Event e) {
		try {
			return PartyAPI.hasAlly(s.getSingle(e));
		} catch (NullPointerException exception) {
			return false;
		}
	}
}

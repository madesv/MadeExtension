package kr.madesv.extension.skript.mcmmo.conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.PartyAPI;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class CondmcMMOSameParty extends Condition {
	private Expression<Player> p;
	private Expression<Player> p2;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, SkriptParser.ParseResult arg3) {
		p = (Expression<Player>) expr[0];
		p2 = (Expression<Player>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "%player% is [in] same party as %player%";
	}

	@Override
	public boolean check(Event e) {
		try {
			return PartyAPI.inSameParty(p.getSingle(e), p2.getSingle(e));
		} catch (NullPointerException exception) {
			return false;
		}
	}
}

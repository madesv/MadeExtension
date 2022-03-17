package kr.madesv.extension.skript.mcmmo.conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.ChatAPI;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class CondmcMMOAdminChat extends Condition {
	private Expression<Player> p;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, SkriptParser.ParseResult arg3) {
		p = (Expression<Player>) expr[0];

		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "[mcmmo] %player% is using admin[]chat";
	}

	@Override
	public boolean check(Event e) {
		try {
			return ChatAPI.isUsingAdminChat(p.getSingle(e));
		} catch (NullPointerException exception) {
			return false;
		}
	}
}

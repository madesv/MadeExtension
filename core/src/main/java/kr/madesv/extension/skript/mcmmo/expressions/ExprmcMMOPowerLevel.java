package kr.madesv.extension.skript.mcmmo.expressions;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.ExperienceAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprmcMMOPowerLevel extends SimpleExpression<Integer> {
	private Expression<OfflinePlayer> p;

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		p = (Expression<OfflinePlayer>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[mcmmo] %offlineplayer%'s power[]level";
	}

	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if (p == null) {
			return new Integer[] { 0 };
		}
		;
		if (p.getSingle(e).isOnline()) {
			return new Integer[] { ExperienceAPI.getPowerLevel(p.getSingle(e).getPlayer()) };
		} else {
			return new Integer[] { ExperienceAPI.getPowerLevelOffline(p.getSingle(e).getUniqueId()) };
		}
	}

}

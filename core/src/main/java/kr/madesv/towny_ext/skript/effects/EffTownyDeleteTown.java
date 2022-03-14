package kr.madesv.towny_ext.skript.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import javax.inject.Inject;

import java.util.logging.Logger;

public class EffTownyDeleteTown extends Effect {
	private Expression<String> s;

	@Inject
	Logger logger;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		s = (Expression<String>) expr[0];

		return true;
	}

	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[towny] delete town %string%";
	}

	@Override
	protected void execute(Event e) {
		TownyUniverse.getInstance().getDataSource().removeTown(TownyUniverse.getInstance().getTown(s.getSingle(e)));
	}
}

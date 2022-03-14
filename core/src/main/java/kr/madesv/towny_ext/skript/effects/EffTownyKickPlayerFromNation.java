package kr.madesv.towny_ext.skript.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.EmptyTownException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import javax.inject.Inject;

;import java.util.logging.Logger;

public class EffTownyKickPlayerFromNation extends Effect {
	private Expression<String> s;
	private Expression<OfflinePlayer> p;

	@Inject
	Logger logger;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		p = (Expression<OfflinePlayer>) expr[0];
		s = (Expression<String>) expr[1];

		return true;
	}

	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[towny] kick %offlineplayer% from [town] %string%";
	}

	@Override
	protected void execute(Event e) {
		try {
			TownyUniverse.getInstance().getTown(s.getSingle(e))
					.removeResident(TownyUniverse.getInstance().getResident(p.getSingle(e).getName()));
		} catch (EmptyTownException ignored) {
		} catch (NotRegisteredException ex2) {
			logger.warning("Could not kick resident: " + "\"" + p.getSingle(e).getName() + "\""
					+ " from town " + "\"" + s.getSingle(e) + "\"");
			logger.warning("Resident is not in town: " + "\"" + s.getSingle(e) + "\"");
			return;
		}

	}
}

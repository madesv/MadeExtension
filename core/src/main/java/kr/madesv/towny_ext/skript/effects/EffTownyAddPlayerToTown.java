package kr.madesv.towny_ext.skript.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.logging.Logger;

public class EffTownyAddPlayerToTown extends Effect {
	private Expression<String> s;
	private Expression<OfflinePlayer> p;

	@Inject
	Logger logger;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, @NotNull Kleenean paramKleenean,
						@NotNull SkriptParser.ParseResult paramParseResult) {
		p = (Expression<OfflinePlayer>) expr[0];

		return true;
	}

	@NotNull
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[towny] Add %offlineplayer% to [town] %string%";
	}

	@Override
	protected void execute(Event e) {
		try {
			TownyUniverse.getInstance().getResident(p.getSingle(e).getName()).setTown(TownyUniverse.getInstance().getTown(s.getSingle(e)));
		} catch (AlreadyRegisteredException ex3) {
			logger.warning("Could not add resident: " + "\"" + p.getSingle(e).getName() + "\"" + " to town "
					+ "\"" + s.getSingle(e) + "\"");
			logger.warning("Resident is already in town: " + "\"" + s.getSingle(e) + "\"");
		}

	}
}

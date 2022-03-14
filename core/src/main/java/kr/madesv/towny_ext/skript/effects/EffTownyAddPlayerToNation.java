package kr.madesv.towny_ext.skript.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import lombok.SneakyThrows;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.inject.Inject;

;import java.util.logging.Logger;

public class EffTownyAddPlayerToNation extends Effect {

	@Inject
	Logger logger;

	private Expression<OfflinePlayer> p;
	private Expression<String> nat;

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
		return "[towny] add %offlineplayer% to nation %string%";
	}

	@Override
	@SneakyThrows
	protected void execute(Event e) {
		TownyUniverse.getInstance().getResident(p.getSingle(e).getName()).setName(nat.getSingle(e));
	}
}

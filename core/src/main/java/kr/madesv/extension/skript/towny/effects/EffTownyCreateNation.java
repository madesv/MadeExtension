package kr.madesv.extension.skript.towny.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import kr.madesv.extension.MadeExtensionPlugin;
import lombok.SneakyThrows;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.inject.Inject;

;import java.util.Objects;

public class EffTownyCreateNation extends Effect {
	private Expression<String> nat;
	private Expression<String> tow;
	private Expression<Number> bal;

	@Inject
	MadeExtensionPlugin plugin;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, @NotNull Kleenean paramKleenean,
						@NotNull SkriptParser.ParseResult paramParseResult) {
		MadeExtensionPlugin.injector.injectMembers(this);
		nat = (Expression<String>) expr[0];
		tow = (Expression<String>) expr[1];
		bal = (Expression<Number>) expr[2];

		return true;
	}

	@NotNull
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[towny] create nation %string% in town %string% [with [bank] balance %-number%]";
	}

	@Override
	@SneakyThrows
	protected void execute(Event e) {
		// Nation Generator

		try {
			String maybeNation = Objects.requireNonNull(nat.getSingle(e));
			String maybeTown = Objects.requireNonNull(tow.getSingle(e));
			TownyUniverse.getInstance().getDataSource().newNation(maybeNation);
			Nation nation = TownyUniverse.getInstance().getNation(maybeNation);
			Town town = TownyUniverse.getInstance().getTown(maybeTown);
			nation.addTown(town);
			nation.setCapital(town);
			if (bal != null) {
				nation.getAccount().setBalance(bal.getSingle(e).doubleValue(), "Nation Creation");
			} else {
				nation.getAccount().setBalance(0, "Nation Creation");
			}
			TownyUniverse.getInstance().getDataSource().saveTown(town);
			TownyUniverse.getInstance().getDataSource().saveNation(nation);
			TownyUniverse.getInstance().getDataSource().saveNations();

		} catch (NotRegisteredException ex1) {
			plugin.getLogger().warning("Could not register nation: " + "\"" + nat.getSingle(e) + "\"");
		} catch (AlreadyRegisteredException ex2) {
			plugin.getLogger().warning(
					"Could not register nation: " + "\"" + nat.getSingle(e) + "\"" + ". Nation already exists in town");
		}
	}

}

package kr.madesv.extension.skript.towny.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import kr.madesv.extension.MadeExtensionPlugin;
import org.bukkit.event.Event;
import javax.annotation.Nullable;
import javax.inject.Inject;

public class EffTownyRenameTown extends Effect {
	private Expression<String> s;
	private Expression<String> s2;

	@Inject
	MadeExtensionPlugin plugin;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		MadeExtensionPlugin.injector.injectMembers(this);
		s = (Expression<String>) expr[0];
		s2 = (Expression<String>) expr[1];

		return true;
	}

	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[towny] rename town %string% to %string%";
	}

	@Override
	protected void execute(Event e) {
		try {
			try {
				TownyUniverse.getInstance().getDataSource().renameTown(TownyUniverse.getInstance().getTown(s.getSingle(e)),
						s2.getSingle(e));
			} catch (AlreadyRegisteredException e1) {
				plugin.getLogger().warning(
						"Could not rename town: " + "\"" + s.getSingle(e) + "\"" + " Town name already in use.");
			}
		} catch (NotRegisteredException ex) {
			plugin.getLogger().warning("Could not rename town: " + "\"" + s.getSingle(e) + "\"" + " Town does not exist");
			return;
		}

	}
}

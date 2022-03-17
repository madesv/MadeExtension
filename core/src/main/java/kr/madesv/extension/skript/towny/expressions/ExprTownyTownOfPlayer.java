package kr.madesv.extension.skript.towny.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ExprTownyTownOfPlayer extends SimpleExpression<String> {

	private Expression<OfflinePlayer> resident;

	@NotNull
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult Result) {
		resident = (Expression<OfflinePlayer>) expr[0];
		return true;
	}

	@NotNull
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[madesv] [towny] town of %player%";
	}

	@Nullable
	@Override
	protected String[] get(Event e) {
		if (resident.getSingle(e) == null) return null;

		Resident a = TownyUniverse.getInstance().getResident(resident.getSingle(e).getUniqueId());
		try {
			return new String[] { a.getTown().getName() };
		} catch (NotRegisteredException e1) {
			return null;
		}

	}

	@Override
	public boolean isSingle() {
		return true;
	}

}

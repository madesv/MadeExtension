package kr.madesv.extension.skript.towny.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import lombok.SneakyThrows;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprTownyNationOfPlayer extends SimpleExpression<String> {

	private Expression<OfflinePlayer> resident;

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

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[madesv] [towny] nation of %player%";
	}

	@SneakyThrows
	@Nullable
	@Override
	protected String[] get(Event e) {
		if (resident.getSingle(e) == null) return null;

		Resident a = TownyUniverse.getInstance().getResident(resident.getSingle(e).getUniqueId());
		try {
			return new String[] { a.getNation().getName() };
		} catch (TownyException e1) {
			return null;
		}

	}

	@Override
	public boolean isSingle() {
		return true;
	}

}

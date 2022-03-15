package kr.madesv.towny_ext.skript.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
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
		return "[sharpsk] [towny] nation of %player%";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {

		String natname = null;
		Resident pl = new Resident(resident.getSingle(e).getName());
		for (Nation a : TownyUniverse.getInstance().getNations()) {
			if (a.getResidents().contains(pl)) {
				natname = a.getName();
				break;
			}
		}

		return new String[] { natname };
	}

	@Override
	public boolean isSingle() {
		return true;
	}

}

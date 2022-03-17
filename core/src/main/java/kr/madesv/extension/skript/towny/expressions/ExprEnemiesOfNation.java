package kr.madesv.extension.skript.towny.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.TownyObject;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ExprEnemiesOfNation extends SimpleExpression<String> {
	private Expression<String> nation;

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult Result) {
		nation = (Expression<String>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[towny] enemies of %string%";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		try {
			ArrayList<String> narr = new ArrayList<String>();
			for (Nation enemy : TownyUniverse.getInstance().getNation(nation.getSingle(e)).getEnemies()) {
				narr.add(enemy.getName());
			}
			return narr.toArray(new String[narr.size()]);
		} catch (NullPointerException ignored) {
			return null;
		}
	}

	@Override
	public boolean isSingle() {
		return false;
	}
}

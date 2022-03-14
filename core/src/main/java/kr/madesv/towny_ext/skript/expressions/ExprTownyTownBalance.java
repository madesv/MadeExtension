package kr.madesv.towny_ext.skript.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprTownyTownBalance extends SimpleExpression<Number> {

	private Expression<String> town;

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult Result) {
		town = (Expression<String>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[sharpsk] [towny] balance of town %string%";
	}

	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[] { TownyUniverse.getInstance().getTown(town.getSingle(e)).getAccount().getHoldingBalance() };
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			try {
				TownyUniverse.getInstance().getTown(town.getSingle(e)).getAccount().setBalance(((Number) delta[0]).doubleValue(),
						"Server");

			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			return CollectionUtils.array(new Class[] { Number.class });
		}
		return null;
	}

}

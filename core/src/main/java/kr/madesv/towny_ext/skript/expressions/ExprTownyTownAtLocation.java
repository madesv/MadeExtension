package kr.madesv.towny_ext.skript.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.WorldCoord;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

public class ExprTownyTownAtLocation extends SimpleExpression<String> {

	private Expression<Location> loc;

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult Result) {
		loc = (Expression<Location>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[sharpsk] [towny] town at %location%";
	}

	@SneakyThrows
	@Override
	@Nullable
	protected String[] get(Event e) {
		try {
			return new String[]{TownyUniverse.getInstance().getTownBlock(WorldCoord.parseWorldCoord(loc.getSingle(e))).getName()};
		} catch (NotRegisteredException ignored) {
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

}

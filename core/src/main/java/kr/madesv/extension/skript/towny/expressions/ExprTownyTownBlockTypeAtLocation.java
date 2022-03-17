package kr.madesv.extension.skript.towny.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.TownBlockType;
import com.palmergames.bukkit.towny.object.WorldCoord;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ExprTownyTownBlockTypeAtLocation extends SimpleExpression<TownBlockType> {
	private Expression<Location> loc;

	@Override
	public Class<? extends TownBlockType> getReturnType() {
		return TownBlockType.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[madesv] [towny] [town] (block|plot)type at %location%";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		loc = (Expression<Location>) expr[0];
		return true;
	}

	@NotNull
	@Override
	protected TownBlockType[] get(Event e) {
		try {
			return new TownBlockType[] { TownyUniverse.getInstance().getTownBlock(WorldCoord.parseWorldCoord(loc.getSingle(e))).getType() };
		} catch (NullPointerException | NotRegisteredException ex) {
			return new TownBlockType[] {};
		}
	}

	@SneakyThrows
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			try {
				TownyUniverse.getInstance().getTownBlock(WorldCoord.parseWorldCoord(loc.getSingle(e))).setType((TownBlockType) delta[0]);
			} catch (NotRegisteredException ignored) {
			}
		}
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			return CollectionUtils.array(new Class[] { TownBlockType.class });
		}
		return null;
	}

}

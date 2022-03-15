package kr.madesv.towny_ext.skript.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.event.*;
import com.palmergames.bukkit.towny.event.town.TownUnclaimEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

public class ExprTownyEventTown extends SimpleExpression<String> {

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "event-town";
	}

	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		if (!ScriptLoader.isCurrentEvent(TownAddResidentEvent.class)
				&& !ScriptLoader.isCurrentEvent(TownRemoveResidentEvent.class)
				&& !ScriptLoader.isCurrentEvent(RenameTownEvent.class)
				&& !ScriptLoader.isCurrentEvent(DeleteTownEvent.class)
				&& !ScriptLoader.isCurrentEvent(NewTownEvent.class)
				&& !ScriptLoader.isCurrentEvent(TownClaimEvent.class)
				&& !ScriptLoader.isCurrentEvent(TownUnclaimEvent.class)
				&& !ScriptLoader.isCurrentEvent(TownAddResidentEvent.class)
				&& !ScriptLoader.isCurrentEvent(TownRemoveResidentEvent.class)
				&& !ScriptLoader.isCurrentEvent(NationAddTownEvent.class)
				&& !ScriptLoader.isCurrentEvent(NationRemoveTownEvent.class)) {
			return false;
		}
		return true;
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		if (e.getEventName().equals("TownAddResidentEvent")) {
			return new String[] { ((TownAddResidentEvent) e).getTown().getName() };
		} else if (e.getEventName().equals("TownRemoveResidentEvent")) {
			return new String[] { ((TownRemoveResidentEvent) e).getTown().getName() };
		} else if (e.getEventName().equals("RenameTownEvent")) {
			return new String[] { ((RenameTownEvent) e).getTown().getName() };
		} else if (e.getEventName().equals("DeleteTownEvent")) {
			return new String[] { ((DeleteTownEvent) e).getTownName() };
		} else if (e.getEventName().equals("NewTownEvent")) {
			return new String[] { ((NewTownEvent) e).getTown().getName() };
		} else if (e.getEventName().equals("TownClaimEvent")) {
			try {
				return new String[] { ((TownClaimEvent) e).getTownBlock().getTown().getName() };
			} catch (NotRegisteredException e1) {
				return new String[] {};
			}
		} else if (e.getEventName().equals("TownUnclaimEvent")) {
			return new String[] { ((TownUnclaimEvent) e).getTown().getName() };
		} else if (e.getEventName().equals("TownAddResidentEvent")) {
			return new String[] { ((TownAddResidentEvent) e).getTown().getName() };
		} else if (e.getEventName().equals("NationAddTownEvent")) {
			return new String[] { ((NationAddTownEvent) e).getTown().getName() };
		} else if (e.getEventName().equals("NationRemoveTownEvent")) {
			return new String[] { ((NationRemoveTownEvent) e).getTown().getName() };
		}
		return null;
	}

}

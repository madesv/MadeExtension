package kr.madesv.towny_ext.skript.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.event.*;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

public class ExprTownyEventNation extends SimpleExpression<String> {

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
		return "event-nation";
	}

	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		if (!ScriptLoader.isCurrentEvent(RenameNationEvent.class)
				&& !ScriptLoader.isCurrentEvent(DeleteNationEvent.class)
				&& !ScriptLoader.isCurrentEvent(NewNationEvent.class)
				&& !ScriptLoader.isCurrentEvent(NationAddTownEvent.class)
				&& !ScriptLoader.isCurrentEvent(NationRemoveTownEvent.class)) {
			return false;
		}
		return true;
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		if (e.getEventName().equals("RenameNationEvent")) {
			return new String[] { ((RenameNationEvent) e).getNation().getName() };
		} else if (e.getEventName().equals("DeleteNationEvent")) {
			return new String[] { ((DeleteNationEvent) e).getNationName() };
		} else if (e.getEventName().equals("NewNationEvent")) {
			return new String[] { ((NewNationEvent) e).getNation().getName() };
		} else if (e.getEventName().equals("NationAddTownEvent")) {
			return new String[] { ((NationAddTownEvent) e).getNation().getName() };
		} else if (e.getEventName().equals("NationRemoveTownEvent")) {
			return new String[] { ((NationRemoveTownEvent) e).getNation().getName() };
		}
		return null;
	}

}

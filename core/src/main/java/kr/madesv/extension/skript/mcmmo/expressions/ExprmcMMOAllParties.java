package kr.madesv.extension.skript.mcmmo.expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.datatypes.party.Party;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprmcMMOAllParties extends SimpleExpression<String> {

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[mcmmo] [(the|all)] [of] [the] party['s]";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		ArrayList<String> parties = new ArrayList<>();
		for (Party p : PartyAPI.getParties()) {
			if (p != null) {
				parties.add(p.getName());
			}
		}
		return parties.toArray(new String[parties.size()]);
	}

}

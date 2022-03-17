package kr.madesv.extension.skript.mcmmo.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.nossr50.events.chat.McMMOPartyChatEvent;
import com.gmail.nossr50.events.party.McMMOPartyLevelUpEvent;
import com.gmail.nossr50.events.party.McMMOPartyXpGainEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprEvtParty extends SimpleExpression<String> {

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
		return "event-[mcmmo]party";
	}

	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		if (!ScriptLoader.isCurrentEvent(McMMOPartyChatEvent.class)
				&& !ScriptLoader.isCurrentEvent(McMMOPartyLevelUpEvent.class)
				&& !ScriptLoader.isCurrentEvent(McMMOPartyXpGainEvent.class)) {
			return false;
		}
		return true;
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		if (e.getEventName().equals("McMMOPartyChatEvent")) {
			return new String[] { ((McMMOPartyChatEvent) e).getParty() };
		} else if (e.getEventName().equals("McMMOPartyLevelUpEvent")) {
			return new String[] { ((McMMOPartyLevelUpEvent) e).getParty().getName() };
		} else if (e.getEventName().equals("McMMOPartyXpGainEvent")) {
			return new String[] { ((McMMOPartyXpGainEvent) e).getParty().getName() };
		}
		return null;
	}

}

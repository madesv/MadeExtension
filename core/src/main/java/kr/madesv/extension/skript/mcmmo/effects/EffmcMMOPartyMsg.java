package kr.madesv.extension.skript.mcmmo.effects;

import javax.annotation.Nullable;

import com.gmail.nossr50.datatypes.party.Party;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.party.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.ChatAPI;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffmcMMOPartyMsg extends Effect {
	private Expression<String> s;
	private Expression<String> s2;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		s = (Expression<String>) expr[0];
		s2 = (Expression<String>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[mcmmo] send %string% to party %string%";
	}

	@Override
	protected void execute(Event e) {
		try {
			Party party = PartyManager.getParty(s2.getSingle(e));
			mcMMO.p.getChatManager().processConsoleMessage(s.getSingle(e), party);
		} catch (NullPointerException ignored) {
		}
	}
}

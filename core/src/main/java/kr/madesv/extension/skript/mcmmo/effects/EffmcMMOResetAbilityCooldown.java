package kr.madesv.extension.skript.mcmmo.effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.nossr50.datatypes.skills.SuperAbilityType;
import com.gmail.nossr50.util.player.UserManager;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffmcMMOResetAbilityCooldown extends Effect {
	private Expression<Player> player;
	private Expression<SuperAbilityType> ab;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		ab = (Expression<SuperAbilityType>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[mcmmo] reset cooldown of %abilitytype% of %player%";
	}

	@Override
	protected void execute(Event e) {
		UserManager.getPlayer(player.getSingle(e)).setAbilityDATS(ab.getSingle(e), 0);
	}
}

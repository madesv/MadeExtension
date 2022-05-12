package kr.madesv.extension.skript.towny.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ExprTownyFriends extends SimpleExpression<OfflinePlayer> {

	private Expression<Player> player;

	@Override
	public Class<? extends OfflinePlayer> getReturnType() {
		return OfflinePlayer.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult Result) {
		this.player = (Expression<Player>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[towny] (all) friends of %player%";
	}

	@Override
	@Nullable
	protected OfflinePlayer[] get(Event e) {
		ArrayList<OfflinePlayer> narr = new ArrayList<>();
		for (Resident resident : TownyUniverse.getInstance().getResident(player.getSingle(e).getUniqueId()).getFriends()) {
			narr.add(resident.getPlayer());
		}
		return narr.toArray(new OfflinePlayer[narr.size()]);

	}

	@Override
	public boolean isSingle() {
		return false;
	}

}

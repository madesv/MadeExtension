package kr.madesv.extension.skript.mcmmo.expressions;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprmcMMOSkillLevelReXP extends SimpleExpression<Integer> {
	private Expression<OfflinePlayer> p;
	private Expression<PrimarySkillType> s;

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
		s = (Expression<PrimarySkillType>) expr[0];
		p = (Expression<OfflinePlayer>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[sharpsk] [mcmmo] remaining %skilltype% [e]xp[erience] of %offlineplayer%";
	}

	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if (p == null) {
			return new Integer[] { 0 };
		}
		;
		if (p.getSingle(e).isOnline()) {
			return new Integer[] {
					ExperienceAPI.getXPRemaining(p.getSingle(e).getPlayer(), s.getSingle(e).toString()) };
		} else {
			return new Integer[] { (int) ExperienceAPI.getOfflineXPRemaining(p.getSingle(e).getUniqueId(),
					s.getSingle(e).toString()) };
		}
	}

}

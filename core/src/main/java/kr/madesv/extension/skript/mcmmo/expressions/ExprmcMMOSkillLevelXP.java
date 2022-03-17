package kr.madesv.extension.skript.mcmmo.expressions;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprmcMMOSkillLevelXP extends SimpleExpression<Number> {
	private Expression<OfflinePlayer> p;
	private Expression<PrimarySkillType> s;

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			SkriptParser.ParseResult paramParseResult) {
		s = (Expression<PrimarySkillType>) expr[0];
		p = (Expression<OfflinePlayer>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[sharpsk] [mcmmo] %skilltype% [e]xp[erience] of %offlineplayer%";
	}

	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if (p == null) {
			return new Integer[] { 0 };
		}
		;
		if (p.getSingle(e).isOnline()) {
			return new Integer[] { ExperienceAPI.getXP(p.getSingle(e).getPlayer(), s.getSingle(e).toString()) };
		} else {
			return new Integer[] {
					ExperienceAPI.getOfflineXP(p.getSingle(e).getUniqueId(), s.getSingle(e).toString()) };
		}
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (p == null) {
			return;
		}
		if (mode == Changer.ChangeMode.SET) {
			if (p.getSingle(e).isOnline()) {
				Number level = (Number) delta[0];
				ExperienceAPI.setXP(p.getSingle(e).getPlayer(), s.getSingle(e).toString(), level.intValue());
			} else {
				Number level = (Number) delta[0];
				ExperienceAPI.setXPOffline(p.getSingle(e).getUniqueId(), s.getSingle(e).toString(), level.intValue());
			}
		}
		if (mode == Changer.ChangeMode.ADD) {
			if (p.getSingle(e).isOnline()) {
				Number level = (Number) delta[0];
				ExperienceAPI.addRawXP(p.getSingle(e).getPlayer(), s.getSingle(e).toString(), level.intValue(),
						"Command");
			} else {
				Number level = (Number) delta[0];
				ExperienceAPI.addRawXPOffline(p.getSingle(e).getUniqueId(), s.getSingle(e).toString(),
						level.floatValue());
			}
		}
		if (mode == Changer.ChangeMode.REMOVE) {
			if (p.getSingle(e).isOnline()) {
				Number level = (Number) delta[0];
				if (!(ExperienceAPI.getXP(p.getSingle(e).getPlayer(), s.getSingle(e).toString()) <= 0)) {
					ExperienceAPI.removeXP(p.getSingle(e).getPlayer(), s.getSingle(e).toString(), +level.intValue());
				} else {
					ExperienceAPI.setXP(p.getSingle(e).getPlayer(), s.getSingle(e).toString(), 0);
				}

			} else {
				Number level = (Number) delta[0];
				if (!(ExperienceAPI.getXP(p.getSingle(e).getPlayer(), s.getSingle(e).toString()) <= 0)) {
					ExperienceAPI.removeXPOffline((p.getSingle(e).getUniqueId()), s.getSingle(e).toString(),
							+level.intValue());
				} else {
					ExperienceAPI.setXPOffline(p.getSingle(e).getUniqueId(), s.getSingle(e).toString(), 0);
				}
			}
		}
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(new Class[] { Number.class });
		if (mode == Changer.ChangeMode.ADD)
			return CollectionUtils.array(new Class[] { Number.class });
		if (mode == Changer.ChangeMode.REMOVE)
			return CollectionUtils.array(new Class[] { Number.class });
		return null;
	}
}

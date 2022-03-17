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

public class ExprmcMMOSkillLevel extends SimpleExpression<Number> {
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
		return "[mcmmo] %skilltype% level of %offlineplayer%";
	}

	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if (p == null) {
			return new Integer[] { 0 };
		}
		if (p.getSingle(e).isOnline()) {
			return new Integer[] {
					ExperienceAPI.getLevel(p.getSingle(e).getPlayer(), (String) s.getSingle(e).toString()) };
		} else {
			return new Integer[] {
					ExperienceAPI.getLevelOffline(p.getSingle(e).getUniqueId(), (String) s.getSingle(e).toString()) };
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
				ExperienceAPI.setLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString(), level.intValue());
			}
		}
		if (mode == Changer.ChangeMode.ADD) {
			if (p.getSingle(e).isOnline()) {
				Number level = (Number) delta[0];
				ExperienceAPI.addLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString(), level.intValue());
			}
		}
		if (mode == Changer.ChangeMode.REMOVE) {
			if (p.getSingle(e).isOnline()) {
				Number level = (Number) delta[0];
				if (ExperienceAPI.getLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString()) > 0) {
					if (ExperienceAPI.getLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString()) < level
							.intValue()) {
						ExperienceAPI.setLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString(), 0);
					} else {
						ExperienceAPI.setLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString(),
								ExperienceAPI.getLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString())
										- level.intValue());
					}
				}
			} else {
				Number level = (Number) delta[0];
				if (ExperienceAPI.getLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString()) > 0) {
					if (ExperienceAPI.getLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString()) < level
							.intValue()) {
						ExperienceAPI.setLevel(p.getSingle(e).getPlayer(), s.getSingle(e).toString(), 0);
					} else {
						ExperienceAPI.setLevelOffline(p.getSingle(e).getUniqueId(), s.getSingle(e).toString(),
								ExperienceAPI.getLevelOffline(p.getSingle(e).getUniqueId(), s.getSingle(e).toString())
										- level.intValue());
					}
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

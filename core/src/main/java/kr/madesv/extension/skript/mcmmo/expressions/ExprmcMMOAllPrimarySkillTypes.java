package kr.madesv.extension.skript.mcmmo.expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.nossr50.datatypes.skills.PrimarySkillType;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprmcMMOAllPrimarySkillTypes extends SimpleExpression<PrimarySkillType> {

	boolean skilltype;

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends PrimarySkillType> getReturnType() {
		return PrimarySkillType.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
			final ParseResult parseResult) {
		skilltype = parseResult.mark == 1 || matchedPattern == 1;
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "all [of|the] skill[]types";
	}

	@Override
	@Nullable
	protected PrimarySkillType[] get(Event e) {
		ArrayList<PrimarySkillType> skilltypes = new ArrayList<>();
		for (PrimarySkillType p : PrimarySkillType.values()) {
			if (p != null) {
				skilltypes.add(p);
			}
		}
		return skilltypes.toArray(new PrimarySkillType[skilltypes.size()]);
	}

	@Override
	public boolean isLoopOf(final String s) {
		return skilltype && (s.equalsIgnoreCase("skilltype"));
	}
}

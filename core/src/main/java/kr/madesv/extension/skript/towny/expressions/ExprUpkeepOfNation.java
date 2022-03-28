package kr.madesv.extension.skript.towny.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.TownyUniverse;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprUpkeepOfNation extends SimpleExpression<Number> {

    private Expression<String> nation;

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean,
                        SkriptParser.ParseResult Result) {
        nation = (Expression<String>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean paramBoolean) {
        return "[madesv] [towny] upkeep of nation %string%";
    }

    @Override
    @Nullable
    protected Number[] get(Event e) {
        return new Number[] {
                TownySettings.getNationUpkeepCost(TownyUniverse.getInstance().getNation(nation.getSingle(e)))
        };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            try {
                TownyUniverse.getInstance().getTown(nation.getSingle(e)).getAccount().setBalance(((Number) delta[0]).doubleValue(),
                        "Server");

            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(new Class[] { Number.class });
        }
        return null;
    }

}

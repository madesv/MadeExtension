package kr.madesv.extension.skript.towny.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.Objects;

public class ExprTownyKingOfNation extends SimpleExpression<OfflinePlayer> {
    private Expression<String> nation;

    @Override
    public Class<? extends OfflinePlayer> getReturnType() {
        return OfflinePlayer.class;
    }

    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, SkriptParser.ParseResult Result) {
        this.nation = (Expression<String>) expr[0];
        return true;
    }

    @Override
    public String toString(Event e, boolean paramBoolean) {
        return "king of nation %string%";
    }

    @Override
    public boolean isSingle() {
        return true;
    }


    @Override
    @Nullable
    protected OfflinePlayer[] get(Event e) {
        return new OfflinePlayer[] { Bukkit.getOfflinePlayer(TownyUniverse.getInstance().getNation(nation.getSingle(e)).getKing().getPlayer().getUniqueId()) };

    }

    @SneakyThrows
    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            try {
                TownyUniverse.getInstance().getNation(nation.getSingle(e))
                        .setKing(TownyUniverse.getInstance().getResident(((OfflinePlayer) delta[0]).getName()));
            } catch (TownyException ignored) {
            }
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(new Class[] { OfflinePlayer.class });
        }
        return null;
    }

}
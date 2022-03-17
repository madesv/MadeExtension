package kr.madesv.extension.skript.towny.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.TownyChat.Chat;
import com.palmergames.bukkit.TownyChat.channels.Channel;
import kr.madesv.extension.MadeExtensionPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class ExprPlayerChannel extends SimpleExpression {

    private Expression<Player> player;

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, SkriptParser.ParseResult Result) {
        this.player = (Expression<Player>) expr[0];
        return true;
    }

    @Override
    public String toString(Event e, boolean paramBoolean) {
        return "channel of %string%";
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Nullable
    @Override
    protected String[] get(Event e) {
        Player player = this.player.getSingle(e);
        Chat townyChat = (Chat) Bukkit.getServer().getPluginManager().getPlugin("TownyChat");
        ArrayList<String> narr = new ArrayList<String>();
        for (Channel channel :townyChat.getChannelsHandler().getAllChannels().values()) {
            if (channel.isPresent(player.getName())) {
               narr.add(channel.getName());
            }
        }

        return narr.toArray(new String[narr.size()]);
    }


}

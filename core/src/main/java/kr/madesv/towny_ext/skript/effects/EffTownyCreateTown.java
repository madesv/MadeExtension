package kr.madesv.towny_ext.skript.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.InvalidNameException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.*;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.inject.Inject;

;import java.util.Objects;
import java.util.logging.Logger;

public class EffTownyCreateTown extends Effect {
    private Expression<String> s;
    private Expression<Number> sb;
    private Expression<Location> homespawn;
    private Expression<OfflinePlayer> owner;
    private Expression<OfflinePlayer> members;

    @Inject
    Logger logger;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int matchedPattern, @NotNull Kleenean paramKleenean,
                        @NotNull SkriptParser.ParseResult paramParseResult) {
        s = (Expression<String>) expr[0];
        homespawn = (Expression<Location>) expr[1];
        sb = (Expression<Number>) expr[2];
        owner = (Expression<OfflinePlayer>) expr[3];
        members = (Expression<OfflinePlayer>) expr[4];

        return true;
    }

    @NotNull
    @Override
    public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
        return "[towny] create town %string% at %location% [with [starting] balance %-number%] [[and] with mayor %-offlineplayer%] [and residents %-offlineplayers%]";
    }

    @Override
    protected void execute(Event e) {
        try {

            TownyWorld world = TownyUniverse.getInstance().getWorld(homespawn.getSingle(e).getWorld().getName());
            Coord loc = Coord.parseCoord(homespawn.getSingle(e));

            // unchecked, vjh0107
            TownyUniverse.getInstance().addTownBlock(TownyUniverse.getInstance().getTownBlock(world.getTownBlock(loc).getWorldCoord()));

            TownyUniverse.getInstance().newTown(Objects.requireNonNull(s.getSingle(e)));
            Town town = TownyUniverse.getInstance().getTown(s.getSingle(e));
            if (owner != null) {
                Resident resident = TownyUniverse.getInstance().getResident(owner.getSingle(e).getName());
                resident.setTown(town);
                town.setMayor(resident);
                TownyUniverse.getInstance().getDataSource().saveResident(resident);
            }
            if (members != null) {

                for (OfflinePlayer member : members.getAll(e)) {
                    Resident loopresident = TownyUniverse.getInstance().getResident(member.getName());
                    loopresident.setTown(town);
                    TownyUniverse.getInstance().getDataSource().saveResident(loopresident);
                }

            }

            TownBlock TB = world.getTownBlock(loc);
            TB.setTown(town);
            town.setHomeBlock(TB);

            TB.setType(TB.getType());
            town.setSpawn(homespawn.getSingle(e));
            if (sb != null) {
                town.getAccount().setBalance(sb.getSingle(e).doubleValue(), "Town Creation");
            } else {
                town.getAccount().setBalance(0, "Town Creation");
            }

            TownyUniverse.getInstance().getDataSource().saveTownBlock(TB);
            TownyUniverse.getInstance().getDataSource().saveTown(town);
            TownyUniverse.getInstance().getDataSource().saveWorld(world);

            TownyUniverse.getInstance().getDataSource().saveTowns();
            TownyUniverse.getInstance().getDataSource().saveTownBlocks();

        } catch (NotRegisteredException ex1) {
            logger.warning("Could not register town: " + "\"" + s.getSingle(e) + "\"");
        } catch (AlreadyRegisteredException ex2) {
            logger.warning("Could not register town: " + "\"" + s.getSingle(e) + "\"" + ". Town already exists");
        } catch (InvalidNameException invalidNameException) {
            invalidNameException.printStackTrace();
        }
    }
}

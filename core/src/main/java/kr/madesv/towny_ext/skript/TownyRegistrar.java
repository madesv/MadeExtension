package kr.madesv.towny_ext.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.palmergames.bukkit.towny.event.*;
import com.palmergames.bukkit.towny.event.damage.TownyPlayerDamagePlayerEvent;
import com.palmergames.bukkit.towny.event.town.TownUnclaimEvent;
import com.palmergames.bukkit.towny.object.TownBlockType;
import com.palmergames.bukkit.towny.object.TownBlockTypeHandler;
import kr.madesv.towny_ext.skript.effects.*;
import kr.madesv.towny_ext.skript.expressions.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class TownyRegistrar {

    public static void RegisterTowny() {
        // Town Types:
        Classes.registerClass(new ClassInfo<TownBlockType>(TownBlockType.class, "townblocktype").name("TownBlockType")
                .parser(new Parser<TownBlockType>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    @Nullable
                    public TownBlockType parse(String s, ParseContext cont) {
                        try {
                            return TownBlockTypeHandler.getType(s.replace(" ", "_").trim().toUpperCase());
                        } catch (IllegalArgumentException e) {
                            return null;
                        }
                    }

                    @Override
                    public boolean canParse(final ParseContext cont) {
                        return true;
                    }

                    @Override
                    public String toString(TownBlockType tbt, int i) {
                        return tbt.getName().replace("_", " ").toUpperCase();
                    }

                    @Override
                    public String toVariableNameString(TownBlockType tbt) {
                        return tbt.getName().replace("_", " ").toUpperCase();
                    }
                })); // .serializer(new EnumSerializer<TownBlockType>(TownBlockType.class)).user("town ?blocktypes?"));

        Converters.registerConverter(TownBlockType.class, String.class, new Converter<TownBlockType, String>() {

                    @Override
                    @Nullable
                    public String convert(TownBlockType tbt) {
                        return tbt.getName();
                    }
                }

        );

        // Towny Events:
        Skript.registerEvent("Towny Mob Removal", SimpleEvent.class, MobRemovalEvent.class,
                "[towny] mob remov([al]|ed])");
        EventValues.registerEventValue(MobRemovalEvent.class, Entity.class, new Getter<Entity, MobRemovalEvent>() {
            @Override
            @Nullable
            public Entity get(MobRemovalEvent e) {
                Entity en = e.getEntity();
                return en;
            }
        }, 0);
        Skript.registerEvent("Towny Nation Create", SimpleEvent.class, NewNationEvent.class,
                "[towny] nation create[d]");
        Skript.registerEvent("Towny Nation Delete", SimpleEvent.class, DeleteNationEvent.class,
                "[towny] nation delete[d]");
        Skript.registerEvent("Towny Town Delete", SimpleEvent.class, DeleteTownEvent.class, "[towny] town delete[d]");
        Skript.registerEvent("Towny Nation Add Town", SimpleEvent.class, NationAddTownEvent.class,
                "[towny] nation town add[ed]");
        Skript.registerEvent("Towny Nation Town Remove", SimpleEvent.class, NationRemoveTownEvent.class,
                "[towny] nation town remove[d]");
        Skript.registerEvent("Towny Town Create", SimpleEvent.class, NewTownEvent.class, "[towny] town create[d]");
        Skript.registerEvent("Towny Resident Rename", SimpleEvent.class, RenameResidentEvent.class,
                "[towny] resident rename[d]");
        EventValues.registerEventValue(RenameResidentEvent.class, String.class,
                new Getter<String, RenameResidentEvent>() {
                    @Override
                    @Nullable
                    public String get(RenameResidentEvent e) {
                        String s = e.getOldName();

                        return s;
                    }
                }, 0);
        Skript.registerEvent("Towny Nation Rename", SimpleEvent.class, RenameNationEvent.class,
                "[towny] nation rename[d]");
        EventValues.registerEventValue(RenameNationEvent.class, String.class, new Getter<String, RenameNationEvent>() {
            @Override
            @Nullable
            public String get(RenameNationEvent e) {
                String s = e.getOldName();
                return s;
            }
        }, 0);
        Skript.registerEvent("Towny Town Rename", SimpleEvent.class, RenameTownEvent.class, "[towny] town rename[d]");
        EventValues.registerEventValue(RenameTownEvent.class, String.class, new Getter<String, RenameTownEvent>() {
            @Override
            @Nullable
            public String get(RenameTownEvent e) {
                String s = e.getOldName();
                return s;
            }
        }, 0);
        Skript.registerEvent("Towny Nation Rename", SimpleEvent.class, RenameNationEvent.class,
                "[towny] nation rename[d]");
        EventValues.registerEventValue(RenameNationEvent.class, String.class, new Getter<String, RenameNationEvent>() {
            @Override
            @Nullable
            public String get(RenameNationEvent e) {
                String s = e.getOldName();
                return s;
            }
        }, 0);
        Skript.registerEvent("Towny Town Claim", SimpleEvent.class, TownClaimEvent.class, "[towny] town claim[ed]");
        Skript.registerEvent("Towny Town Unclaim", SimpleEvent.class, TownUnclaimEvent.class,
                "[towny] town unclaime[d]");
        Skript.registerEvent("Towny Resident Add", SimpleEvent.class, TownAddResidentEvent.class,
                "[towny] resident add[ed]");
        EventValues.registerEventValue(TownAddResidentEvent.class, String.class,
                new Getter<String, TownAddResidentEvent>() {
                    @Override
                    @Nullable
                    public String get(TownAddResidentEvent e) {
                        String s = e.getResident().getName();

                        return s;
                    }
                }, 0);
        Skript.registerEvent("Towny Resident Remove", SimpleEvent.class, TownRemoveResidentEvent.class,
                "[towny] resident remove[d]");
        EventValues.registerEventValue(TownRemoveResidentEvent.class, String.class,
                new Getter<String, TownRemoveResidentEvent>() {
                    @Override
                    @Nullable
                    public String get(TownRemoveResidentEvent e) {
                        String s = e.getResident().getName();
                        return s;
                    }
                }, 0);
        Skript.registerEvent("Towny Plot Clear", SimpleEvent.class, PlotClearEvent.class, "[towny] plot clear[ed]");
        Skript.registerEvent("Towny Player Plot Change", SimpleEvent.class, PlayerChangePlotEvent.class,
                "[towny] plot change[d]");
        EventValues.registerEventValue(PlayerChangePlotEvent.class, Player.class,
                new Getter<Player, PlayerChangePlotEvent>() {
                    @Override
                    @Nullable
                    public Player get(PlayerChangePlotEvent e) {
                        Player p = e.getPlayer();
                        return p;
                    }
                }, 0);
        Skript.registerEvent("Towny PVP Disallow", SimpleEvent.class, TownyPlayerDamagePlayerEvent.class,
                "[towny] pvp disallow[ed]");
        EventValues.registerEventValue(TownyPlayerDamagePlayerEvent.class, Player.class,
                new Getter<Player, TownyPlayerDamagePlayerEvent>() {
                    @Override
                    @Nullable
                    public Player get(TownyPlayerDamagePlayerEvent e) {
                        Player p = e.getAttackingPlayer();
                        return p;
                    }
                }, 0);

        Skript.registerEvent("Towny Town Block Settings Change", SimpleEvent.class, TownBlockSettingsChangedEvent.class,
                "[towny] town block settings chang(e|ed)");

        // Towny Expressions:

        Skript.registerExpression(ExprTownyAllNations.class, String.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] (all|the) nations");
        Skript.registerExpression(ExprTownyAllTowns.class, String.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] (all|the) towns");
        Skript.registerExpression(ExprTownyTownAtLocation.class, String.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] town at %location%");
        Skript.registerExpression(ExprTownyNationBalance.class, Number.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] [bank] balance of nation %string%");
        Skript.registerExpression(ExprTownyTownBalance.class, Number.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] [bank] balance of town %string%");
        Skript.registerExpression(ExprTownyTownOfPlayer.class, String.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] town of %offlineplayer%");
        Skript.registerExpression(ExprTownyNationOfPlayer.class, String.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] nation of %offlineplayer%");
        Skript.registerExpression(ExprTownyTownBlocktypeAtLocation.class, TownBlockType.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] [town] (block|plot)type at %location%");
        Skript.registerExpression(ExprTownyEventTown.class, String.class, ExpressionType.SIMPLE, "event-town");
        Skript.registerExpression(ExprTownyEventNation.class, String.class, ExpressionType.SIMPLE, "event-nation");
        Skript.registerExpression(ExprTownyMayorOfTown.class, OfflinePlayer.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] mayor of town %string%");
        Skript.registerExpression(ExprAllTownBlockTypes.class, TownBlockType.class, ExpressionType.SIMPLE,
                "[sharpsk] [towny] all [of|the] town[ ]blocktypes");

        // Towny Effects:
        Skript.registerEffect(EffTownyCreateTown.class,
                "[sharpsk] [towny] create town %string% at %location% [with [bank] balance %-number%] [[and] with mayor %-offlineplayer%] [and residents %-offlineplayers%]");
        Skript.registerEffect(EffTownyDeleteTown.class, "[sharpsk] [towny] delete town %string%");
        Skript.registerEffect(EffTownyRenameTown.class, "[sharpsk] [towny] rename town %string% to %string%");
        Skript.registerEffect(EffTownyKickPlayerFromTown.class,
                "[sharpsk] [towny] kick %offlineplayer% from town %string%");
        Skript.registerEffect(EffTownyAddPlayerToTown.class,
                "[sharpsk] [towny] add %offlineplayer% to [town] %string%");
        Skript.registerEffect(EffTownyCreateNation.class,
                "[sharpsk] [towny] create nation %string% (of|in) town %string% [with [bank] balance %-number%]");
        Skript.registerEffect(EffTownyKickPlayerFromNation.class,
                "[sharpsk] [towny] kick %offlineplayer% from nation %string%");
        Skript.registerEffect(EffTownyAddPlayerToNation.class, "[towny] add %offlineplayer% to nation %string%");

    }
}

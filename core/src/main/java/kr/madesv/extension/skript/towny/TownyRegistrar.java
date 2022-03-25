package kr.madesv.extension.skript.towny;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.google.inject.Injector;
import com.palmergames.bukkit.towny.event.*;
import com.palmergames.bukkit.towny.event.damage.TownyPlayerDamagePlayerEvent;
import com.palmergames.bukkit.towny.event.town.TownUnclaimEvent;
import com.palmergames.bukkit.towny.object.TownBlockType;
import com.palmergames.bukkit.towny.object.TownBlockTypeHandler;
import kr.madesv.extension.skript.SkriptRegistrarModule;
import kr.madesv.extension.skript.towny.effects.*;
import kr.madesv.extension.skript.towny.expressions.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class TownyRegistrar extends SkriptRegistrarModule {
    @Override
    public void register() {
        // Town Types:
        Classes.registerClass(new ClassInfo<TownBlockType>(TownBlockType.class, "townblocktype").name("TownBlockType")
                .parser(new Parser<TownBlockType>() {

                    // @Override Skript 2.6.1 에 삭제됨.
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
                "[madesv] [towny] (all|the) nations");
        Skript.registerExpression(ExprTownyAllTowns.class, String.class, ExpressionType.SIMPLE,
                "[madesv] [towny] (all|the) towns");
        Skript.registerExpression(ExprTownyTownAtLocation.class, String.class, ExpressionType.SIMPLE,
                "[madesv] [towny] town at %location%");
        Skript.registerExpression(ExprTownyNationBalance.class, Number.class, ExpressionType.SIMPLE,
                "[madesv] [towny] [bank] balance of nation %string%");
        Skript.registerExpression(ExprTownyTownBalance.class, Number.class, ExpressionType.SIMPLE,
                "[madesv] [towny] [bank] balance of town %string%");
        Skript.registerExpression(ExprTownyTownOfPlayer.class, String.class, ExpressionType.SIMPLE,
                "[madesv] [towny] town of %offlineplayer%");
        Skript.registerExpression(ExprTownyNationOfPlayer.class, String.class, ExpressionType.SIMPLE,
                "[madesv] [towny] nation of %offlineplayer%");
        Skript.registerExpression(ExprTownyTownBlockTypeAtLocation.class, TownBlockType.class, ExpressionType.SIMPLE,
                "[madesv] [towny] [town] (block|plot)type at %location%");
        Skript.registerExpression(ExprTownyEventTown.class, String.class, ExpressionType.SIMPLE, "event-town");
        Skript.registerExpression(ExprTownyEventNation.class, String.class, ExpressionType.SIMPLE, "event-nation");
        Skript.registerExpression(ExprTownyMayorOfTown.class, OfflinePlayer.class, ExpressionType.SIMPLE,
                "[madesv] [towny] mayor of town %string%");
        Skript.registerExpression(ExprAllTownBlockTypes.class, TownBlockType.class, ExpressionType.SIMPLE,
                "[madesv] [towny] all [of|the] town[ ]blocktypes");
        Skript.registerExpression(ExprTownyKingOfNation.class, OfflinePlayer.class, ExpressionType.SIMPLE,
                "[madesv] king of nation %string%");
        Skript.registerExpression(ExprPlayerChannel.class, String.class, ExpressionType.SIMPLE, new String[]{"channel of %player%"});
        Skript.registerExpression(ExprAlliesOfNation.class, String.class, ExpressionType.SIMPLE, new String[]{"allies of %string%"});
        Skript.registerExpression(ExprEnemiesOfNation.class, String.class, ExpressionType.SIMPLE, new String[]{"enemies of %string%"});


        // Towny Effects:
        Skript.registerEffect(EffTownyCreateTown.class,
                "[madesv] [towny] create town %string% at %location% [with [bank] balance %-number%] [[and] with mayor %-offlineplayer%] [and residents %-offlineplayers%]");
        Skript.registerEffect(EffTownyDeleteTown.class, "[madesv] [towny] delete town %string%");
        Skript.registerEffect(EffTownyRenameTown.class, "[madesv] [towny] rename town %string% to %string%");
        Skript.registerEffect(EffTownyKickPlayerFromTown.class,
                "[madesv] [towny] kick %offlineplayer% from town %string%");
        Skript.registerEffect(EffTownyAddPlayerToTown.class,
                "[madesv] [towny] add %offlineplayer% to [town] %string%");
        Skript.registerEffect(EffTownyCreateNation.class,
                "[madesv] [towny] create nation %string% (of|in) town %string% [with [bank] balance %-number%]");
        Skript.registerEffect(EffTownyKickPlayerFromNation.class,
                "[madesv] [towny] kick %offlineplayer% from nation %string%");
        Skript.registerEffect(EffTownyAddPlayerToNation.class, "[towny] add %offlineplayer% to nation %string%");

    }
}

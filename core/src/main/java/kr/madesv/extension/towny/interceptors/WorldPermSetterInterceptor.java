package kr.madesv.extension.towny.interceptors;

import com.palmergames.bukkit.towny.*;
import com.palmergames.bukkit.towny.command.HelpMenu;
import com.palmergames.bukkit.towny.command.TownyWorldCommand;
import com.palmergames.bukkit.towny.object.TownyWorld;
import com.palmergames.bukkit.towny.object.Translatable;
import com.palmergames.util.StringMgmt;
import kr.madesv.extension.towny.TownyTranslation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class WorldPermSetterInterceptor {
    @RuntimeType
    public static Object intercept(@AllArguments Object[] allArguments, @This Object proxy) throws NoSuchFieldException, IllegalAccessException {
        CommandSender sender = (CommandSender) allArguments[0];
        String[] split = (String[]) allArguments[1];
        TownyWorldCommand townyWorldCommand = (TownyWorldCommand) proxy;

        Field globalWorldField = townyWorldCommand.getClass().getDeclaredField("globalWorld");
        globalWorldField.setAccessible(true);
        TownyWorld globalWorld = (TownyWorld) globalWorldField.get(townyWorldCommand);

        Towny plugin = Towny.getPlugin();

        if (split.length == 0) {
            HelpMenu.TOWNYWORLD_SET.send(sender);
        } else {

            if (split[0].equalsIgnoreCase("usedefault")) {

                globalWorld.setUsingDefault();
                plugin.resetCache();
                TownyMessaging.sendMsg(sender, Translatable.of("msg_usedefault", globalWorld.getName()));

            } else if (split[0].equalsIgnoreCase("wildperm")) {

                if (split.length < 2) {
                    // set default wildperm settings (/tw set wildperm)
                    globalWorld.setUsingDefault();
                    TownyMessaging.sendMsg(sender, Translatable.of("msg_usedefault", globalWorld.getName()));
                } else
                    try {
                        List<String> perms = Arrays.asList(String.join(",", StringMgmt.remFirstArg(split)).toLowerCase(Locale.ROOT).split(","));
                        globalWorld.setUnclaimedZoneBuild(perms.contains(TownyTranslation.BUILD.getTranslatedName()));
                        globalWorld.setUnclaimedZoneDestroy(perms.contains(TownyTranslation.DESTROY.getTranslatedName()));
                        globalWorld.setUnclaimedZoneSwitch(perms.contains(TownyTranslation.SWITCH.getTranslatedName()));
                        globalWorld.setUnclaimedZoneItemUse(perms.contains(TownyTranslation.ITEM_USE.getTranslatedName()));

                        plugin.resetCache();
                        TownyMessaging.sendMsg(sender, Translatable.of("msg_set_wild_perms", globalWorld.getName(), perms.toString()));
                    } catch (Exception e) {
                        TownyMessaging.sendErrorMsg(sender, "Eg: /townyworld set wildperm build destroy <world>");
                    }

            } else if (split[0].equalsIgnoreCase("wildignore")) {

                if (split.length < 2)
                    TownyMessaging.sendErrorMsg(sender, "Eg: /townyworld set wildignore SAPLING,GOLD_ORE,IRON_ORE <world>");
                else
                    try {
                        List<String> mats = new ArrayList<>();
                        for (String s : StringMgmt.remFirstArg(split))
                            mats.add(Material.matchMaterial(s.trim().toUpperCase()).name());

                        globalWorld.setUnclaimedZoneIgnore(mats);

                        plugin.resetCache();
                        TownyMessaging.sendMsg(sender, Translatable.of("msg_set_wild_ignore", globalWorld.getName(), globalWorld.getUnclaimedZoneIgnoreMaterials()));

                    } catch (Exception e) {
                        TownyMessaging.sendErrorMsg(sender, Translatable.of("msg_err_invalid_input", " on/off."));
                    }

            } else if (split[0].equalsIgnoreCase("wildregen")) {

                if (split.length < 2)
                    TownyMessaging.sendErrorMsg(sender, "Eg: /townyworld set wildregen Creeper,EnderCrystal,EnderDragon,Fireball,SmallFireball,LargeFireball,TNTPrimed,ExplosiveMinecart");
                else {

                    String[] entities = String.join(",", StringMgmt.remFirstArg(split)).split(",");
                    List<String> entityList = new ArrayList<>(Arrays.asList(entities));

                    globalWorld.setPlotManagementWildRevertEntities(entityList);

                    TownyMessaging.sendMsg(sender, Translatable.of("msg_set_wild_regen", globalWorld.getName(), globalWorld.getPlotManagementWildRevertEntities()));
                }

            } else if (split[0].equalsIgnoreCase("wildname")) {

                if (split.length < 2) {
                    TownyMessaging.sendErrorMsg(sender, "Eg: /townyworld set wildname Wildy");
                } else
                    globalWorld.setUnclaimedZoneName(split[1]);
                TownyMessaging.sendMsg(sender, Translatable.of("msg_set_wild_name", globalWorld.getName(), split[1]));
            } else if (TownyCommandAddonAPI.hasCommand(TownyCommandAddonAPI.CommandType.TOWNYWORLD_SET, split[0])) {
                try {
                    TownyCommandAddonAPI.getAddonCommand(TownyCommandAddonAPI.CommandType.TOWNYWORLD_SET, split[0]).execute(sender, "townyworld", split);
                } catch (Exception e) {
                    TownyMessaging.sendErrorMsg(sender, e.getMessage());
                }
            } else {
                TownyMessaging.sendErrorMsg(sender, Translatable.of("msg_err_invalid_property", "world"));
                return null;
            }

            globalWorld.save();
        }
        return null;
    }
}

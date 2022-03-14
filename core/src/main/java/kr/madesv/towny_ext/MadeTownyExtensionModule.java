package kr.madesv.towny_ext;

import ch.njol.skript.Skript;
import com.google.inject.*;
import com.palmergames.bukkit.towny.Towny;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Logger;

public class MadeTownyExtensionModule extends AbstractModule {
    private final MadeTownyExtensionPlugin plugin;

    public MadeTownyExtensionModule(MadeTownyExtensionPlugin plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        /**
         * MadeTownyExtensionPlugin 과 Plugin 클래스를 plugin 인스턴스로 bind 한다.
         *
         **/
        bind(MadeTownyExtensionPlugin.class).toInstance(this.plugin);
        bind(Plugin.class).toInstance(this.plugin);

        bind(Logger.class).toInstance(this.plugin.getLogger());
    }

    @Provides
    @Singleton
    Optional<Skript> provideSkript() {
        Plugin plugin = this.plugin.getServer().getPluginManager().getPlugin("Skript");
        return Optional.ofNullable(plugin)
                .map(maybeSkript -> (Skript) maybeSkript);
    }

    @Provides
    @Singleton
    Optional<Towny> provideTowny() {
        Plugin plugin = this.plugin.getServer().getPluginManager().getPlugin("Towny");
        return Optional.ofNullable(plugin)
                .map(maybeTowny -> (Towny) maybeTowny);
    }
}

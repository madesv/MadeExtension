package kr.madesv.extension;

import ch.njol.skript.Skript;
import com.gmail.nossr50.mcMMO;
import com.google.inject.*;
import com.palmergames.bukkit.TownyChat.Chat;
import com.palmergames.bukkit.towny.Towny;
import org.bukkit.plugin.Plugin;
import java.util.Optional;

public class MadeExtensionModule extends AbstractModule {
    private final MadeExtensionPlugin plugin;

    public MadeExtensionModule(MadeExtensionPlugin plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        /*
          MadeTownyExtensionPlugin 과 Plugin 클래스를 plugin 인스턴스로 bind 한다.
         */
        bind(MadeExtensionPlugin.class).toInstance(this.plugin);
    }

    @Provides
    @Singleton
    Optional<Skript> provideSkript() {
        return providePlugin("Skript");
    }

    @Provides
    @Singleton
    Optional<Towny> provideTowny() {
        return providePlugin("Towny");
    }

    @Provides
    @Singleton
    Optional<mcMMO> provideMcMMO() {
        return providePlugin("mcMMO");
    }

    @Provides
    @Singleton
    Optional<Chat> providesTownyChat() { return providePlugin("TownyChat"); }

    private <T> Optional<T> providePlugin(String pluginName) {
        System.out.println("provide plugin called " + pluginName);
        Plugin nullablePlugin = this.plugin.getServer().getPluginManager().getPlugin(pluginName);
        return Optional.ofNullable(nullablePlugin)
                .map(maybePlugin -> (T) maybePlugin);
    }
}

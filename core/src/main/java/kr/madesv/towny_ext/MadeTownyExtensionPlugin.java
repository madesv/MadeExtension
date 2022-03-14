package kr.madesv.towny_ext;

import ch.njol.skript.Skript;
import com.google.inject.Injector;
import com.palmergames.bukkit.towny.Towny;
import kr.madesv.towny_ext.skript.TownyRegistrar;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Level;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class MadeTownyExtensionPlugin extends JavaPlugin {

    @Inject
    Optional<Skript> maybeSkript;

    @Inject
    Optional<Towny> maybeTowny;

    @SneakyThrows
    @Override
    public void onEnable() {
        MadeTownyExtensionModule module = new MadeTownyExtensionModule(this);
        Injector injector = module.createInjector();
        //모듈이 선언되기 전에 필드가 선언되었으니 이 객체에 대하여 injectMembers 를 해줘야 한다.
        injector.injectMembers(this);

        if (maybeSkript.isPresent()) {
            this.getLogger().info("스크립트 플러그인을 성공적으로 로드하였습니다.");
            TownyRegistrar.RegisterTowny();
        }
        if (maybeTowny.isPresent()) {
            this.getLogger().info("타우니 플러그인을 성공적으로 로드하였습니다.");
        }

    }
}

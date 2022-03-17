package kr.madesv.extension;

import ch.njol.skript.Skript;
import com.gmail.nossr50.mcMMO;
import com.google.inject.Injector;
import com.palmergames.bukkit.TownyChat.Chat;
import com.palmergames.bukkit.towny.Towny;
import kr.madesv.extension.skript.mcmmo.McMMORegistrar;
import kr.madesv.extension.skript.towny.TownyRegistrar;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import javax.inject.Inject;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class MadeExtensionPlugin extends JavaPlugin {

    @Inject
    Optional<Skript> maybeSkript;

    @Inject
    Optional<Towny> maybeTowny;

    @Inject
    Optional<mcMMO> maybeMcMMO;

    @Inject
    Optional<Chat> maybeTownyChat;

    @SneakyThrows
    @Override
    public void onEnable() {
        MadeExtensionModule module = new MadeExtensionModule(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        if (maybeSkript.isPresent()) {
            this.getLogger().info("스크립트 플러그인을 성공적으로 로드하였습니다.");
            this.getLogger().info("Extension들을 등록합니다...");

            if (maybeTowny.isPresent() && maybeTownyChat.isPresent()) {
                TownyRegistrar.RegisterTowny();
                this.getLogger().info("Towny와 관련된 Extension을 성공적으로 등록하였습니다.");
                maybeTownyChat.get().getLogger().info("TownyChat 이 성공적으로 MadeExtension과 연동되었습니다.");
            } else {
                this.getLogger().info("§cTowny와 TownyChat를 찾지 못하였습니다.");
            }
            if (maybeMcMMO.isPresent()) {
                McMMORegistrar.registerMcMMO();
                this.getLogger().info("mcMMO와 관련된 Extension을 성공적으로 등록하였습니다.");
            } else {
                this.getLogger().info("§cmcMMO를 찾지 못하였습니다.");
            }
            Skript.registerAddon(this);

        } else {
            this.getLogger().info("§cSkript 플러그인을 찾지 못하였습니다.");
        }
    }
}

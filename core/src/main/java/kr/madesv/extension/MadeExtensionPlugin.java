package kr.madesv.extension;

import ch.njol.skript.Skript;
import com.gmail.nossr50.mcMMO;
import com.google.inject.Injector;
import com.palmergames.bukkit.TownyChat.Chat;
import com.palmergames.bukkit.towny.Towny;
import kr.madesv.extension.skript.SkriptRegistrarComponent;
import kr.madesv.extension.skript.mcmmo.McMMORegistrar;
import kr.madesv.extension.skript.towny.TownyRegistrar;
import kr.madesv.extension.towny.TownyChatProvider;
import kr.madesv.extension.towny.TownyProvider;
import kr.madesv.extension.towny.TownyReflection;
import lombok.Getter;
import lombok.SneakyThrows;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class MadeExtensionPlugin extends JavaPlugin {

    @Inject
    Optional<Skript> maybeSkript;

    @Inject
    Optional<TownyProvider> maybeTowny;

    @Inject
    Optional<mcMMO> maybeMcMMO;

    @Inject
    Optional<TownyChatProvider> maybeTownyChat;

    public static Injector injector;

    public static ClassReloadingStrategy classReloadingStrategy;

    @Getter
    public static MadeExtensionPlugin instance;

    static {
        boolean foundTowny = true;
        try {
            Class.forName("com.palmergames.bukkit.towny.Towny");
        } catch (Exception ignored) {
            foundTowny = false;
        }

        if (foundTowny) {
            ByteBuddyAgent.install();
            classReloadingStrategy = ClassReloadingStrategy.fromInstalledAgent();
            TownyReflection.overridePermission();

            System.out.println("TownyPermission 클래스, TownyWorldCommand 클래스를 redefine 하였습니다.");
        }
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        MadeExtensionModule module = new MadeExtensionModule(this);
        injector = module.createInjector();
        injector.injectMembers(this);

        if (maybeTowny.isPresent()) {
            try {
                TownyReflection.overrideDateFormat();
                this.getLogger().info("Towny의 SimpleDateFormat 필드를 성공적으로 override 하였습니다.");
            } catch (Exception exception) {
                this.getLogger().severe("Towny의 SimpleDateFormat 필드를 override 하는데에 성공한 줄 알았으나 실패하였습니다. Towny의 버전이 호환되지 않아 생기는 문제일 가능성이 높습니다.");
                exception.printStackTrace();
            }
        }

        if (maybeSkript.isPresent()) {
            this.getLogger().info("스크립트 플러그인을 성공적으로 로드하였습니다.");
            this.getLogger().info("Extension들을 등록합니다...");

            if (maybeTowny.isPresent() && maybeTownyChat.isPresent()) {
                registerSkriptRegistrarComponent(new TownyRegistrar());
                this.getLogger().info("Towny와 관련된 Extension을 성공적으로 등록하였습니다.");
            } else {
                this.getLogger().info("§cTowny와 TownyChat를 찾지 못하였습니다.");
            }
            if (maybeMcMMO.isPresent()) {
                registerSkriptRegistrarComponent(new McMMORegistrar());
                this.getLogger().info("mcMMO와 관련된 Extension을 성공적으로 등록하였습니다.");
            } else {
                this.getLogger().info("§cmcMMO를 찾지 못하였습니다.");
            }
            Skript.registerAddon(this);

        } else {
            this.getLogger().info("§cSkript 플러그인을 찾지 못하였습니다.");
        }
    }

    public void registerSkriptRegistrarComponent(SkriptRegistrarComponent registrar) {
        registrar.register(injector);
    }
}

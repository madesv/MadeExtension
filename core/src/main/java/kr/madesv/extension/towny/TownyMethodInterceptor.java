package kr.madesv.extension.towny;

import com.palmergames.bukkit.towny.object.TownyPermission;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

public class TownyMethodInterceptor {
    @RuntimeType
    public static Object intercept(@AllArguments Object[] allArguments, @This Object proxy) {
        String string = (String) allArguments[0];
        TownyPermission townyPermission = (TownyPermission) proxy;

        townyPermission.setAll(false);
        // System.out.println("tokens: " + string);
        String[] tokens = string.split(",");
        for (String token : tokens) {
            String untranslatedToken = TownyTranslation.untranslateAll(token);
            // System.out.println("token: " + token + " untranslated: " + untranslatedToken);
            townyPermission.set(untranslatedToken, true);
        }
        return null;
    }
}

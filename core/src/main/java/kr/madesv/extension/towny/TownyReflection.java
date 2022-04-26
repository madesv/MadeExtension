package kr.madesv.extension.towny;

import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.command.TownyWorldCommand;
import com.palmergames.bukkit.towny.object.TownyPermission;
import kr.madesv.extension.towny.interceptors.PermSetterInterceptor;
import kr.madesv.extension.towny.interceptors.WorldPermSetterInterceptor;
import kr.madesv.extension.utils.reflection.FieldUtil;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import static kr.madesv.extension.MadeExtensionPlugin.classReloadingStrategy;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class TownyReflection {
    @SneakyThrows
    public static void overridePermission() {
        try {
            setActionTypeCommonNameField(TownyPermission.ActionType.BUILD, TownyTranslation.BUILD.getTranslatedName());
            setActionTypeCommonNameField(TownyPermission.ActionType.DESTROY, TownyTranslation.DESTROY.getTranslatedName());
            setActionTypeCommonNameField(TownyPermission.ActionType.SWITCH, TownyTranslation.SWITCH.getTranslatedName());
            setActionTypeCommonNameField(TownyPermission.ActionType.ITEM_USE, TownyTranslation.ITEM_USE.getTranslatedName());

            redefineMethod(TownyPermission.class, "load", PermSetterInterceptor.class);
            redefineMethod(TownyWorldCommand.class, "worldSet", WorldPermSetterInterceptor.class);
        } catch (Exception ignored) {}
    }

    @SuppressWarnings("unchecked")
    private static void redefineMethod(Class clazz, String methodName, Class interceptor) {
        try {
            new ByteBuddy()
                    .redefine(clazz)
                    .method(named(methodName))
                    .intercept(MethodDelegation.to(interceptor))
                    .make()
                    .load(clazz.getClassLoader(), classReloadingStrategy);
        } catch (Exception ignored) {}
    }

    @SneakyThrows
    public static void overrideDateFormat() {
        setDateFormatField("registeredFormat", "yyyy.MM.dd");
        setDateFormatField("lastOnlineFormat", "yyyy.MM.dd HH:mm");
        setDateFormatField("lastOnlineFormatIncludeYear", "yyyy.MM.dd");
        setDateFormatField("fullDateFormat", "yyyy.MM.dd HH:mm");
    }


    private static void setDateFormatField(String fieldName, String format) {
        try {
            Class<TownyFormatter> formatterClazz = TownyFormatter.class;
            Field field = formatterClazz.getDeclaredField(fieldName);
            FieldUtil.setFinalStatic(field, new SimpleDateFormat(format));
        } catch (Exception ignored) {}
    }

    private static void setActionTypeCommonNameField(TownyPermission.ActionType actionType, String newCommonName) {
        try {
            Field field = TownyPermission.ActionType.class.getDeclaredField("commonName");
            if (!field.canAccess(actionType)) {
                field.setAccessible(true);
            }
            field.set(actionType, newCommonName);
        } catch (Exception ignored) {}
    }

}

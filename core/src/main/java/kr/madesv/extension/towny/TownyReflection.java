package kr.madesv.extension.towny;

import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.object.TownyPermission;
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
    public static void overridePermission() throws Exception {
        setActionTypeCommonNameField(TownyPermission.ActionType.BUILD, TownyTranslation.BUILD.getTranslatedName());
        setActionTypeCommonNameField(TownyPermission.ActionType.DESTROY, TownyTranslation.DESTROY.getTranslatedName());
        setActionTypeCommonNameField(TownyPermission.ActionType.SWITCH, TownyTranslation.SWITCH.getTranslatedName());
        setActionTypeCommonNameField(TownyPermission.ActionType.ITEM_USE, TownyTranslation.ITEM_USE.getTranslatedName());

        new ByteBuddy()
                .redefine(TownyPermission.class)
                .method(named("load"))
                .intercept(MethodDelegation.to(TownyMethodInterceptor.class))
                .make()
                .load(TownyPermission.class.getClassLoader(), classReloadingStrategy);
    }
    @SneakyThrows
    public static void overrideDateFormat() throws Exception {
        setDateFormatField("registeredFormat", "yyyy.MM.dd");
        setDateFormatField("lastOnlineFormat", "yyyy.MM.dd HH:mm");
        setDateFormatField("lastOnlineFormatIncludeYear", "yyyy.MM.dd");
        setDateFormatField("fullDateFormat", "yyyy.MM.dd HH:mm");
    }


    private static void setDateFormatField(String fieldName, String format) throws Exception {
        Class<TownyFormatter> formatterClazz = TownyFormatter.class;
        Field field = formatterClazz.getDeclaredField(fieldName);
        FieldUtil.setFinalStatic(field, new SimpleDateFormat(format));
    }

    private static void setActionTypeCommonNameField(TownyPermission.ActionType actionType, String newCommonName) throws Exception {
        Field field = TownyPermission.ActionType.class.getDeclaredField("commonName");
        if (!field.canAccess(actionType)) {
            field.setAccessible(true);
        }
        field.set(actionType, newCommonName);
    }

}

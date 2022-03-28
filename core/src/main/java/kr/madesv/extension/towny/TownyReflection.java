package kr.madesv.extension.towny;

import com.palmergames.bukkit.towny.TownyFormatter;
import com.palmergames.bukkit.towny.object.TownyPermission;
import kr.madesv.extension.utils.reflection.FieldUtil;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

public class TownyReflection {

    private static void setDateFormatField(String fieldName, String format) throws Exception {
        Class<TownyFormatter> formatterClazz = TownyFormatter.class;
        Field field = formatterClazz.getDeclaredField(fieldName);
        FieldUtil.setFinalStatic(field, new SimpleDateFormat(format));
    }

    private static void setPermissionCommonNameField(TownyPermission.ActionType actionType, String newCommonName) throws Exception {
        Field field = TownyPermission.ActionType.class.getDeclaredField("commonName");
        if (!field.canAccess(actionType)) {
            field.setAccessible(true);
        }
        field.set(actionType, newCommonName);
    }

    @SneakyThrows
    public static void override() throws Exception {
        setDateFormatField("registeredFormat", "yyyy.MM.dd");
        setDateFormatField("lastOnlineFormat", "yyyy.MM.dd HH:mm");
        setDateFormatField("lastOnlineFormatIncludeYear", "yyyy.MM.dd");
        setDateFormatField("fullDateFormat", "yyyy.MM.dd HH:mm");

        setPermissionCommonNameField(TownyPermission.ActionType.BUILD, "설치");
        setPermissionCommonNameField(TownyPermission.ActionType.DESTROY, "파괴");
        setPermissionCommonNameField(TownyPermission.ActionType.ITEM_USE, "아이템");
        setPermissionCommonNameField(TownyPermission.ActionType.SWITCH, "스위치");
    }
}

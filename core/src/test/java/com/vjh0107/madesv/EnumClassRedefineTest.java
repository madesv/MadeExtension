package com.vjh0107.madesv;

import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.object.TownyPermission.ActionType;
import kr.madesv.extension.utils.reflection.FieldUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class EnumClassRedefineTest {
    @Test
    public void getDeclaredEnumField() {
        Field[] declaredFields = TownyPermission.ActionType.class.getDeclaredFields();
        for (Field field : declaredFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                System.out.println(field.getName() + ": " + field.getType());
            }
        }
    }
    @SneakyThrows
    @Test
    public void getEnumsArrayTest() throws Exception {
        ActionType[] types = getEnumsArray(ActionType.class);
        for (ActionType actionType : types) {
            print(actionType + ":" + actionType.ordinal());
        }
    }

    protected static <E extends Enum<E>> E[] getEnumsArray(Class<E> ec) throws Exception {
        Field field = ec.getDeclaredField("$VALUES");
        field.setAccessible(true);
        return (E[]) field.get(ec);
    }

    @SneakyThrows
    @Test
    public void setEnumsFieldTest() throws Exception {
        Field[] fields = ActionType.class.getDeclaredFields();
        for (Field field : fields) {
            print(field.getName());
            if (field.getName() == "commonName") {
                FieldUtil.getFinalStaticObject(field);
            }
        }
    }

    @SneakyThrows
    @Test
    public void setInstanceFieldTest() throws Exception {
        setPermissionCommonNameField(ActionType.BUILD, "건축");
        setPermissionCommonNameField(ActionType.DESTROY, "파괴");
        setPermissionCommonNameField(ActionType.ITEM_USE, "아이템 사용");
        setPermissionCommonNameField(ActionType.SWITCH, "스위치");
        for (ActionType actionType : ActionType.values()) {
            print(actionType.getCommonName());
        }
    }

    private static void setPermissionCommonNameField(TownyPermission.ActionType actionType, String newCommonName) throws Exception {
        Field field = TownyPermission.ActionType.class.getDeclaredField("commonName");
        if (!field.canAccess(actionType)) {
            field.setAccessible(true);
        }
        field.set(actionType, newCommonName);
    }

    private void print(String string) {
        System.out.println(string);
    }
}

package kr.madesv.extension.towny;

import com.palmergames.bukkit.towny.object.TownyPermission;
import lombok.Getter;

public enum TownyTranslation {
    BUILD(TownyPermission.ActionType.BUILD, "설치", "build"),
    DESTROY(TownyPermission.ActionType.DESTROY, "파괴", "destroy"),
    SWITCH(TownyPermission.ActionType.SWITCH, "스위치", "switch"),
    ITEM_USE(TownyPermission.ActionType.ITEM_USE, "아이템", "itemuse");

    @Getter
    private final TownyPermission.ActionType actionType;

    @Getter
    private final String translatedName;

    @Getter
    private final String genuineName;

    TownyTranslation(TownyPermission.ActionType actionType, String translatedName, String genuineName) {
        this.actionType = actionType;
        this.translatedName = translatedName;
        this.genuineName = genuineName;
    }

    public static String untranslateAll(String string) {
        return string
                .replace(TownyTranslation.BUILD.getTranslatedName(), TownyTranslation.BUILD.genuineName)
                .replace(TownyTranslation.DESTROY.getTranslatedName(), TownyTranslation.DESTROY.genuineName)
                .replace(TownyTranslation.SWITCH.getTranslatedName(), TownyTranslation.SWITCH.genuineName)
                .replace(TownyTranslation.ITEM_USE.getTranslatedName(), TownyTranslation.ITEM_USE.genuineName);

    }
}

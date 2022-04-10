package kr.madesv.extension.towny;

import com.palmergames.bukkit.towny.object.TownyPermission;
import lombok.Getter;

public enum TownyTranslation {
    BUILD(TownyPermission.ActionType.BUILD, "건축"),
    DESTROY(TownyPermission.ActionType.DESTROY, "파괴"),
    SWITCH(TownyPermission.ActionType.SWITCH, "스위치"),
    ITEM_USE(TownyPermission.ActionType.ITEM_USE, "아이템");

    @Getter
    private final String translatedName;

    @Getter
    private final TownyPermission.ActionType actionType;

    TownyTranslation(TownyPermission.ActionType actionType, String translatedName) {
        this.actionType = actionType;
        this.translatedName = translatedName;
    }

    public static String untranslateAll(String string) {
        return string
                .replace(TownyTranslation.BUILD.getTranslatedName(), "build")
                .replace(TownyTranslation.DESTROY.getTranslatedName(), "destroy")
                .replace(TownyTranslation.SWITCH.getTranslatedName(), "switch")
                .replace(TownyTranslation.ITEM_USE.getTranslatedName(), "itemuse");
    }
}

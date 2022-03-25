package kr.madesv.extension.towny;

import com.palmergames.bukkit.towny.TownyFormatter;
import lombok.SneakyThrows;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;

public class TownyDateFormatOverrider {
    @SneakyThrows
    public static void override() throws Exception {
        Class<TownyFormatter> formatter = TownyFormatter.class;
        Field formatterField = formatter.getDeclaredField("registeredFormat");
        FieldUtil.setFinalStatic(formatterField, new SimpleDateFormat("yyyy년 MM월 dd일"));
    }

    static class FieldUtil {
        private static Unsafe unsafe;
        static {
            try {
                final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                unsafe = (Unsafe) unsafeField.get(null);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }

        public static void setFinalStatic(Field field, Object value) throws Exception {
            Object fieldBase = unsafe.staticFieldBase(field);
            long fieldOffset = unsafe.staticFieldOffset(field);

            unsafe.putObject(fieldBase, fieldOffset, value);
        }
    }
}

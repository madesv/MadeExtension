package kr.madesv.extension.utils.reflection;

import org.jetbrains.annotations.ApiStatus;
import sun.misc.Unsafe;

import java.lang.reflect.Field;


public class FieldUtil {
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

    // final static 필드에서 오브젝트를 가져온다. enum class 용
    @ApiStatus.Experimental
    public static Object getFinalStaticObject(Field field) throws Exception {
        Object fieldBase = unsafe.staticFieldBase(field);
        long fieldOffset = unsafe.staticFieldOffset(field);
        return unsafe.getObject(fieldBase, fieldOffset);
    }
}
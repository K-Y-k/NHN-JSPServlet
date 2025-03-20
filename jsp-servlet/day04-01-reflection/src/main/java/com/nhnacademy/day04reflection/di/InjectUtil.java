package com.nhnacademy.day04reflection.di;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * UserService에 UserRepository를 자동으로 주입한다.
 *
 * 객체의 생성이 프로그램에 의해서 컨트롤 되었다.
 * 객체 간의 의존성을 프로그래머가 직접 제어하기 보다는 InjectUtil에 의해서 제어되었다.
 */
public final class InjectUtil {

    public static <T> T getObject(Class<T> classType) {
        // Reflection API를 이용해서 모든 fields를 조회해서 field[] 배열로 반환한다.
        T instance = createInstance(classType);
        Field[] fields = classType.getDeclaredFields();

        // 반환된 field[] 배열을 이용해서 순회하면서 @Autowired annotation이 있는 field를 조회한다.
        // 즉, 객체의 생성이 프로그램에 의해서 컨트롤 되었다.
        // 객체 간의 의존성을 프로그래머가 직접 제어하기 보다는 InjectUtil 에 의해서 제어되었다.
        for (Field field : fields) {
            if (field.getAnnotation(Autowired.class) != null) {
                // 해당 field는 createInstance() method에 의해서 객체를 생성한다.
                Object fieldInstance = createInstance(field.getType());
                field.setAccessible(true);

                try {
                    field.set(instance, fieldInstance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return instance;
    }

    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}

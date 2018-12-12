package com.lzp.baseui.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lizhipeng 2017.9.20
 * <p>
 * 类相关的工具
 */

public class ClassUtils {

    /**
     * 通过反射创建Class的实例，请注意这里只能使用无参的构造方法
     */
    public static <T> T createClass(Class<T> classItem) {
        try {
            Constructor<T> constructor = classItem.getConstructor();
            return constructor.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Unable to find a public constructor that takes an argument View in " +
                    classItem.getSimpleName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate " + classItem.getSimpleName(), e);
        }
    }

}

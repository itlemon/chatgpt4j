package cn.codingguide.chatgpt4j.utils;

import java.util.Arrays;

/**
 * @author itlemon
 * Created on 2023-04-23
 */
public class EnumUtils {

    private EnumUtils() {
    }

    @SafeVarargs
    public static <T extends Enum<T>> boolean isIn(T enumValue, T... enumValues) {
        return Arrays.stream(enumValues).anyMatch(e -> e == enumValue);
    }
}

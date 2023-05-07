package cn.codingguide.chatgpt4j.key;

/**
 * OpenAI Key选择策略
 *
 * @author itlemon
 * Created on 2023-04-04
 */
@FunctionalInterface
public interface KeySelectorStrategy<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t);

}

package cn.codingguide.chatgpt4j.key;

import cn.hutool.core.util.RandomUtil;

import java.util.List;

/**
 * 默认的Key选择策略，随机选择
 *
 * @author itlemon
 * Created on 2023-04-04
 */
public class RandomKeySelectorStrategy implements KeySelectorStrategy<List<String>, String> {

    @Override
    public String apply(List<String> keyList) {
        return RandomUtil.randomEle(keyList);
    }

}

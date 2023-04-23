package cn.codingguide.chatgpt4j.utils;

import cn.codingguide.chatgpt4j.exception.ChatGptExceptionCode;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-19
 */
public class ParamValidator {

    private ParamValidator() {
    }

    /**
     * 检查image和mask，其他的依赖OpenAI来检查
     *
     * @param imagePath image
     * @param maskPath mask
     */
    public static void validateImageEditRequest(String imagePath, String maskPath) {
        // 检查image参数
        String validateErrorReason;
        ChatGpt4jExceptionUtils.isTrue(StrUtil.isNotBlank(validateErrorReason = validateImage(imagePath)))
                .throwMessage(ChatGptExceptionCode.OPEN_AI_INVALID_REQUEST_ERROR, validateErrorReason);

        // 检查mask参数
        ChatGpt4jExceptionUtils.isTrue(
                        StrUtil.isNotBlank(maskPath) && StrUtil.isNotBlank(validateErrorReason =
                                validateImage(maskPath)))
                .throwMessage(ChatGptExceptionCode.OPEN_AI_INVALID_REQUEST_ERROR, validateErrorReason);
    }

    /**
     * 检查图片路径是否合规
     *
     * @param imagePath 图片路径
     * @return 如果不合规，将返回错误信息，合规，将返回""
     */
    private static String validateImage(String imagePath) {
        if (StrUtil.isBlank(imagePath)) {
            return "Parameter image must not be blank.";
        }
        // image必须是png格式
        if (!FileUtil.isFile(imagePath) || !"png".equalsIgnoreCase(FileUtil.extName(imagePath))) {
            return "Parameter image or mask must be a png file.";
        }
        // 检查大小
        long maxSize = 4 * 1024 * 1024;
        if (FileUtil.file(imagePath).length() > maxSize) {
            return "The image or mask to edit. Must be a valid PNG file, less than 4MB";
        }
        return "";
    }


}

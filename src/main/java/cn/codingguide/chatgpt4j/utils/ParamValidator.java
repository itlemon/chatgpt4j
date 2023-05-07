package cn.codingguide.chatgpt4j.utils;

import cn.codingguide.chatgpt4j.constant.TranscriptionModel;
import cn.codingguide.chatgpt4j.domain.audio.TranscriptionRequest;
import cn.codingguide.chatgpt4j.domain.audio.TranslationRequest;
import cn.codingguide.chatgpt4j.exception.ChatGptExceptionCode;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author itlemon
 * Created on 2023-04-19
 */
public class ParamValidator {

    private ParamValidator() {
    }

    /**
     * 检查文件
     *
     * @param filePath 文件路径
     */
    public static void validateFile(String filePath) {
        ChatGpt4jExceptionUtils.isTrue(!FileUtil.isFile(filePath))
                .throwMessage(ChatGptExceptionCode.OPEN_AI_INVALID_REQUEST_ERROR,
                        "Parameter file: " + filePath + " is not found.");
    }

    /**
     * 检查文件格式，必须是mp3、mp4、mpeg、mpga、m4a、wav 或 webm
     *
     * @param translation 请求参数
     */
    public static void validateTranslationRequest(TranslationRequest translation) {
        validateAudioRequest(translation.getFile(), translation.getModel());
    }

    /**
     * 检查文件格式，必须是mp3、mp4、mpeg、mpga、m4a、wav 或 webm
     *
     * @param transcription 请求参数
     */
    public static void validateTranscriptionRequest(TranscriptionRequest transcription) {
        validateAudioRequest(transcription.getFile(), transcription.getModel());
    }

    private static void validateAudioRequest(String audioFile, String model) {
        // 检查文件
        ChatGpt4jExceptionUtils.isTrue(StrUtil.isBlank(audioFile))
                .throwMessage(ChatGptExceptionCode.OPEN_AI_INVALID_REQUEST_ERROR, "Parameter file must be not blank.");
        ChatGpt4jExceptionUtils.isTrue(
                StrUtil.equalsAnyIgnoreCase(FileUtil.extName(audioFile), "mp3", "mp4", "mpeg", "mpga", "m4a", "wav",
                        "webm")).throwMessage(ChatGptExceptionCode.OPEN_AI_INVALID_REQUEST_ERROR,
                "Parameter file must be end with mp3、mp4、mpeg、mpga、m4a、wav or webm.");
        // 检查模型
        ChatGpt4jExceptionUtils.isTrue(TranscriptionModel.WHISPER_1.getModel().equals(model))
                .throwMessage(ChatGptExceptionCode.OPEN_AI_INVALID_REQUEST_ERROR,
                        "ID of the model to use. Only whisper-1 is currently available.");
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

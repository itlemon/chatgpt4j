package cn.codingguide.chatgpt4j.constant;

/**
 * @author itlemon
 * Created on 2023-04-21
 */
public enum TranscriptionResponseFormat {

    /**
     * The format of the transcript output, in one of these options: json, text, srt, verbose_json, or vtt.
     * <a href="https://platform.openai.com/docs/api-reference/audio/create#audio/create-response_format">参考链接</a>
     */
    JSON, TEXT, SRT, VERBOSE_JSON, VTT

}

package cn.codingguide.chatgpt4j.exception;

/**
 * @author itlemon <lemon_jiang@aliyun.com>
 * Created on 2023-04-04
 */
public enum ChatGptExceptionMsg implements IExceptionMsg {

    /**
     * 内部预定错误类型
     */
    API_KEY_LIST_NOT_EMPTY(500, "Api key list must be not empty.");


    private final int code;

    private final String msg;

    ChatGptExceptionMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}

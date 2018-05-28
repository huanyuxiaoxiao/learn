package space.fengzheng.cloud.exception;

import lombok.Data;
import space.fengzheng.cloud.contract.CommonContract;

/**
 * 提示式异常
 * 需要返回给调用者 且不进入异常
 */
@Data
public class PromptException extends RuntimeException {
    private int code;
    private String msg;


    public PromptException(String msg) {
        this.code = CommonContract.NET_WORK_WARN;
        this.msg = msg;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public PromptException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getLocalizedMessage() {
        return this.msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}

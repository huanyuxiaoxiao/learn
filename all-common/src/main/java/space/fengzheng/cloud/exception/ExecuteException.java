package space.fengzheng.cloud.exception;

import lombok.Data;
import space.fengzheng.cloud.contract.CommonContract;

/**
 * 异常中断(进入熔断器),每个服务提供者自管理异常
 */
@Data
public class ExecuteException extends RuntimeException {
    private int code;
    private String msg;


    public ExecuteException(String msg) {
        this.code = CommonContract.NET_WORK_WARN;
        this.msg = msg;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ExecuteException(int code, String msg) {
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

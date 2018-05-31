package space.fengzheng.cloud.controller;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.fengzheng.cloud.contract.CommonContract;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Objects;

@Slf4j
@RestController
public class ErrorHandleController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Serializable error(HttpServletRequest request, HttpServletResponse response,Exception e) {
        if (Objects.equals(response.getStatus(), HttpStatus.NOT_FOUND.value())) {
            log.warn("下游业务系统未上线,中断请求:"+request.getRequestURI(),e);
        }else{
            log.warn("原请求状态码:" + response.getStatus());
        }
        response.setStatus(HttpStatus.OK.value());
        return ImmutableMap.of("code", CommonContract.NET_WORK_ERROR, "msg", "系统繁忙");
    }
}

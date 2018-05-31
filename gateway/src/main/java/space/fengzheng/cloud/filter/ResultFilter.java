package space.fengzheng.cloud.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import space.fengzheng.cloud.contract.CommonContract;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

@Slf4j
@Configuration
public class ResultFilter extends ZuulFilter {
    @Value("${spring.profiles.active}")
    private String profiles;
    @Value("#{'${pass.url}'.split(',')}")
    private List<String> passUrl;
    private static String STATIC_MATCH =".*(\\.css|\\.js|\\.html|\\.jsp)$";

    /**
     * to classify a filter by type. Standard types in Zuul are "pre" for pre-routing filtering,
     * "route" for routing to an origin, "post" for post-routing filters, "error" for error handling.
     * We also support a "static" type for static responses see  StaticResponseFilter.
     * Any filterType made be created or added and run by calling FilterProcessor.runFilters(type)
     *
     * @return A String representing that type
     */
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    /**
     * filterOrder() must also be defined for a filter. Filters may have the same  filterOrder if precedence is not
     * important for a filter. filterOrders do not need to be sequential.
     *
     * @return the int order of a filter
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * a "true" return from this method means that the run() method should be invoked
     *
     * @return true if the run() method should be invoked. false will not invoke the run() method
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = getCurrentContext();
        return ! Objects.equals(profiles, "prod") && checkInPassUrl(ctx.getRequest().getRequestURL().toString());
    }

    private boolean checkInPassUrl(String requestURL) {
        if(requestURL.matches(STATIC_MATCH)){
            return false;
        }
        for (String urlKey : passUrl) {
            if (requestURL.indexOf(urlKey)!=-1) {
                log.debug("当前请求地址:"+requestURL+"匹配忽略关键字:"+urlKey);
                return false;
            }
        }
        return true;
    }

    /**
     * if shouldFilter() is true, this method will be invoked. this method is the core method of a ZuulFilter
     *
     * @return Some arbitrary artifact may be returned. Current implementation ignores it.
     */
    @Override
    public Object run() {
        RequestContext ctx = getCurrentContext();
        final int responseStatusCode = ctx.getResponseStatusCode();
        InputStream stream = ctx.getResponseDataStream();
        JSONObject result = new JSONObject();
        try {
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            if (! Objects.equals(responseStatusCode, HttpStatus.OK.value())) {
                handleException(result, ctx, body);
            } else {
                result.put("code", CommonContract.NET_WORK_SUCCESS);
                if (body.matches("^\\{.*\\}$")) {
                    result.put("data", JSONObject.parseObject(body));
                } else {
                    result.put("data", ImmutableMap.of("value", body));
                }
            }

        } catch (Exception e) {
            result.put("code", CommonContract.NET_WORK_ERROR);
            result.put("msg", "系统异常");
            log.error(e.getLocalizedMessage(), e);
        }
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.OK.value());
        ctx.setResponseBody(result.toString());
        return null;
    }

    /**
     * 处理异常回文
     *
     * @param result
     * @param ctx
     * @param body
     */
    private void handleException(JSONObject result, RequestContext ctx, String body) {
        JSONObject oldData = JSONObject.parseObject(body);
        result.put("code", ctx.getResponseStatusCode());
        if (Objects.equals(ctx.getResponseStatusCode(), HttpStatus.NOT_FOUND.value())) {
            result.put("msg", HttpStatus.NOT_FOUND.getReasonPhrase());
        } else {
            result.put("code", CommonContract.NET_WORK_WARN);
            log.warn("[handleException]" + oldData.toJSONString());
            result.put("msg", oldData.getString("message"));
        }
    }
}

package space.fengzheng.cloud.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import space.fengzheng.cloud.contract.CommonContract;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

@Slf4j
@Configuration
public class ResultFilter extends ZuulFilter {

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
        RequestContext ctx =getCurrentContext();
        return !Objects.equals(ctx.getResponseStatusCode(),HttpStatus.NOT_FOUND.value());
    }

    /**
     * if shouldFilter() is true, this method will be invoked. this method is the core method of a ZuulFilter
     *
     * @return Some arbitrary artifact may be returned. Current implementation ignores it.
     */
    @Override
    public Object run() {
        log.info("run");
        RequestContext ctx = getCurrentContext();
        final int responseStatusCode = ctx.getResponseStatusCode();
        log.info(String.valueOf(responseStatusCode));
        InputStream stream = ctx.getResponseDataStream();
        JSONObject result = new JSONObject();
        try {
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            if (! Objects.equals(responseStatusCode, HttpStatus.OK.value())) {
                handleException(result, ctx);
            } else {
                result.put("code", CommonContract.NET_WORK_SUCCESS);
            }
            if (! StringUtils.isEmpty(body)) {
                result.put("data", body);
            }
        } catch (Exception e) {
            result.put("code", CommonContract.NET_WORK_ERROR);
            log.error(e.getLocalizedMessage(), e);
        }
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.OK.value());
        if(ctx.getResponse()!=null){
            ctx.setResponseBody(result.toString());
        }
        return null;
    }

    private void handleException(JSONObject result, RequestContext ctx) {
        final Throwable cause = ctx.getThrowable();
        if(cause!=null){
            log.info(cause.getLocalizedMessage(),cause.getCause());
        }
        result.put("code", CommonContract.NET_WORK_WARN);
    }
}

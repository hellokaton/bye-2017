package io.github.biezhi.bye2017.webhook;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.WebContext;
import com.blade.mvc.hook.Signature;
import com.blade.mvc.hook.WebHook;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import io.github.biezhi.bye2017.cache.BlackCache;
import lombok.extern.slf4j.Slf4j;

/**
 * @author biezhi
 * @date 2017/12/10
 */
@Bean
@Slf4j
public class BaseWebHook implements WebHook {

    @Override
    public boolean before(Signature signature) {
        Request  request  = signature.request();
        Response response = signature.response();
        String   ip       = request.address();

        if (BlackCache.isBlock(ip)) {
            response.text("你被禁止访问了, 请向管理员充钱。");
            return false;
        }

        log.info("客户端UA: {}", request.header("user-agent"));
        log.info("客户端IP: {}", ip);

        request.attribute("isDev", WebContext.blade().devMode());
        request.attribute("version", WebContext.blade().environment().get("app.version", "0.0.1"));
        request.attribute("cdn", WebContext.blade().environment().getOrNull("app.cdn"));
        return true;
    }

}

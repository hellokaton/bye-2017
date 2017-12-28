package io.github.biezhi.bye2017.config;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.WebContext;
import com.blade.mvc.handler.DefaultExceptionHandler;
import com.blade.mvc.http.Response;
import com.blade.mvc.ui.RestResponse;
import com.blade.validator.exception.ValidateException;

/**
 * @author biezhi
 * @date 2017/12/10
 */
@Bean
public class ExceptionConfig extends DefaultExceptionHandler {

    @Override
    public void handle(Exception e) {
        if (e instanceof ValidateException) {
            ValidateException validateException = (ValidateException) e;
            Response          response          = WebContext.response();
            String            msg               = validateException.getErrMsg();
            response.json(RestResponse.fail(msg));
        } else {
            super.handle(e);
        }
    }

}

package io.github.biezhi.bye2017.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.ui.RestResponse;
import com.blade.validator.annotation.Valid;
import com.google.common.util.concurrent.RateLimiter;
import io.github.biezhi.bye2017.cache.ClientCache;
import io.github.biezhi.bye2017.constant.AllConst;
import io.github.biezhi.bye2017.model.dto.RecordDto;
import io.github.biezhi.bye2017.model.param.My2017;
import io.github.biezhi.bye2017.service.RecordService;
import io.github.biezhi.bye2017.utils.ByeUtils;

import java.util.Date;
import java.util.List;

import static io.github.biezhi.bye2017.constant.AllConst.WEB_CLIENT_SIGN;

@Path
public class IndexController {

    @Inject
    private RecordService recordService;

    /**
     * 每秒允许10个请求
     */
    private RateLimiter rateLimiter = RateLimiter.create(10);

    @GetRoute("/")
    public String index(Response response, Request request) {
        response.cookie(WEB_CLIENT_SIGN, ClientCache.getSalt());
        request.attribute("is_home", true);
        return "index.html";
    }

    @GetRoute("records/:page")
    @JSON
    public RestResponse recordList(@PathParam Integer page,
                                   @CookieParam("WEB_CLIENT_SIGN_") String sign) {

        if (!ClientCache.isOk(sign)) {
            return RestResponse.fail("Fuck [WEB_CLIENT_SIGN]!");
        }

        if (rateLimiter.tryAcquire()) {
            List<RecordDto> recordDtos = recordService.queryRecords("created desc", page, 12);
            return RestResponse.ok(recordDtos);
        } else {
            return RestResponse.fail("Fuck [The frequency is too fast]!");
        }
    }

    @GetRoute("/about")
    public String about() {
        return "about.html";
    }

    @GetRoute("/show")
    public String show() {
        return "show.html";
    }

    @PostRoute("record")
    @JSON
    public RestResponse submit(@Valid @BodyParam My2017 my2017,
                               @CookieParam("WEB_CLIENT_SIGN_") String sign,
                               Request request, Response response) {

        if (StringKit.isNotBlank(request.cookie(AllConst.SUBMIT_COOKIE_KEY))) {
            return RestResponse.fail("Fuck [REPEAT]!");
        }

        if (!ClientCache.isOk(sign)) {
            return RestResponse.fail("Fuck [WEB_CLIENT_SIGN]!");
        }

        if (ByeUtils.isAnyBlank(my2017.getBook(), my2017.getFeeling(), my2017.getHealth(),
                my2017.getJob(), my2017.getMusic(), my2017.getOther(), my2017.getOutlook(), my2017.getTechnology())) {
            return RestResponse.fail("至少写点什么吧!");
        }

        my2017.setIp(request.address());
        recordService.saveRecord(my2017);
        response.cookie(AllConst.SUBMIT_COOKIE_KEY, DateKit.toString(new Date(), "yyyyMMdd"));
        ClientCache.del(sign);
        return RestResponse.ok();
    }

    @PostRoute("star/:rid")
    @JSON
    public RestResponse star(@PathParam String rid) {
        recordService.addStar(rid);
        return RestResponse.ok();
    }

}

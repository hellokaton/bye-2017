package io.github.biezhi.bye2017.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import io.github.biezhi.bye2017.service.GenerateWordCloud;

/**
 * @author biezhi
 * @date 2017/12/7
 */
@Path("admin")
public class AdminController {

    @Inject
    private GenerateWordCloud generateWordCloud;

    /**
     * 生成云词
     * 1. all.txt (所有内容)
     * 2. location.txt
     * 3. topics (每单个话题)
     */
    @GetRoute("g")
    public void g() {
        generateWordCloud.generator();
    }


}

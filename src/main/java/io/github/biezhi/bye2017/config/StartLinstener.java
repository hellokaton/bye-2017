package io.github.biezhi.bye2017.config;

import com.blade.Blade;
import com.blade.event.BeanProcessor;
import com.blade.ioc.annotation.Bean;
import com.blade.mvc.Const;
import io.github.biezhi.bye2017.service.GenerateWordCloud;
import io.github.biezhi.bye2017.task.CleanBlockTask;

/**
 * @author biezhi
 * @date 2017/12/11
 */
@Bean
public class StartLinstener implements BeanProcessor {

    @Override
    public void processor(Blade blade) {
        System.out.println("======================" + Const.class.getResource("/"));
        new CleanBlockTask().run();
//        new GenerateWordCloud().generator();
    }

}

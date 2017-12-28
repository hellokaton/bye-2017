package io.github.biezhi.bye2017.config;

import com.blade.Blade;
import com.blade.event.BeanProcessor;
import com.blade.ioc.annotation.Bean;
import com.blade.mvc.view.template.JetbrickTemplateEngine;

@Bean
public class TemplateConfig implements BeanProcessor {

    @Override
    public void processor(Blade blade) {
        blade.templateEngine(new JetbrickTemplateEngine());
    }

}
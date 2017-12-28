package io.github.biezhi.bye2017.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.blade.Blade;
import com.blade.event.BeanProcessor;
import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Order;
import com.blade.jdbc.Base;
import lombok.extern.slf4j.Slf4j;

@Bean
@Order(1)
@Slf4j
public class DbConfig implements BeanProcessor {

    @Override
    public void processor(Blade blade) {
        try {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl(blade.environment().getOrNull("jdbc.url"));
            dataSource.setUsername(blade.environment().getOrNull("jdbc.username"));
            dataSource.setPassword(blade.environment().getOrNull("jdbc.password"));
            dataSource.setInitialSize(5);
            dataSource.setMinIdle(5);
            dataSource.setMaxActive(50);
            dataSource.setMaxWait(60_000);
            dataSource.setMinEvictableIdleTimeMillis(300_000);
            dataSource.setPhyTimeoutMillis(7200_000);
            dataSource.setMinIdle(5);
            dataSource.setTestOnBorrow(false);
            dataSource.setTestOnReturn(false);
            dataSource.setTestWhileIdle(true);
            dataSource.setRemoveAbandoned(true);
            dataSource.setRemoveAbandonedTimeout(180);
            dataSource.setLogAbandoned(true);
            dataSource.setDefaultAutoCommit(true);

            Base.open(dataSource);
        } catch (Exception e) {
            log.error("Connection database fail");
        }

    }
}
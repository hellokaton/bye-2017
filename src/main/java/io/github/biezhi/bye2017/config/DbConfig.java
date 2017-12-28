package io.github.biezhi.bye2017.config;

import com.blade.Blade;
import com.blade.event.BeanProcessor;
import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Order;
import com.blade.jdbc.Base;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

@Bean
@Order(1)
@Slf4j
public class DbConfig implements BeanProcessor {

    @Override
    public void processor(Blade blade) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(blade.environment().getOrNull("jdbc.url"));
            config.setUsername(blade.environment().getOrNull("jdbc.username"));
            config.setPassword(blade.environment().getOrNull("jdbc.password"));
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            HikariDataSource ds = new HikariDataSource(config);
            Base.open(ds);
        } catch (Exception e) {
            log.error("Connection database fail");
        }

    }
}
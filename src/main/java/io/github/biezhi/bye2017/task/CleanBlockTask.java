package io.github.biezhi.bye2017.task;

import io.github.biezhi.bye2017.cache.BlackCache;
import io.github.biezhi.bye2017.model.entity.Blacklist;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 定时清除黑名单列表
 *
 * @author biezhi
 * @date 2017/12/11
 */
@Slf4j
public class CleanBlockTask implements Task {

    @Override
    public void run() {
        int period = 30;
        log.info("开启定时清理黑名单任务，每 {} 分钟一次", period);
        int initialDelay = 0;
        executor.scheduleAtFixedRate(() -> {
            Set<String> blockList = BlackCache.blockList();
            log.info("清理黑名单IP列表: {}", blockList);
            List<Blacklist> blacklists = new Blacklist().where("expired", ">", new Date()).findAll();
            Set<String>     cleanIps   = blacklists.stream().map(Blacklist::getIp).collect(Collectors.toSet());
            blockList.removeAll(cleanIps);
        }, initialDelay, period, TimeUnit.MINUTES);
    }

}

package io.github.biezhi.bye2017.cache;

import io.github.biezhi.bye2017.model.entity.Blacklist;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 黑名单缓存
 *
 * @author biezhi
 * @date 2017/12/7
 */
public class BlackCache {

    private static final Set<String>     ips              = new HashSet<>(64);
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);

    /**
     * 是否屏蔽该 IP
     *
     * @param ip
     * @return
     */
    public static boolean isBlock(String ip) {
        return ips.parallelStream().filter(i -> i.equals(ip)).count() > 0;
    }

    /**
     * 添加黑名单
     *
     * @param ip
     */
    public static void addBlock(String ip) {
        ips.add(ip);

        EXECUTOR_SERVICE.submit(() -> {
            Blacklist blackList = new Blacklist();
            blackList.setCreated(new Date());
            blackList.setIp(ip);
            blackList.save();
        });
    }

    /**
     * 解除黑名单
     *
     * @param ip
     */
    public static void addWhitelist(String ip) {
        ips.remove(ip);
        EXECUTOR_SERVICE.submit(() -> {
            Blacklist blackList = new Blacklist();
            blackList.setIp(ip);
            blackList.delete();
        });
    }

    public static Set<String> blockList() {
        return ips;
    }

    public static void clean(){
        ips.clear();
    }
}

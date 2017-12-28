package io.github.biezhi.bye2017.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author biezhi
 * @date 2017/12/11
 */
public interface Task {

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    void run();

}

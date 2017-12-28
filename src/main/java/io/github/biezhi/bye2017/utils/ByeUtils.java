package io.github.biezhi.bye2017.utils;

import com.blade.kit.CollectionKit;
import com.blade.kit.StringKit;

import java.util.stream.Stream;

/**
 * @author biezhi
 * @date 2017/12/27
 */
public class ByeUtils {

    public static boolean isAnyBlank(String... values) {
        if (CollectionKit.isEmpty(values)) {
            return true;
        }
        return Stream.of(values).filter(StringKit::isBlank).count() == values.length;
    }

}

package io.github.biezhi.bye2017.cache;

import com.blade.kit.Hashids;
import com.blade.kit.StringKit;

import java.util.HashSet;
import java.util.Set;

/**
 * @author biezhi
 * @date 2017/12/27
 */
public class ClientCache {

    private static final Set<String> clients = new HashSet<>();
    private static final Hashids     h       = new Hashids("client_slat", 10);

    public static synchronized String getSalt() {
        String s = h.encode(2017, System.currentTimeMillis());
        String  deCode = h.decodeHex(s);
        clients.add(deCode);
        return s;
    }

    public static boolean isOk(String id) {
        if (StringKit.isBlank(id)) {
            return false;
        }
        try {
            String  deCode = h.decodeHex(id);
            return clients.contains(deCode);
        } catch (Exception e) {
        }
        return false;
    }

    public static void del(String id) {
        if (StringKit.isBlank(id)) {
            return;
        }
        try {
            String  deCode = h.decodeHex(id);
            clients.remove(deCode);
        } catch (Exception e) {
        }
    }
}

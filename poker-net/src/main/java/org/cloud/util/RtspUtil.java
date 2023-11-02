package org.cloud.util;

import io.netty.util.internal.StringUtil;

import java.util.Map;
import java.util.WeakHashMap;

public class RtspUtil {

    public static Map<String, String> decode(String authenticate) {
        String[] parts = authenticate.split(String.valueOf(StringUtil.COMMA));
        WeakHashMap<String, String> params = new WeakHashMap<>();
        String[] a = parts[0].split(String.valueOf(StringUtil.SPACE));
        String scheme = a[0];
        params.put("scheme", scheme);
        String realmEntry = parts[0].replace(scheme, "").trim();
        String val = realmEntry.split("=")[1];
        if (realmEntry.startsWith("realm")) {
            params.put("realm", val.replaceAll("\"", ""));
        } else if (realmEntry.startsWith("nonce")) {
            params.put("nonce", val.replaceAll("\"", ""));
        }
        for (int i = 1; i < parts.length; i++) {
            String[] entry = parts[i].split("=");
            if (parts[i].trim().startsWith("realm")) {
                params.put("realm", entry[1].replaceAll("\"", ""));
            } else if (parts[i].trim().startsWith("nonce")) {
                params.put("nonce", entry[1].replaceAll("\"", ""));
            }
        }

        return params;
    }

}

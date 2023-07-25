package org.cloud.cst;

public class APIConstant {
    public static final String VERSION = "/v1";
    public static final String GATEWAY_PATH = "/api";
    public static final double DEFAULT_RATE_LIMITER = 10.0;

    // gateway服务
    public static final String GATEWAY_LOGIN = GATEWAY_PATH + VERSION + "/login";
    // chat服务
    public static final String CHAT_COMPLETIONS = VERSION + "/chat/completions";
    public static final String CHAT_COUNT = VERSION + "/chat/count";
    public static final String CHAT_IMAGES_GENERATIONS = VERSION + "/chat/images/generations";


}

package org.cloud.emu;

import org.cloud.cst.APIConstant;

public enum ApiEmu {

    // chat服务
    CHAT_COMPLETIONS(APIConstant.CHAT_COMPLETIONS,"创建聊天完成", APIConstant.DEFAULT_RATE_LIMITER),
    CHAT_COUNT(APIConstant.CHAT_COUNT,"统计会话总数", APIConstant.DEFAULT_RATE_LIMITER),
    CHAT_IMAGES_GENERATIONS(APIConstant.CHAT_IMAGES_GENERATIONS,"创建图像", APIConstant.DEFAULT_RATE_LIMITER);


    private String api;
    private String describe;
    private double rateLimiter;


    ApiEmu(String api, String describe, double rateLimiter) {
        this.api = api;
        this.describe = describe;
        this.rateLimiter = rateLimiter;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

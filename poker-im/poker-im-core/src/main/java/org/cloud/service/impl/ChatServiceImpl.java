package org.cloud.service.impl;

import cn.hutool.json.JSONUtil;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;
import org.cloud.entity.*;
import org.cloud.entity.common.BaseResponse;
import org.cloud.entity.exception.FutureException;
import org.cloud.properties.OpenAIProperties;
import org.cloud.service.ChatService;
import org.cloud.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ChatServiceImpl<T extends BaseResponse> implements ChatService<BaseResponse> {
    @Autowired(required = false)
    private OpenAIProperties openAIProperties;
    private static final String AUTHORIZATION = "Authorization";
    private static final String API_COMPLETIONS = "/v1/chat/completions";
    private static final String API_MODELS = "/v1/models/davinci";
    private static final String API_IMAGES_GENERATIONS = "/v1/images/generations";

    @Override
    public CompletableFuture<BaseResponse> completions(ChatRequest chatRequest) {
        String parms = JSONUtil.toJsonStr(chatRequest);
        log.info("completions:{}", parms);
        String url = new StringBuffer().append(openAIProperties.getApiBaseUrl()).append(API_COMPLETIONS).toString();
        return HttpUtil.asyncHttpClient().preparePost(url)
                .setHeader(AUTHORIZATION, openAIProperties.getAuthorization())
                .setHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .setBody(parms)
                .execute().toCompletableFuture()
                .thenApplyAsync(f ->{
                    String body = f.getResponseBody();
                    log.info("{}", body);
                    OpenAIResponse response = JSONUtil.toBean(body, ChatResponse.class);
                    return BaseResponse.success(response);
                }).exceptionally((e)->{
                    log.error("{}", e);
                    throw FutureException.show(e.getMessage());
                });
    }

    @Override
    public CompletableFuture<BaseResponse> models() {
        String url = new StringBuffer().append(openAIProperties.getApiBaseUrl()).append(API_MODELS).toString();
        return HttpUtil.asyncHttpClient().prepareGet(url)
                .setHeader(AUTHORIZATION, openAIProperties.getAuthorization())
                .execute().toCompletableFuture()
                .thenApplyAsync(f ->{
                    String body = f.getResponseBody();
                    OpenAIResponse response = JSONUtil.toBean(body, OpenAIResponse.class);
                    return BaseResponse.success(response);
                }).exceptionally((e)->{
                    log.error("{}", e);
                    throw FutureException.show(e.getMessage());
                });
    }

    @Override
    public CompletableFuture<BaseResponse> createImages(ImageRequest imageRequest) {
        String parms = JSONUtil.toJsonStr(imageRequest);
        log.info("createImages: {}", parms);
        String url = new StringBuffer().append(openAIProperties.getApiBaseUrl()).append(API_IMAGES_GENERATIONS).toString();
        return HttpUtil.asyncHttpClient().preparePost(url)
                .setHeader(AUTHORIZATION, openAIProperties.getAuthorization())
                .setHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .setBody(parms)
                .execute().toCompletableFuture()
                .thenApplyAsync(f ->{
                    String body = f.getResponseBody();
                    log.info("{}", body);
                    OpenAIResponse response = JSONUtil.toBean(body, ImageResponse.class);
                    return BaseResponse.success(response);
                }).exceptionally((e)->{
                    log.error("{}", e);
                    throw FutureException.show(e.getMessage());
                });
    }

    @Override
    public CompletableFuture<BaseResponse> imagesEdits() {
        return null;
    }
}
